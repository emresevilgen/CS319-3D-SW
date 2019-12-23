<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class Card implements MongoDB\BSON\Persistable, JSONResponse {
  private $id;
  private $color;
  private $name;
  private $cost;
  private $age;
  private $playerLimit;
  private $friendCard;

  function __construct()
  {
    $this->id = "";
    $this->color = "";
    $this->name = "";
    $this->cost = 0;
    $this->age  = 0;
    $this->playerLimit = 0;
    $this->friendCard = null;
  }

  // Needed for MongoDB\BSON\Persistable
  function bsonSerialize()
  {
    return [
      '_id' => $this->id,
      'color' => $this->color,
      'name' => $this->name,
      'cost' => $this->cost,
      'friendCard' => $this->friendCard,
      'age' => $this->age,
      'playerLimit' => $this->playerLimit,
    ];
  }

  // Needed for MongoDB\BSON\Persistable
  function bsonUnserialize(array $data)
  {
    $this->id  = $data['_id'];
    $this->color  = $data['color'];
    $this->name  = $data['name'];
    $this->cost  = $data['cost'];
    $this->playerLimit  = $data['playerLimit'];
    $this->age  = $data['age'];
    $this->friendCard  = $data['friendCard'];
  }

  // Needed for JSONResponse
  function jsonArraySerialize()
  {
    return [
      'id' => $this->id,
      'color' => $this->color,
      'name' => $this->name,
    ];
  }

  /**
   * Determine that the card can be picked or not by comparing cost and user' materials. 
   * 
   * @return bool
   */
  function canBePicked($materials) : bool {
    //TO DO
    return false;
  }

  /**
   * The card is appropriate for game, 
   * if its player limit less or equal than number of players in the game.
   * 
   * @return bool
   */
  function isApproptiateForGame($numberOfPlayers) {
    return $this->playerLimit <= $numberOfPlayers;
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
   * Get the value of color
   */ 
  public function getColor()
  {
    return $this->color;
  }

  /**
   * Set the value of color
   *
   * @return  self
   */ 
  public function setColor($color)
  {
    $this->color = $color;

    return $this;
  }

  /**
   * Get the value of cost
   */ 
  public function getCost()
  {
    return $this->cost;
  }

  /**
   * Set the value of cost
   *
   * @return  self
   */ 
  public function setCost($cost)
  {
    $this->cost = $cost;

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
   * Get the value of age
   */ 
  public function getAge()
  {
    return $this->age;
  }

  /**
   * Set the value of age
   *
   * @return  self
   */ 
  public function setAge($age)
  {
    $this->age = $age;

    return $this;
  }

  /**
   * Get the value of playerLimit
   */ 
  public function getPlayerLimit()
  {
    return $this->playerLimit;
  }

  /**
   * Set the value of playerLimit
   *
   * @return  self
   */ 
  public function setPlayerLimit($playerLimit)
  {
    $this->playerLimit = $playerLimit;

    return $this;
  }

  /**
   * Get the value of friendCard
   */ 
  public function getFriendCard()
  {
    return $this->friendCard;
  }

  /**
   * Set the value of friendCard
   *
   * @return  self
   */ 
  public function setFriendCard($friendCard)
  {
    $this->friendCard = $friendCard;

    return $this;
  }

  
} 


?>