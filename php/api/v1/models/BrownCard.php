<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/Card.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class BrownCard extends Card implements MongoDB\BSON\Persistable, JSONResponse {

  private $lumber;
  private $stone;
  private $clay;
  private $ore;
  
  function __construct()
  {
    $this->id = "";
    $this->color = "brown";
    $this->name = "";
    $this->cost = 0;
    $this->age  = 0;
    $this->playerLimit = 0;
    $this->friendCard = null;
    $this->lumber = 0;
    $this->stone = 0;
    $this->clay = 0;
    $this->ore = 0;
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
      'lumber' => $this->lumber,
      'stone' => $this->stone,
      'clay' => $this->clay,
      'ore' => $this->ore,
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
    $this->lumber  = $data['lumber'];
    $this->stone  = $data['stone'];
    $this->clay  = $data['clay'];
    $this->ore  = $data['ore'];
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
  
  // Getters and setters


  /**
   * Get the value of lumber
   */ 
  public function getLumber()
  {
    return $this->lumber;
  }

  /**
   * Set the value of lumber
   *
   * @return  self
   */ 
  public function setLumber($lumber)
  {
    $this->lumber = $lumber;

    return $this;
  }

  /**
   * Get the value of stone
   */ 
  public function getStone()
  {
    return $this->stone;
  }

  /**
   * Set the value of stone
   *
   * @return  self
   */ 
  public function setStone($stone)
  {
    $this->stone = $stone;

    return $this;
  }

  /**
   * Get the value of ore
   */ 
  public function getOre()
  {
    return $this->ore;
  }

  /**
   * Set the value of ore
   *
   * @return  self
   */ 
  public function setOre($ore)
  {
    $this->ore = $ore;

    return $this;
  }

  /**
   * Get the value of clay
   */ 
  public function getClay()
  {
    return $this->clay;
  }

  /**
   * Set the value of clay
   *
   * @return  self
   */ 
  public function setClay($clay)
  {
    $this->clay = $clay;

    return $this;
  }

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