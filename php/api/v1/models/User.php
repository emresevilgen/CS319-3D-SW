<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../configs/ConfigManager.php';
include_once dirname(__FILE__) . '/../utils/Utils.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class User implements MongoDB\BSON\Persistable, JSONResponse {

  private $isAuthenticated;
  private $collection;
  private $configManager;

  private $id;
  private $username;
  private $password;
  private $name;
  private $joinedLobby;
  private $joinedGame;
  private $createdAt;
  private $registeredIp;
  private $token;
  private $expiresIn;
  private $lastActivenessTimestamp;

  function __construct()
  {
    // Create config manager to manage database and time.
    $this->configManager = new ConfigManager();
    $this->collection = $this->configManager->getDatabase()->users;

    $this->isAuthenticated = false;
    $this->id = new MongoDB\BSON\ObjectId;
    $this->createdAt = new MongoDB\BSON\UTCDateTime;
    $this->expiresIn = $this->configManager->getExpiresIn();


  }

  /**
   * Authenticate the user
   * Set isAuthenticated true if it is success.
   *
   * @return  self
   */ 
  function login() {
    // Check all necessary fields are filled.
    $fields = ['username' => $this->username, 'password' => $this->password];
    checkFields($fields);

    //Get current timestamp
    $this->lastActivenessTimestamp = $this->configManager->getCurrentTimestamp();

    //Generate token with 256 character which is either a,b,c,d,e,f,0,1,2,3,4,5,6,7,8 or 9.
    $this->token = bin2hex(random_bytes(128));

    // Authenticate the user with given username and password in md5.
    // If it corresponds with the data, update it.
    $updateResult = $this->collection->updateOne(
      [
        'username' => $this->username, 
        'password' => $this->password
      ],
      [
        '$set' => [
          'token' => $this->token,
          'expiresIn' => $this->expiresIn,
          'lastActivenessTimestamp' => $this->lastActivenessTimestamp
        ]
      ]
    );
    // If authentication is successful
    if ($updateResult->getModifiedCount() == 1) {
      $this->isAuthenticated = true;
      $this->retrieveUserData();
    } else {
      $this->isAuthenticated = false;
      throw new Exception("Invalid username or password.");
    }
    
    return $this;
  }

  /**
   * If the user is authenticated, retrieve all user data
   *  
   * @return  self
   */ 
  function retrieveUserData() {

    // Check all necessary fields are filled.
    $fields = ['username' => $this->username, 'token' => $this->token];
    checkFields($fields);

    //Get current timestamp
    $this->lastActivenessTimestamp = $this->configManager->getCurrentTimestamp();

    // If the user is authenticated successfully, get all data of the user.
    $cursor = $this->collection->findOne(
      [
      'username' => $this->username,
      'token' => $this->token
      ]
    );
    
    if (!$cursor) {
      throw new Exception("Not authenticated!");
    }

    $this->bsonUnserialize((array)($cursor->bsonSerialize()));

    return $this;
  }

  /**
   * Check the user authentication
   *  
   * @return  self
   */ 
  function checkAuthenticationWithToken() {

    // Check all necessary fields are filled.
    $fields = ['username' => $this->username, 'token' => $this->token];
    checkFields($fields);

    $cursor = $this->collection->findOne(
      [
      'username' => $this->username,
      'token' => $this->token
      ],
      [
        'projection' => [
          'lastActivenessTimestamp' => 1
        ]
      ]
    );

    if (!$cursor) {
      throw new Exception("Not authenticated!");
    }

    // Get current timestamp
    $currentTimestamp = $this->configManager->getCurrentTimestamp();

    // Get users' lastActivenessTimestamp
    $this->lastActivenessTimestamp = $cursor["lastActivenessTimestamp"];
    $userLastActiveAt = $this->lastActivenessTimestamp;
    $diff = $currentTimestamp - $userLastActiveAt;

    // Check the token has expired or not
    if ($diff > $this->expiresIn) {
      throw new Exception("Your session has expired! Please sign in again.");
    }

    $this->isAuthenticated = true;
    return $this;
  }

  /**
   * Sign up by username, password and name.
   *  
   * @return  self
   */ 
  function signup() {

    // Check all necessary fields are filled.
    $fields = ['username' => $this->username, 'password' => $this->password, 'name' => $this->name];
    checkFields($fields);

    // Check whether username is already used or not.
    $this->checkUsernameIsTaken();

    // Insert new user to database
    $insertOneResult = $this->collection->insertOne($this);
  
    // Check signing up is okay
    if ($insertOneResult->getInsertedCount() != 1) {
      throw new Exception("Signing up failed");
    }
    
    return $this;
  }

  /**
   * Update users' lastActivenessTimestamp to prevent expiration of session of user.
   *  
   * @return  self
   */ 
  function updateLastTimestamp() {

    // Get current timestamp
    $this->lastActivenessTimestamp = $this->configManager->getCurrentTimestamp();

    // Update lastActivenessTimestamp with current time stamp.
    $this->collection->updateOne(
      [
        'username' => $this->username, 
        'token' => $this->token
      ],
      [
        '$set' => [
          'lastActivenessTimestamp' => $this->lastActivenessTimestamp
        ]
      ]
    );
    
    return $this;

  }

  /**
   * Check the username for signing up is taken already or not
   *  
   * @return  self
  */ 
  private function checkUsernameIsTaken() {
    $isUsernameTaken = $this->collection->count(['username' => $this->username]);
    if ($isUsernameTaken) {
      throw new Exception("Username " . $this->username . " is not available.");
    }

    return $this;
  }

  /**
   * Check the user is already in another lobby or not.
   *  
   * @return  self
  */ 
  function isAvailableForLobby() {
    $cursor = $this->collection->findOne(
      [
      'username' => $this->username,
      'token' => $this->token
      ],
      [
        'projection' => [
          'joinedLobby' => 1,
          'joinedGame' => 1
        ]
      ]
    );

    // Authentication is needed.
    if (!$cursor) {
      throw new Exception("Not authenticated!");
    }
    
    // User should not be in any lobby.
    if (!empty($cursor['joinedLobby'])) {
      throw new Exception("You are already in a lobby!");
    }

    // User should not be in any game.
    if (!empty($cursor['joinedGame'])) {
      throw new Exception("You are already in a game!");
    }

    return $this;
  }

  /**
   * Helper function which called after kicking user from lobby after being offline for 1 minute.
   * It does not need token because other users in the lobby call this function for another user.
   * 
   * @return  self
  */ 
  function updateJoinedLobbyWithoutToken() {
    
    $this->collection->updateOne(
      [
        'username' => $this->username
      ],
      [
        '$set' => [
          'joinedLobby' => null
        ]
      ]
    );
    
    return $this;
  }

  /**
   * Helper function which called after kicking user from lobby after being offline for 1 minute.
   * It does not need token because other users in the lobby call this function for another user.
   * 
   * @return  self
  */ 
  function updateJoinedGameWithoutToken($gameId = null) {
    
    $this->collection->updateOne(
      [
        'username' => $this->username
      ],
      [
        '$set' => [
          'joinedGame' => $gameId,
        ]
      ]
    );
    
    return $this;
  }

  /**
   * Helper function which called after kicking user from lobby after being offline for 1 minute.
   * 
   * @return  self
  */ 
  function updateJoinedLobby($lobbyId = null) {
    
    $updateResult = $this->collection->updateOne(
      [
        'username' => $this->username,
        'token' => $this->token
      ],
      [
        '$set' => [
          'joinedLobby' => $lobbyId
        ]
      ]
    );

    if ($updateResult->getModifiedCount() != 1) {
      throw new Exception("Not authenticated!");
    }

    return $this;
  }

  /**
   * Needed for MongoDB\BSON\Persistable
   * 
   * @return  array
  */ 
  function bsonSerialize()
  {
    return [
      '_id' => $this->id,
      'username' => $this->username,
      'password' => $this->password,
      'name' => $this->name,
      'joinedLobby' => $this->joinedLobby,
      'joinedGame' => $this->joinedGame,
      'createdAt' => $this->createdAt,
      'registeredIp' => $this->registeredIp,
      'token' => $this->token,
      'expiresIn' => $this->expiresIn,
      'lastActivenessTimestamp' => $this->lastActivenessTimestamp,
    ];
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */ 
  function bsonUnserialize(array $data)
  { 
    $this->id  = $data['_id'];
    $this->username  = $data['username'];
    $this->password  = $data['password'];
    $this->name  = $data['name'];
    $this->joinedLobby = $data['joinedLobby'];
    $this->joinedGame = $data['joinedGame'];
    $this->createdAt  = $data['createdAt'];
    $this->registeredIp  = $data['registeredIp'];
    $this->token  = $data['token'];
    $this->expiresIn  = $data['expiresIn'];
    $this->lastActivenessTimestamp  = $data['lastActivenessTimestamp'];
  }

  /**
   * Needed for JSONResponse
  */ 
  function jsonArraySerialize()
  {
    return [
      'id' => (string)$this->id,
      'username' => $this->username,
      'name' => $this->name,
      'token' => $this->token
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
   * Get the value of username
   */ 
  public function getUsername()
  {
    return $this->username;
  }

  /**
   * Set the value of username
   *
   * @return  self
   */ 
  public function setUsername($username)
  {
    $this->username = $username;
    return $this;
  }

  /**
   * Get the value of password
   */ 
  public function getPassword()
  {
    return $this->password;
  }

  /**
   * Set the value of password
   *
   * @return  self
   */ 
  public function setPassword($password)
  {
    $this->password = md5($password);
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
   * Get the value of registeredIp
   */ 
  public function getRegisteredIp()
  {
    return $this->registeredIp;
  }

  /**
   * Set the value of registeredIp
   *
   * @return  self
   */ 
  public function setRegisteredIp($registeredIp)
  {
    $this->registeredIp = $registeredIp;

    return $this;
  }

  /**
   * Get the value of token
   */ 
  public function getToken()
  {
    return $this->token;
  }

  /**
   * Set the value of token
   *
   * @return  self
   */ 
  public function setToken($token)
  {
    $this->token = $token;

    return $this;
  }

  /**
   * Get the value of expiresIn
   */ 
  public function getExpiresIn()
  {
    return $this->expiresIn;
  }

  /**
   * Set the value of expiresIn
   *
   * @return  self
   */ 
  public function setExpiresIn($expiresIn)
  {
    $this->expiresIn = $expiresIn;

    return $this;
  }

  /**
   * Get the value of lastActivenessTimestamp
   */ 
  public function getLastActivenessTimestamp()
  {
    return $this->lastActivenessTimestamp;
  }

  /**
   * Set the value of lastActivenessTimestamp
   *
   * @return  self
   */ 
  public function setLastActivenessTimestamp($lastActivenessTimestamp)
  {
    $this->lastActivenessTimestamp = $lastActivenessTimestamp;

    return $this;
  }

  /**
   * Get the value of isAuthenticated
   */ 
  public function getIsAuthenticated()
  {
    return $this->isAuthenticated;
  }

  /**
   * Set the value of isAuthenticated
   *
   * @return  self
   */ 
  public function setIsAuthenticated($isAuthenticated)
  {
    $this->isAuthenticated = $isAuthenticated;

    return $this;
  }

  /**
   * Get the value of joinedLobby
   */ 
  public function getJoinedLobby()
  {
    return $this->joinedLobby;
  }

  /**
   * Set the value of joinedLobby
   *
   * @return  self
   */ 
  public function setJoinedLobby($joinedLobby)
  {
    $this->joinedLobby = $joinedLobby;

    return $this;
  }

  /**
   * Get the value of joinedGame
   */ 
  public function getJoinedGame()
  {
    return $this->joinedGame;
  }

  /**
   * Set the value of joinedGame
   *
   * @return  self
   */ 
  public function setJoinedGame($joinedGame)
  {
    $this->joinedGame = $joinedGame;

    return $this;
  }
}

?>