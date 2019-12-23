<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/Card.php';

// @author: Meryem Banu Cavlak
class GrayCard extends Card implements MongoDB\BSON\Persistable, JSONResponse {

  private $loom;
  private $glassworks;
  private $press;
  
  function __construct()
  {
    $this->id = "";
    $this->color = "gray";
    $this->name = "";
    $this->cost = 0;
    $this->age  = 0;
    $this->playerLimit = 0;
    $this->friendCard = null;
    $this->loom = 0;
    $this->glassworks = 0;
    $this->press = 0;
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
      'loom' => $this->loom,
      'glassworks' => $this->glassworks,
      'press' => $this->press,
    ];
  }
 
  // Needed for MongoDB\BSON\Persistable
  function bsonUnserialize(array $data)
  {
    $this->id = $data['_id'];
    $this->color = $data['color'];
    $this->name = $data['name'];
    $this->cost = $data['cost'];
    $this->playerLimit = $data['playerLimit'];
    $this->age = $data['age'];
    $this->friendCard = $data['friendCard'];
    $this->loom = $data['loom'];
    $this->glassworks = $data['glassworks'];
    $this->press = $data['press'];
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
   * Get the value of loom
   */ 
  public function getLoom()
  {
    return $this->loom;
  }

  /**
   * Set the value of loom
   *
   * @return  self
   */ 
  public function setLoom($loom)
  {
    $this->loom = $loom;

    return $this;
  }

  /**
   * Get the value of glassworks
   */ 
  public function getGlassworks()
  {
    return $this->glassworks;
  }

  /**
   * Set the value of glassworks
   *
   * @return  self
   */ 
  public function setGlassworks($glassworks)
  {
    $this->glassworks = $glassworks;

    return $this;
  }

  /**
   * Get the value of press
   */ 
  public function getPress()
  {
    return $this->press;
  }

  /**
   * Set the value of press
   *
   * @return  self
   */ 
  public function setPress($press)
  {
    $this->press = $press;

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
