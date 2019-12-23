<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

// @author: Meryem Banu Cavlak
class Worth implements MongoDB\BSON\Persistable, JSONResponse {

  private $victory;
  private $shield;
  private $coin;
  private $card;
  private $clay;
  private $ore;
  private $stone;
  private $lumber;
  private $compass;
  private $wheel;
  private $script;

  function __construct($victory = 0, $shield = 0, $coin = 0, $card = "", $clay = 0, $ore = 0, $stone = 0, $lumber = 0, $compass = 0, $wheel = 0, $script = 0) {
    $this->victory = $victory;
    $this->shield = $shield;
    $this->coin = $coin;
    $this->card = $card;
    $this->clay = $clay;
    $this->ore = $ore;
    $this->stone = $stone;
    $this->lumber = $lumber;
    $this->compass = $compass;
    $this->wheel = $wheel;
    $this->script = $script;
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonSerialize()
  {
    return [
      'victory' => $this->victory,
      'shield' => $this->shield,
      'coin' => $this->coin,
      'card' => $this->card,
      'clay' => $this->clay,
      'ore' => $this->ore,
      'stone' => $this->stone,
      'lumber' => $this->lumber,
      'compass' => $this->compass,
      'wheel' => $this->wheel,
      'script' => $this->script,
    ];
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonUnserialize(array $data)
  {
    $this->victory  = $data['victory'];
    $this->shield  = $data['shield'];
    $this->coin  = $data['coin'];
    $this->card  = $data['card'];
    $this->clay  = $data['clay'];
    $this->ore  = $data['ore'];
    $this->stone  = $data['stone'];
    $this->lumber  = $data['lumber'];
    $this->compass  = $data['compass'];
    $this->wheel  = $data['wheel'];
    $this->script  = $data['script'];
  }

  /**
   * Needed for JSONResponse
  */
  function jsonArraySerialize()
  {
    return [
      'victory' => $this->victory,
      'shield' => $this->shield,
      'coin' => $this->coin,
      'card' => $this->card,
      'clay' => $this->clay,
      'ore' => $this->ore,
      'stone' => $this->stone,
      'lumber' => $this->lumber,
      'compass' => $this->compass,
      'wheel' => $this->wheel,
    	'script' => $this->script,
    ];
  }


  // Getters and Setters

  

  /**
   * Get the value of victory
   */ 
  public function getVictory()
  {
    return $this->victory;
  }

  /**
   * Set the value of victory
   *
   * @return  self
   */ 
  public function setVictory($victory)
  {
    $this->victory = $victory;

    return $this;
  }

  /**
   * Get the value of shield
   */ 
  public function getShield()
  {
    return $this->shield;
  }

  /**
   * Set the value of shield
   *
   * @return  self
   */ 
  public function setShield($shield)
  {
    $this->shield = $shield;

    return $this;
  }

  /**
   * Get the value of coin
   */ 
  public function getCoin()
  {
    return $this->coin;
  }

  /**
   * Set the value of coin
   *
   * @return  self
   */ 
  public function setCoin($coin)
  {
    $this->coin = $coin;

    return $this;
  }

  /**
   * Get the value of card
   */ 
  public function getCard()
  {
    return $this->card;
  }

  /**
   * Set the value of card
   *
   * @return  self
   */ 
  public function setCard($card)
  {
    $this->card = $card;

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
   * Get the value of compass
   */ 
  public function getCompass()
  {
    return $this->compass;
  }

  /**
   * Set the value of compass
   *
   * @return  self
   */ 
  public function setCompass($compass)
  {
    $this->compass = $compass;

    return $this;
  }

  /**
   * Get the value of script
   */ 
  public function getScript()
  {
    return $this->script;
  }

  /**
   * Set the value of script
   *
   * @return  self
   */ 
  public function setScript($script)
  {
    $this->script = $script;

    return $this;
  }

  /**
   * Get the value of wheel
   */ 
  public function getWheel()
  {
    return $this->wheel;
  }

  /**
   * Set the value of wheel
   *
   * @return  self
   */ 
  public function setWheel($wheel)
  {
    $this->wheel = $wheel;

    return $this;
  }
}
