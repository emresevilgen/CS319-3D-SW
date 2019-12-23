<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class Mode implements MongoDB\BSON\Persistable, JSONResponse {

  private $shufflePlaces;
  private $secretSkills;
  private $invalidMovePenalty;
  private $loot;

  function __construct($shufflePlaces = false, $secretSkills = false, $invalidMovePenalty = false, $loot = false) {
    $this->shufflePlaces = $shufflePlaces;
    $this->secretSkills = $secretSkills;
    $this->invalidMovePenalty = $invalidMovePenalty;
    $this->loot = $loot;
  }

  function bsonSerialize()
  {
    return [
      'shufflePlaces' => $this->shufflePlaces,
      'secretSkills' => $this->secretSkills,
      'invalidMovePenalty' => $this->invalidMovePenalty,
      'loot' => $this->loot,
    ];
  }

  function bsonUnserialize(array $data)
  {
    $this->shufflePlaces  = $data['shufflePlaces'];
    $this->secretSkills  = $data['secretSkills'];
    $this->invalidMovePenalty  = $data['invalidMovePenalty'];
    $this->loot  = $data['loot'];
  }

  function jsonArraySerialize()
  {
    return [
      'shufflePlaces' => $this->shufflePlaces,
      'secretSkills' => $this->secretSkills,
      'invalidMovePenalty' => $this->invalidMovePenalty,
      'loot' => $this->loot,
    ];
  }


  // Getters and Setters

  /**
   * Get the value of invalidMovePenalty
   */ 
  public function getInvalidMovePenalty()
  {
    return $this->invalidMovePenalty;
  }

  /**
   * Set the value of invalidMovePenalty
   *
   * @return  self
   */ 
  public function setInvalidMovePenalty($invalidMovePenalty)
  {
    $this->invalidMovePenalty = $invalidMovePenalty;

    return $this;
  }

  /**
   * Get the value of shufflePlaces
   */ 
  public function getShufflePlaces()
  {
    return $this->shufflePlaces;
  }

  /**
   * Set the value of shufflePlaces
   *
   * @return  self
   */ 
  public function setShufflePlaces($shufflePlaces)
  {
    $this->shufflePlaces = $shufflePlaces;

    return $this;
  }

  /**
   * Get the value of secretSkills
   */ 
  public function getSecretSkills()
  {
    return $this->secretSkills;
  }

  /**
   * Set the value of secretSkills
   *
   * @return  self
   */ 
  public function setSecretSkills($secretSkills)
  {
    $this->secretSkills = $secretSkills;

    return $this;
  }

  /**
   * Get the value of loot
   */ 
  public function getLoot()
  {
    return $this->loot;
  }

  /**
   * Set the value of loot
   *
   * @return  self
   */ 
  public function setLoot($loot)
  {
    $this->loot = $loot;

    return $this;
  }
}

?>