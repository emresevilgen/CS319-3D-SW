<?php

require_once dirname(__FILE__) . '/../models/WonderBoard.php';
require_once dirname(__FILE__) . '/../models/Materials.php';
require_once dirname(__FILE__) . '/../models/Worth.php';

/**
 * @author: Meryem Banu Cavlak
 * @author: Aziz Utku Kağıtcı
 */ 
class AllWonderBoards {

  private $wonderBoards;

  function __construct()
  {
    $this->prepareWonderBoards();
    $this->shuffleWonderBoards();
  }

  /**
   * Give one wonder board and remove it from array.
   *
   * @return WonderBoard
   */ 
  function takeOneWonderBoard() {
    if (isset($this->wonderBoards)) {
      //Get last wonder board in the array of wonder boards.
      $lastIndex = count($this->wonderBoards) - 1;
      $wonderBoard = $this->wonderBoards[$lastIndex];
      unset($this->wonderBoards[$lastIndex]);
      return $wonderBoard;
    } else {
      throw new Exception("Wonder boards array not initiliazed!");
    }
  }

  
  /**
   * Set the elements of wonder boards array
   *
   * @return  self
   */ 
  private function prepareWonderBoards() {
    $this->wonderBoards = array();

    $worthsAlexandria = array();
    array_push($worthsAlexandria, (new Worth())->setVictory(3));
    array_push($worthsAlexandria, (new Worth())->setLumber(0.5)->setStone(0.5)->setClay(0.5)->setOre(0.5));
    array_push($worthsAlexandria, (new Worth())->setVictory(7));

    $costsAlexandria = array();
    array_push($costsAlexandria, (new Materials())->setStone(2));
    array_push($costsAlexandria, (new Materials())->setOre(2));
    array_push($costsAlexandria, (new Materials())->setGlassworks(2));

    array_push($this->wonderBoards, (new WonderBoard("Alexandria", '1'))->setProductions((new Materials())->setGlassworks(1))->setStageCosts($costsAlexandria)->setStageWorth($worthsAlexandria));

    $worthsBabylon = array();
    array_push($worthsBabylon, (new Worth())->setVictory(3));
    array_push($worthsBabylon, (new Worth())->setCompass(0.5)->setWheel(0.5)->setScript(0.5));
    array_push($worthsBabylon, (new Worth())->setVictory(7));

    $costsBabylon = array();
    array_push($costsBabylon, (new Materials())->setClay(2));
    array_push($costsBabylon, (new Materials())->setLumber(3));
    array_push($costsBabylon, (new Materials())->setClay(4));

    array_push($this->wonderBoards, (new WonderBoard("Babylon", '2'))->setProductions((new Materials())->setClay(1))->setStageCosts($costsBabylon)->setStageWorth($worthsBabylon));

    $worthsEphesos = array();
    array_push($worthsEphesos, (new Worth())->setVictory(3));
    array_push($worthsEphesos, (new Worth())->setCoin(9));
    array_push($worthsEphesos, (new Worth())->setVictory(7));

    $costsEphesos = array();
    array_push($costsEphesos, (new Materials())->setStone(2));
    array_push($costsEphesos, (new Materials())->setLumber(2));
    array_push($costsEphesos, (new Materials())->setPress(2));

    array_push($this->wonderBoards, (new WonderBoard("Ephesos", '3'))->setProductions((new Materials())->setPress(1))->setStageCosts($costsEphesos)->setStageWorth($worthsEphesos));

    $worthsGizah = array();
    array_push($worthsGizah, (new Worth())->setVictory(3));
    array_push($worthsGizah, (new Worth())->setVictory(5));
    array_push($worthsGizah, (new Worth())->setVictory(7));

    $costsGizah = array();
    array_push($costsGizah, (new Materials())->setClay(2));
    array_push($costsGizah, (new Materials())->setLumber(3));
    array_push($costsGizah, (new Materials())->setClay(4));

    array_push($this->wonderBoards, (new WonderBoard("Gizah", '4'))->setProductions((new Materials())->setStone(1))->setStageCosts($costsGizah)->setStageWorth($worthsGizah));

    $worthsHalikarnassos = array();
    array_push($worthsHalikarnassos, (new Worth())->setVictory(3));
    array_push($worthsHalikarnassos, (new Worth())->setCard("pickDiscardedCard"));
    array_push($worthsHalikarnassos, (new Worth())->setVictory(7));

    $costsHalikarnassos = array();
    array_push($costsHalikarnassos, (new Materials())->setClay(2));
    array_push($costsHalikarnassos, (new Materials())->setOre(3));
    array_push($costsHalikarnassos, (new Materials())->setLoom(2));

    array_push($this->wonderBoards, (new WonderBoard("Halikarnassos", '5'))->setProductions((new Materials())->setLoom(1))->setStageCosts($costsHalikarnassos)->setStageWorth($worthsHalikarnassos));

    $worthsOlympia = array();
    array_push($worthsOlympia, (new Worth())->setVictory(3));
    array_push($worthsOlympia, (new Worth())->setCard("BuildCardForFree"));
    array_push($worthsOlympia, (new Worth())->setVictory(7));

    $costsOlympia = array();
    array_push($costsOlympia , (new Materials())->setLumber(2));
    array_push($costsOlympia , (new Materials())->setStone(2));
    array_push($costsOlympia , (new Materials())->setOre(2));

    array_push($this->wonderBoards, (new WonderBoard("Olympia", '6'))->setProductions((new Materials())->setLumber(1))->setStageCosts($costsOlympia)->setStageWorth($worthsOlympia));

    $worthsRhodos = array();
    array_push($worthsRhodos, (new Worth())->setVictory(3));
    array_push($worthsRhodos, (new Worth())->setShield(2));
    array_push($worthsRhodos, (new Worth())->setVictory(7));

    $costsRhodos = array();
    array_push($costsRhodos, (new Materials())->setLumber(2));
    array_push($costsRhodos, (new Materials())->setClay(3));
    array_push($costsRhodos, (new Materials())->setOre(4));

    array_push($this->wonderBoards, (new WonderBoard("Rhodos", '7'))->setProductions((new Materials())->setore(1))->setStageCosts($costsRhodos)->setStageWorth($worthsRhodos));
    
    return $this;
  }

  
  /**
   * Shuffle the Wonderboards array.
   *
   * @return  self
   */ 
  private function shuffleWonderBoards() {
    shuffle($this->wonderBoards);
    return $this;
  }


  /**
   * Get the value of wonderBoards
   */ 
  public function getWonderBoards()
  {
    return $this->wonderBoards;
  }

  /**
   * Set the value of wonderBoards
   *
   * @return  self
   */ 
  public function setWonderBoards($wonderBoards)
  {
    $this->wonderBoards = $wonderBoards;

    return $this;
  }
}


