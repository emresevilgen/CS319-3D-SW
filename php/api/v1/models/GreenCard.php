<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/Card.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class GreenCard extends Card implements MongoDB\BSON\Persistable, JSONResponse {

  private $compassPoints;
  private $wheelPoints;
  private $scriptPoints;
  
  function __construct()
  {
    $this->id = "";
    $this->color = "green";
    $this->name = "";
    $this->cost = 0;
    $this->age  = 0;
    $this->playerLimit = 0;
    $this->friendCard = null;
    $this->compassPoints = 0;
    $this->wheelPoints = 0;
    $this->scriptPoints = 0;
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
      'compassPoints' => $this->compassPoints,
      'wheelPoints' => $this->wheelPoints,
      'scriptPoints' => $this->scriptPoints,
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
    $this->compassPoints  = $data['compassPoints'];
    $this->wheelPoints  = $data['wheelPoints'];
    $this->scriptPoints  = $data['scriptPoints'];
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
   * Get the value of compassPoints
   */ 
  public function getCompassPoints()
  {
    return $this->compassPoints;
  }

  /**
   * Set the value of compassPoints
   *
   * @return  self
   */ 
  public function setCompassPoints($compassPoints)
  {
    $this->compassPoints = $compassPoints;

    return $this;
  }

  /**
   * Get the value of wheelPoints
   */ 
  public function getWheelPoints()
  {
    return $this->wheelPoints;
  }

  /**
   * Set the value of wheelPoints
   *
   * @return  self
   */ 
  public function setWheelPoints($wheelPoints)
  {
    $this->wheelPoints = $wheelPoints;

    return $this;
  }

  /**
   * Get the value of scriptPoints
   */ 
  public function getScriptPoints()
  {
    return $this->scriptPoints;
  }

  /**
   * Set the value of scriptPoints
   *
   * @return  self
   */ 
  public function setScriptPoints($scriptPoints)
  {
    $this->scriptPoints = $scriptPoints;

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