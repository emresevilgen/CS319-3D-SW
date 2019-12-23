<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

// @author: Meryem Banu Cavlak
class Materials implements MongoDB\BSON\Persistable, JSONResponse {

  private $coin;
  private $clay;
  private $ore;
  private $stone;
  private $lumber;
  private $press;
  private $loom;
  private $glassworks;

  function __construct($coin = 0, $clay = 0, $ore = 0, $stone = 0, $lumber = 0, $press = 0, $loom = 0, $glassworks = 0) {
    $this->coin = $coin;
    $this->clay = $clay;
    $this->ore = $ore;
    $this->stone = $stone;
    $this->lumber = $lumber;
    $this->press = $press;
    $this->loom = $loom;
    $this->glassworks = $glassworks;
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonSerialize()
  {
    return [
      'coin' => $this->coin,
      'clay' => $this->clay,
      'ore' => $this->ore,
      'stone' => $this->stone,
      'lumber' => $this->lumber,
      'press' => $this->press,
      'loom' => $this->loom,
      'glassworks' => $this->glassworks,
    ];
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonUnserialize(array $data)
  {
    $this->coin  = $data['coin'];
    $this->clay  = $data['clay'];
    $this->ore  = $data['ore'];
    $this->stone  = $data['stone'];
    $this->lumber  = $data['lumber'];
    $this->press  = $data['press'];
    $this->loom  = $data['loom'];
    $this->glassworks  = $data['glassworks'];
  }

  /**
   * Needed for JSONResponse
  */
  function jsonArraySerialize()
  {
    return [
      'coin' => $this->coin,
      'clay' => $this->clay,
      'ore' => $this->ore,
      'stone' => $this->stone,
      'lumber' => $this->lumber,
      'press' => $this->press,
      'loom' => $this->loom,
    	'glassworks' => $this->glassworks,
    ];
  }

  // Getters and Setters

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
}
