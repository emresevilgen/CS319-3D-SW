<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../configs/ConfigManager.php';
include_once dirname(__FILE__) . '/../utils/Utils.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/Game.php';
include_once dirname(__FILE__) . '/LobbyUser.php';
include_once dirname(__FILE__) . '/Mode.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class Lobby implements MongoDB\BSON\Persistable, JSONResponse {

  private $collection;
  private $configManager;

  private $id;
  private $gameId;
  private $admin;
  private $name;
  private $code;
  private $users;
  private $mode;
  private $createdAt;

  function __construct()
  {
    // Create config manager to manage database and time.
    $this->configManager = new ConfigManager();
    $this->collection = $this->configManager->getDatabase()->lobbies;

    $this->id = new MongoDB\BSON\ObjectId;
    $this->gameId = null;
    $this->createdAt = new MongoDB\BSON\UTCDateTime;
    $this->users = array();
  }

  /**
   * Create new lobby.
   * 
   */
  function createLobby() {

    // Check all necessary fields are filled.
    $fields = ['admin' => $this->admin, 'name' => $this->name];
    checkFields($fields);
    
    $lobbyUser = (new LobbyUser($this->admin))->setIsReady(true);
    array_push($this->users, $lobbyUser);

    // Generate lobby code with length of six captial characters until it is unique.
    do {
      $this->code = strtoupper(bin2hex(random_bytes(3)));
    } while ($this->isLobbyCodeExist($this->code));
    
    // Create new lobby.
    $insertOneResult = $this->collection->insertOne($this);
  
    // Check lobby is created properly.
    if ($insertOneResult->getInsertedCount() == 1) {
      return $this;
    } else {
      throw new Exception("An error occured while creating lobby!");
    }

    return $this;
  }

  /**
   * 
   * 
   */
  function joinLobby($lobbyUser) {
    $updateResult = $this->collection->updateOne(
      [
        'code' => $this->code
      ],
      [
        '$push' => [ 
          'users' => $lobbyUser
        ]
      ]
    );
  
    if ($updateResult->getModifiedCount() != 1) { 
      throw new Exception("Invalid lobby code!");
    }

    return $this;
  }

  /**
   * 
   * 
   */
   function retrieveLobbyDataViaCode() {
    $cursor = $this->collection->findOne(
      [
        'code' => $this->code
      ]
    );

    if (!$cursor) {
      throw new Exception("Invalid lobby code!");
    }

    $this->bsonUnserialize($cursor->bsonSerialize());

    return $this;

  }

  /**
   * 
   * 
   */
  function retrieveLobbyDataViaId($username) {

    $this->updatePlayerActiveness($username);

    $cursor = $this->collection->findOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ]
    );

    if (!$cursor) {
      throw new Exception("Invalid lobby id!");
    }

    $this->bsonUnserialize($cursor->bsonSerialize());
   

    return $this;

  }

  /**
   * 
   * 
   */
  function exitLobby($username) {
    $users = $this->retrieveUsers();
    foreach ($users as $key => $value) {
      if ($value->getUsername() == $username) {
        unset($users[$key]);
      }
    }

    $this->updateUsers($users);

    return $this;
  }

  function ready($username, $isReady) {
    $users = $this->retrieveUsers();
    foreach ($users as $key => $user) {
      if ($user->getUsername() == $username) {
        $user->setIsReady((bool)$isReady);
      }
    }

    $this->updateUsers($users);

    return $this;
  }
  
 
  function startGame() {
    // Check the game is already started
    if (!empty($this->getGameId())) {
      throw new Exception('The game has been already started!');
    }

    // Get lobby users and send them to game's constructor.
    $users = $this->retrieveUsers();
    $game = (new Game())->constructFromLobby((array)$users);
    $game->setMode($this->mode);
    $game->createGame();

    // Update gameId attribute
    $this->updateSingleAttributeViaId('gameId', (string)$game->getId(), false);
    
    return $this;
  }

  /**
   * 
   * 
   */
  private function updateSingleAttributeViaId($key, $value, $giveError = true) {
    $updateResult = $this->collection->updateOne(
      [
        '_id' => $this->id
      ],
      [
        '$set' => [ 
          $key => $value
        ]
      ]
    );
  
    if ($giveError && $updateResult->getModifiedCount() != 1) { 
      throw new Exception("Invalid lobby code!");
    }

    return $this;
  }

  /**
   * 
   * 
   */
  private function removePlayer($username) {
    
  }

  
  /**
   * 
   * 
   */
  private function updatePlayerActiveness($username) {
    $users = $this->retrieveUsers();
    foreach ($users as $key => $lobbyUser) {
      if ($lobbyUser->getUsername() == $username) {
        $lobbyUser->setIsActive(true);
        $lobbyUser->setLastActiveAt(new MongoDB\BSON\UTCDateTime);
      } else {
        $userLastActiveAt = (int)(string)$lobbyUser->getLastActiveAt() / 1000;
        $currentTimestamp = $this->configManager->getCurrentTimestamp();
        $diff = $currentTimestamp - $userLastActiveAt;
        if ($diff > 5000) {
          $lobbyUser->setIsActive(false);
          if ($diff > 6000) {
            unset($users[$key]);
            $user = (new User())->setUsername($lobbyUser->getUsername());
            $user->updateJoinedLobbyWithoutToken();
          }
        }

      }
    }

    $this->updateUsers($users);

    return $this;
  }

  /**
   * 
   * 
   */
  private function retrieveUsers() {
    $users = $this->collection->findOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ],
      [
        'projection' => [
          'users' => 1
        ]
      ]
    );

    if (!$users) {
      throw new Exception("Invalid lobby id!");
    }

    return $users['users'];

  }

  private function updateUsers($users) {

    $admin = null;
    foreach ($users as $key => $lobbyUser) {
      $admin = $lobbyUser->getUsername();
      break;
    }

    $this->collection->updateOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ],
      [
        '$set' => [
          'users' => $users,
          'admin' => $admin
        ]
      ]
    );

    return $this;
  }

  /**
   * 
   * 
   */
  private function isLobbyCodeExist($code) {
    $isLobbyCodeExist = $this->collection->count(['code' => $code]);
    return $isLobbyCodeExist;
  }

  function bsonSerialize()
  {
    return [
      '_id' => $this->id,
      'gameId' => $this->gameId,
      'admin' => $this->admin,
      'name' => $this->name,
      'code' => $this->code,
      'users' => $this->users,
      'mode' => $this->mode,
      'createdAt' => $this->createdAt
    ];
  }

  function bsonUnserialize(array $data)
  {
    $this->id  = $data['_id'];
    $this->gameId  = $data['gameId'];
    $this->admin  = $data['admin'];
    $this->name  = $data['name'];
    $this->code  = $data['code'];
    $this->users  = $data['users'];
    $this->mode  = $data['mode'];
    $this->createdAt  = $data['createdAt'];
  }

  function jsonArraySerialize()
  {
    $users = array();
    foreach($this->users as $key => $value) {
      array_push($users, $value->jsonArraySerialize());
    }

    return [
      'id' => (string) $this->id,
      'gameId' => $this->gameId,
      'admin' => $this->admin,
      'name' => $this->name,
      'code' => $this->code,
      'users' => $users,
      'mode' => $this->mode->jsonArraySerialize(),
    ];

  }

  // Getters and setters


  /**
   * Get the value of id
   */ 
  public function getId()
  {
    return $this->id;
  }

  /**
   * Set the value of id
   *
   * @return  self
   */ 
  public function setId($id)
  {
    $this->id = $id;

    return $this;
  }

  /**
   * Get the value of admin
   */ 
  public function getAdmin()
  {
    return $this->admin;
  }

  /**
   * Set the value of admin
   *
   * @return  self
   */ 
  public function setAdmin($admin)
  {
    $this->admin = $admin;

    return $this;
  }

  /**
   * Get the value of name
   */ 
  public function getName()
  {
    return $this->name;
  }

  /**
   * Set the value of name
   *
   * @return  self
   */ 
  public function setName($name)
  {
    $this->name = $name;

    return $this;
  }

  /**
   * Get the value of code
   */ 
  public function getCode()
  {
    return $this->code;
  }

  /**
   * Set the value of code
   *
   * @return  self
   */ 
  public function setCode($code)
  {
    $this->code = $code;

    return $this;
  }

  /**
   * Get the value of users
   */ 
  public function getUsers()
  {
    return $this->users;
  }

  /**
   * Set the value of users
   *
   * @return  self
   */ 
  public function setUsers($users)
  {
    $this->users = $users;

    return $this;
  }

  /**
   * Get the value of mode
   */ 
  public function getMode()
  {
    return $this->mode;
  }

  /**
   * Set the value of mode
   *
   * @return  self
   */ 
  public function setMode($mode)
  {
    $this->mode = $mode;

    return $this;
  }

  /**
   * Get the value of createdAt
   */ 
  public function getCreatedAt()
  {
    return $this->createdAt;
  }

  /**
   * Set the value of createdAt
   *
   * @return  self
   */ 
  public function setCreatedAt($createdAt)
  {
    $this->createdAt = $createdAt;

    return $this;
  }

  /**
   * Get the value of gameId
   */ 
  public function getGameId()
  {
    return $this->gameId;
  }

  /**
   * Set the value of gameId
   *
   * @return  self
   */ 
  public function setGameId($gameId)
  {
    $this->gameId = $gameId;

    return $this;
  }
}

?>