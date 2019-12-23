<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

// @author: Meryem Banu Cavlak
class WonderBoard implements MongoDB\BSON\Persistable, JSONResponse {

  private $id;
  private $cards;
  private $wonderStage;
  private $wonderName;
  private $productions;
  private $stageCosts;
  private $stageWorth;

  function __construct($wonderName = "", $id = "", $cards = array(), $wonderStage = 0, $productions = array(), $stageCosts = array(), $stageWorth = array()) {
    $this->id = $id;
    $this->cards = $cards;
    $this->wonderStage = $wonderStage;
    $this->wonderName = $wonderName;
    $this->productions = $productions;
    $this->stageCosts = $stageCosts;
    $this->stageWorth = $stageWorth;
  }

  function addCardToBoard($cardToUse) {
    array_push($this->cards, $cardToUse);
    return $this;
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonSerialize()
  {
    return [
      'id' => $this->id,
      'cards' => $this->cards,
      'wonderStage' => $this->wonderStage,
      'wonderName' => $this->wonderName,
      'productions' => $this->productions,
      'stageCosts' => $this->stageCosts,
      'stageWorth' => $this->stageWorth,
    ];
  }

  /**
   * Needed for MongoDB\BSON\Persistable
  */
  function bsonUnserialize(array $data)
  {
    $this->id  = $data['id'];
    $this->cards  = (array)$data['cards'];
    $this->wonderStage  = $data['wonderStage'];
    $this->wonderName  = $data['wonderName'];
    $this->productions  = $data['productions'];
    $this->stageCosts  = $data['stageCosts'];
    $this->stageWorth  = $data['stageWorth'];
  }

  /**
   * Needed for JSONResponse
  */
  function jsonArraySerialize()
  {
    return [
      'id' => $this->id,
      'cards' => $this->cards,
      'stage' => $this->wonderStage,
      'name' => $this->wonderName,
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
   * Get the value of cards
   */ 
  public function getCards()
  {
    return $this->cards;
  }

  /**
   * Set the value of cards
   *
   * @return  self
   */ 
  public function setCards($cards)
  {
    $this->cards = $cards;

    return $this;
  }

  /**
   * Get the value of wonderStage
   */ 
  public function getWonderStage()
  {
    return $this->wonderStage;
  }

  /**
   * Set the value of wonderStage
   *
   * @return  self
   */ 
  public function setWonderStage($wonderStage)
  {
    $this->wonderStage = $wonderStage;

    return $this;
  }

  /**
   * Get the value of wonderName
   */ 
  public function getWonderName()
  {
    return $this->wonderName;
  }

  /**
   * Set the value of wonderName
   *
   * @return  self
   */ 
  public function setWonderName($wonderName)
  {
    $this->wonderName = $wonderName;

    return $this;
  }

  /**
   * Get the value of productions
   */ 
  public function getProductions()
  {
    return $this->productions;
  }

  /**
   * Set the value of productions
   *
   * @return  self
   */ 
  public function setProductions($productions)
  {
    $this->productions = $productions;

    return $this;
  }

  /**
   * Get the value of stageWorth
   */ 
  public function getStageWorth()
  {
    return $this->stageWorth;
  }

  /**
   * Set the value of stageWorth
   *
   * @return  self
   */ 
  public function setStageWorth($stageWorth)
  {
    $this->stageWorth = $stageWorth;

    return $this;
  }

  /**
   * Get the value of stageCosts
   */ 
  public function getStageCosts()
  {
    return $this->stageCosts;
  }

  /**
   * Set the value of stageCosts
   *
   * @return  self
   */ 
  public function setStageCosts($stageCosts)
  {
    $this->stageCosts = $stageCosts;

    return $this;
  }
}
