<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/Card.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class YellowCard extends Card implements MongoDB\BSON\Persistable, JSONResponse {

  private $goldCoin;
  private $leftCostReduction;
  private $rightCostReduction;
  private $clay;
  private $stone;
  private $lumber;
  private $ore;
  private $loom;
  private $glassworks;
  private $press;
  private $goldVictoryForBrown;
  private $goldVictoryForYellow;
  private $goldVictoryForGray;
  private $goldForBrown;
  private $goldForGray;
  private $goldVictoryForWonderStages;

  
  function __construct()
  {
    $this->id = "";
    $this->color = "yellow";
    $this->name = "";
    $this->cost = 0;
    $this->age  = 0;
    $this->playerLimit = 0;
    $this->friendCard = null;
    $this->goldCoin = 0;
    $this->leftCostReduction = false;
    $this->rightCostReduction = false;
    $this->clay = 0;
    $this->stone = 0;
    $this->lumber = 0;
    $this->ore = 0;
    $this->loom = 0;
    $this->glassworks = 0;
    $this->press = 0;
    $this->goldVictoryForBrown = false;
    $this->goldVictoryForYellow = false;
    $this->goldVictoryForGray = false;
    $this->goldForGray = false;
    $this->goldForBrown = false;
    $this->goldVictoryForWonderStages = false;
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
      'goldCoin' => $this->goldCoin,
      'leftCostReduction' => $this->leftCostReduction,
      'rightCostReduction' => $this->rightCostReduction,
      'clay' => $this->clay,
      'stone' => $this->stone,
      'lumber' => $this->lumber,
      'ore' => $this->ore,
      'loom' => $this->loom,
      'glassworks' => $this->glassworks,
      'press' => $this->press,
      'goldVictoryForBrown' => $this->goldVictoryForBrown,
      'goldVictoryForYellow' => $this->goldVictoryForYellow,
      'goldVictoryForGray' => $this->goldVictoryForGray,
      'goldForGray' => $this->goldForGray,
      'goldForBrown' => $this->goldForBrown,
      'goldVictoryForWonderStages' => $this->goldVictoryForWonderStages,
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
    $this->goldCoin  = $data['goldCoin'];
    $this->leftCostReduction  = $data['leftCostReduction'];
    $this->rightCostReduction  = $data['rightCostReduction'];
    $this->clay  = $data['clay'];
    $this->stone  = $data['stone'];
    $this->lumber  = $data['lumber'];
    $this->ore  = $data['ore'];
    $this->loom  = $data['loom'];
    $this->glassworks  = $data['glassworks'];
    $this->press  = $data['press'];
    $this->pregoldVictoryForBrownss  = $data['goldVictoryForBrown'];
    $this->goldVictoryForYellow  = $data['goldVictoryForYellow'];
    $this->goldVictoryForGray  = $data['goldVictoryForGray'];
    $this->goldForGray  = $data['goldForGray'];
    $this->goldForBrown  = $data['goldForBrown'];
    $this->goldVictoryForWonderStages  = $data['goldVictoryForWonderStages'];
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
   * Get the value of goldCoin
   */ 
  public function getGoldCoin()
  {
    return $this->goldCoin;
  }

  /**
   * Set the value of goldCoin
   *
   * @return  self
   */ 
  public function setGoldCoin($goldCoin)
  {
    $this->goldCoin = $goldCoin;

    return $this;
  }

  /**
   * Get the value of leftCostReduction
   */ 
  public function getLeftCostReduction()
  {
    return $this->leftCostReduction;
  }

  /**
   * Set the value of leftCostReduction
   *
   * @return  self
   */ 
  public function setLeftCostReduction($leftCostReduction)
  {
    $this->leftCostReduction = $leftCostReduction;

    return $this;
  }

  /**
   * Get the value of rightCostReduction
   */ 
  public function getRightCostReduction()
  {
    return $this->rightCostReduction;
  }

  /**
   * Set the value of rightCostReduction
   *
   * @return  self
   */ 
  public function setRightCostReduction($rightCostReduction)
  {
    $this->rightCostReduction = $rightCostReduction;

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
   * Get the value of goldVictoryForBrown
   */ 
  public function getGoldVictoryForBrown()
  {
    return $this->goldVictoryForBrown;
  }

  /**
   * Set the value of goldVictoryForBrown
   *
   * @return  self
   */ 
  public function setGoldVictoryForBrown($goldVictoryForBrown)
  {
    $this->goldVictoryForBrown = $goldVictoryForBrown;

    return $this;
  }

  /**
   * Get the value of goldVictoryForYellow
   */ 
  public function getGoldVictoryForYellow()
  {
    return $this->goldVictoryForYellow;
  }

  /**
   * Set the value of goldVictoryForYellow
   *
   * @return  self
   */ 
  public function setGoldVictoryForYellow($goldVictoryForYellow)
  {
    $this->goldVictoryForYellow = $goldVictoryForYellow;

    return $this;
  }

  /**
   * Get the value of goldForGray
   */ 
  public function getGoldForGray()
  {
    return $this->goldForGray;
  }

  /**
   * Set the value of goldForGray
   *
   * @return  self
   */ 
  public function setGoldForGray($goldForGray)
  {
    $this->goldForGray = $goldForGray;

    return $this;
  }

  /**
   * Get the value of goldVictoryForGray
   */ 
  public function getGoldVictoryForGray()
  {
    return $this->goldVictoryForGray;
  }

  /**
   * Set the value of goldVictoryForGray
   *
   * @return  self
   */ 
  public function setGoldVictoryForGray($goldVictoryForGray)
  {
    $this->goldVictoryForGray = $goldVictoryForGray;

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

  /**
   * Get the value of goldVictoryForWonderStages
   */ 
  public function getGoldVictoryForWonderStages()
  {
    return $this->goldVictoryForWonderStages;
  }

  /**
   * Set the value of goldVictoryForWonderStages
   *
   * @return  self
   */ 
  public function setGoldVictoryForWonderStages($goldVictoryForWonderStages)
  {
    $this->goldVictoryForWonderStages = $goldVictoryForWonderStages;

    return $this;
  }

  /**
   * Get the value of goldForBrown
   */ 
  public function getGoldForBrown()
  {
    return $this->goldForBrown;
  }

  /**
   * Set the value of goldForBrown
   *
   * @return  self
   */ 
  public function setGoldForBrown($goldForBrown)
  {
    $this->goldForBrown = $goldForBrown;

    return $this;
  }
}

?>