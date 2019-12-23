<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
require_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/../utils/Utils.php';
require_once dirname(__FILE__) . '/../utils/AllCards.php';
require_once dirname(__FILE__) . '/../utils/AllWonderBoards.php';
require_once dirname(__FILE__) . '/User.php';
require_once dirname(__FILE__) . '/Player.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class Game implements MongoDB\BSON\Persistable, JSONResponse {

  const NUMBER_OF_TURNS = 6;
  const NUMBER_OF_AGES = 3;

  private $collection;
  private $configManager;

  private $allCards;
  private $allWonderBoards;

  private $id;
  private $createdAt;
  private $ageNumber;
  private $turnNumber;
  private $players;
  private $discardedCards;
  private $mode;
  

  function __construct()
  {  
    // Create config manager to manage database and time.
    $this->configManager = new ConfigManager();
    $this->collection = $this->configManager->getDatabase()->games;

    $this->allCards = new AllCards();
    $this->allWonderBoards = new AllWonderBoards();

    $this->id = new MongoDB\BSON\ObjectId;
    $this->createdAt = new MongoDB\BSON\UTCDateTime;
    $this->ageNumber = 1;
    $this->turnNumber = 1;
    $this->players = array();
    $this->discardedCards = array();
  }

  function createGame() {
     // Create new lobby.
     $insertOneResult = $this->collection->insertOne($this);
   
     if ($insertOneResult->getInsertedCount() == 1) {
       return $this;
     } else {
       throw new Exception("An error occured while creating game!");
     }
 
     return $this;
  }

  function constructFromLobby($lobbyUsers) {
    shuffle($lobbyUsers);
    foreach($lobbyUsers as $key => $lobbyUser) {
      // Update every user's joinedGame attribute on database.
      $user = (new User())->setUsername($lobbyUser->getUsername());
      $user->updateJoinedGameWithoutToken($this->id);

      $wonderBoard = $this->allWonderBoards->takeOneWonderBoard();
      $cards = $this->allCards->takeSevenCards($this->ageNumber);
      $player = (new Player())->setName($lobbyUser->getUsername())->setGameId($this->id)->setBoard($wonderBoard)->setCards($cards);
      $player->addMaterials($wonderBoard->getProductions());
      array_push($this->players, $player);
    }

    // Set players' left and right player id
    for ($i = 0; $i < count($this->players); ++$i) {
      ($this->players)[$i]->setLeftPlayerId((string)($this->players)[($i+count($this->players)-1) % count($this->players)]->getId());
      ($this->players)[$i]->setRightPlayerId((string)($this->players)[($i+1) % count($this->players)]->getId());
    }

    return $this;
  }

  /**
   * 
   * 
   */
  function commerce($isWithLeft, $buyerPlayerName, $commerceMaterials){
    $buyerPlayer = $this->findPlayerByName($buyerPlayerName); 
    $sellerPlayer = $isWithLeft ? $this->findPlayerById($buyerPlayer->getLeftPlayerId()) : $this->findPlayerById($buyerPlayer->getRightPlayerId());
    $buyerPlayer->commerce($isWithLeft, $sellerPlayer, $commerceMaterials);
    
    $this->updatePlayers();

    return $this;
  }

  function endGame(){
    
  }

  function doMilitaryConflicts($player1, $player2, &$player1wins){

  }

  function shufflePlaces(){
    shuffle($this->players);
  }

  function endAge(){
    if($this->getAgeNumber() < Game::NUMBER_OF_AGES){
      // do military conflict
      foreach($this->players as $key => $player) {
        // since the players do military conflict with their neighbours only if each player does this with its right neighboors, all the conflicts will be done
        $rightPlayer = $this->findPlayerById($player->getRightPlayerId());
        $playerWinsToRight = false;
        $this->doMilitaryConflicts($player, $rightPlayer, $playerWinsToRight);
        if($playerWinsToRight && $this->getMode()->getLoot()){

        }
        else if($playerWinsToRight && !($this->getMode()->getLoot())){

        }
        else{
          
        }
      }            

      // if shuffle players mode is active then shuffle the places
      if($this->getMode()->getShufflePlaces())
        $this->shufflePlaces();
    }
    else{
      $this->endGame();
    }   
  }

  /**
   * @author: Meryem Banu Cavlak
   */
  function endTurn(){
    if($this->getTurnNumber < Game::NUMBER_OF_TURNS){
      
      $this->moveCards();
  
      // update the turn
      $this->turnNumber = $this->turnNumber + 1;

      // update player attributes
      foreach($this->players as $key => $player) {
        $player->setIsPlayedForTurn(false);
        $player->setCoin($player->getCoin() + $player->getCoinLeftCommerceSell() + $player->getCoinRightCommerceSell() - $player->getCoinLeftCommerceBuy() - $player->getCoinRightCommerceBuy());
        $player->setCoinLeftCommerceSell(0)->setCoinRightCommerceSell(0)->setCoinLeftCommerceBuy(0)->setCoinRightCommerceBuy(0);
        $player->setProducedMaterialsAfterCommerce( $player->getProducedMaterials() );
        $player->setProducedOptionalMaterialsAfterCommerce( $player->getProducedOptionalMaterials() );
      }

      $this->updateTurnNumber();
    }
    else{
      $this->endAge();
    }

    return $this;
  }

  function moveCards() {
    // For age 1 and age 2
    if ($this->getAgeNumber() % 2 == 1) {
      // Move cards with left
      $tempCards =  $this->players[0]->getCards();
      for($i = 0; $i < count($this->players) - 1; ++$i) {
        $this->players[$i]->setCards($this->players[$i + 1]->getCards());
      }
      $this->players[count($this->players) - 1]->setCards($tempCards);
    } else {
      // Move cards with right
      $tempCards = $this->players[count($this->players) - 1]->getCards();
      for($i = count($this->players) - 1; $i > 0; --$i) {
        $this->players[$i]->setCards($this->players[$i - 1]->getCards());
      }
      $this->players[0]->setCards($tempCards);
    }
  }

   /**
   * 
   * @author Banu Cavlak
   */
  function useCard($playerName, $cardId, $selectionType, $freeBuilding) {
    $isValid = false;
    $player = $this->findPlayerByName($playerName); 

    if ($player->getIsPlayedForTurn()) {
      throw new Exception('Player ' . $player->getName() . ' has already played this turn.' );
    }

    $card = $player->findCard($cardId); 

    $leftPlayerCards = $this->findPlayerById($player->getLeftPlayerId())->getBoard()->getCards(); 
    $rightPlayerCards = $this->findPlayerById($player->getRightPlayerId())->getBoard()->getCards();
    
    // Discard the card
    if($selectionType == 0){
      // Add this card to discardedCards.
      array_push($this->discardedCards, $card);
      //Give 3 coin to player.
      $player->setCoin($player->getCoin() + 3);

      $isValid = true;
    }

    // Build the structure
    else if($selectionType == 1){
      $isValid = $player->addCard($card, $freeBuilding, $leftPlayerCards, $rightPlayerCards);
    }

    // Build a wonder stage
    else if($selectionType == 2){
      $isValid = $player->increaseWonderStage();
    }

    if (!$isValid) {
      if ($this->mode->getInvalidMovePenalty()) {
        $player->penalizePlayer();
      }
      $this->updatePlayers();
      throw new Exception('Invalid use of card!');
    }

    $player->setIsPlayedForTurn(true);
    $player->dismissCard($cardId); 

    $isAllPlayersReady = true;
    foreach ($this->players as $key => $player) {
      $isAllPlayersReady = $isAllPlayersReady && $player->getIsPlayedForTurn();
    }

    if ($isAllPlayersReady) {
      $this->endTurn();
    }

    $this->updatePlayers();
    return $this;
  }


  /**
   * 
   * 
   */
  function retrieveGameDataViaId() {

    $cursor = $this->collection->findOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ]
    );

    if (!$cursor) {
      throw new Exception("Invalid game id!");
    }

    $this->bsonUnserialize($cursor->bsonSerialize());
   

    return $this;

  }

  function changePlayersOrder($playerName) {

    $indexOfCurrentPlayer = -1;
    for ($i = 0; $i < count($this->players); ++$i) {
      if ($this->players[$i]->getName() == $playerName) {
        $indexOfCurrentPlayer = $i;
      }
    }

    if ($indexOfCurrentPlayer == -1) {
      throw new Exception('Player $playerName is not found!');
    }

    $tempPlayers = array();
    for ($i = 0; $i < count($this->players); ++$i) {
      $tempPlayers[$i] = $this->players[($indexOfCurrentPlayer + $i) % count($this->players)];
    }

    $this->players = $tempPlayers;

    return $this;
  }

    /**
   * 
   * 
   */
  private function findPlayerByName($playerName): Player {
    foreach($this->players as $key => $player) {
      if ($player->getName() == $playerName) {
        return $player;
      }
    }

    throw new Exception('Player \'' . $playerName . '\' is not found in the game!');
  }

  /**
   * 
   * 
   */
  private function findPlayerById($playerId): Player {
    foreach($this->players as $key => $player) {
      if ($player->getId() == $playerId) {
        return $player;
      }
    }

    throw new Exception('Player with given id \'' . $playerId . '\' is not found in the game!');
  }

   /**
   * 
   * 
   */
  private function updatePlayers() {
    $this->collection->updateOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ],
      [
        '$set' => [
          'players' => $this->players,
        ]
      ]
    );

    return $this;
  }

  /**
   * 
   * 
   */
  private function updateTurnNumber() {
    $this->collection->updateOne(
      [
        '_id' => new MongoDB\BSON\ObjectId($this->id)
      ],
      [
        '$set' => [
          'turnNumber' => $this->turnNumber,
        ]
      ]
    );

    return $this;
  }

  function bsonSerialize()
  {
    return [
      '_id' => $this->id,
      'createdAt' => $this->createdAt,
      'ageNumber' => $this->ageNumber,
      'turnNumber' => $this->turnNumber,
      'players' => $this->players,
      'discardedCards' => $this->discardedCards,
      'mode' => $this->mode,
    ];
  }

  function bsonUnserialize(array $data)
  {
    $this->id  = $data['_id'];
    $this->createdAt  = $data['createdAt'];
    $this->ageNumber  = $data['ageNumber'];
    $this->turnNumber  = $data['turnNumber'];
    $this->players  = $data['players'];
    $this->discardedCards  = $data['discardedCards'];
    $this->mode  = $data['mode'];
  }

  function jsonArraySerialize()
  {
    $players = array();
    foreach($this->players as $key => $player) {
      array_push($players, $player->jsonArraySerialize());
    }

    return [
      'id' => (string) $this->id,
      'ageNumber' => $this->ageNumber,
      'turnNumber' => $this->turnNumber,
      'players' => $players,
      'discardedCards' => $this->discardedCards,
      'mode' => $this->mode,
    ];
  }

  // Getters and Setters

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
   * Get the value of turnNumber
   */ 
  public function getTurnNumber()
  {
    return $this->turnNumber;
  }

  /**
   * Set the value of turnNumber
   *
   * @return  self
   */ 
  public function setTurnNumber($turnNumber)
  {
    $this->turnNumber = $turnNumber;

    return $this;
  }

  /**
   * Get the value of ageNumber
   */ 
  public function getAgeNumber()
  {
    return $this->ageNumber;
  }

  /**
   * Set the value of ageNumber
   *
   * @return  self
   */ 
  public function setAgeNumber($ageNumber)
  {
    $this->ageNumber = $ageNumber;

    return $this;
  }

  /**
   * Get the value of players
   */ 
  public function getPlayers()
  {
    return $this->players;
  }

  /**
   * Set the value of players
   *
   * @return  self
   */ 
  public function setPlayers($players)
  {
    $this->players = $players;

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
   * Get the value of allCards
   */ 
  public function getAllCards()
  {
    return $this->allCards;
  }

  /**
   * Set the value of allCards
   *
   * @return  self
   */ 
  public function setAllCards($allCards)
  {
    $this->allCards = $allCards;

    return $this;
  }

  /**
   * Get the value of allWonderBoards
   */ 
  public function getAllWonderBoards()
  {
    return $this->allWonderBoards;
  }

  /**
   * Set the value of allWonderBoards
   *
   * @return  self
   */ 
  public function setAllWonderBoards($allWonderBoards)
  {
    $this->allWonderBoards = $allWonderBoards;

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
}


?>