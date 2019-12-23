<?php
// Not done

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
require_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';
include_once dirname(__FILE__) . '/WonderBoard.php';

/**
 * @author: Meryem Banu Cavlak
 */
class Player implements MongoDB\BSON\Persistable, JSONResponse {

  private $id;
  private $gameId;
  private $name;
  private $secretSkill;

  // this is cards at hand
  private $cards;   

  private $board;
  private $leftPlayerId;
  private $rightPlayerId;
  private $victoryPoints;
  private $victoryTokens;
  private $defeatTokens;
  private $shields;

  private $isPlayedForTurn;
  private $coin;
  

  // needed bcs gold coins acquired through commerce cannnot be used in the same turn
  private $coinLeftCommerceSell;  
  private $coinRightCommerceSell;  
  private $coinLeftCommerceBuy;  
  private $coinRightCommerceBuy;  

  // hash (key, int) array, needed bcs some yellow cards change the cost of the materials for the commerce, initialise both of the array elements to 2
  private $materialCostLeft;
  private $materialCostRight;

  // hash array, needed to be updated after every build a structure command
  private $producedMaterials;
  private $producedMaterialsAfterCommerce;   // needed because, if the user makes an invalid move the commerce move will become invalid also

  // double hash array, needed to be updated after every build a structure command
  private $producedOptionalMaterials;
  private $producedOptionalMaterialsAfterCommerce;  // it is not possible to get optional materials through commerce, however, it is used for isCardValid function

  // a wonder stage gives this opportunity, ones per age
  private $canBuildForFree;
  // needed to initialise the above attribute at the beginning of each age
  private $updateInNewAgeBuildForFree;

  function __construct(){

    $this->id = new MongoDB\BSON\ObjectId;
    $this->victoryPoints = 0;
    $this->victoryTokens = 0;
    $this->defeatTokens = 0;
    $this->shields = 0;
    $this->coin = 3;
    $this->coinLeftCommerceSell = 0;
    $this->coinRightCommerceSell = 0;
    $this->coinLeftCommerceBuy = 0;
    $this->coinRightCommerceBuy = 0;
    $this->materialCostLeft = array("clay"=>2, "ore"=>2, "stone"=>2, "lumber"=>2, "press"=>2, "loom"=>2, "glassworks"=>2);
    $this->materialCostRight = array("clay"=>2, "ore"=>2, "stone"=>2, "lumber"=>2, "press"=>2, "loom"=>2, "glassworks"=>2);
    $this->board = null;
    $this->producedMaterials = array("clay"=>0, "ore"=>0, "stone"=>0, "lumber"=>0, "press"=>0, "loom"=>0, "glassworks"=>0);
    $this->producedMaterialsAfterCommerce = array("clay"=>0, "ore"=>0, "stone"=>0, "lumber"=>0, "press"=>0, "loom"=>0, "glassworks"=>0);
    $this->canBuildForFree = false;
    $this->updateInNewAgeBuildForFree = false;
    $this->producedOptionalMaterials = array();
    $this->producedOptionalMaterialsAfterCommerce = array(); 
  }

  // $materials is in array("clay"=>0, "ore"=>0, "stone"=>0, "lumber"=>0, "press"=>0, "loom"=>0, "glassworks"=>0) this format
  // $costObject is a Materials object
  // @author: Meryem Banu Cavlak
  // this function does not check coin cost
  private function checkMaterials($materials, $costObject){
    $cost = $costObject->jsonArraySerialize();
    foreach($materials as $key => $value){
      if($materials[$key] >= $cost[$key])
        $cost[$key] = 0;
      else {
        $cost[$key] = $cost[$key] - $materials[$key];
      }
    }
    $costObject->bsonUnserialize($cost);
    return $this;
  }

  function findCard($cardId) : Card {
    foreach($this->cards as $key => $card) {
      if ($card->getId() == $cardId) {
        return $card;
      }
    }

    throw new Exception('Card with given id \'' . $cardId . '\' is not found in the player ' . $this->name .' hand!');
  }

  function penalizePlayer() {
    $this->setDefeatTokens($this->getDefeatTokens() + 3);
    return $this;
  }

  function dismissCard($cardId) {
    foreach($this->cards as $key => $card) {
      if ($card->getId() == $cardId) {
        unset($this->cards[$key]);
        return $this;
      }
    }

    throw new Exception('Card with given id \'' . $cardId . '\' is not found in the player ' . $this->name .' hand!');
  }

  function addMaterials($materials) {
    $materialsArr = $materials->bsonSerialize();
    unset($materialsArr['coin']);
    foreach ($materialsArr as $key => $material) {
      $this->producedMaterials[$key] = $this->producedMaterials[$key] + $material;
    }
    return $this;
  }

  //$commerceMaterials = Materials objesi;
  // $isLeft = is the player I am trying to buy materials is my left player
  // @author: Meryem Banu Cavlak
  function commerce($isLeft, $player, $commerceMaterials){
    $materials = $player->getProducedMaterials();
    $initialCost = $commerceMaterials->getClay() + $commerceMaterials->getOre() + $commerceMaterials->getStone() + $commerceMaterials->getLumber() + $commerceMaterials->getPress() + $commerceMaterials->getLoom() + $commerceMaterials->getGlassworks();
    $commerceGain = $commerceMaterials->jsonArraySerialize();;

    // check if the commerce can be done
    $this->checkMaterials($materials, $commerceMaterials);
    $remainingCost = $commerceMaterials->getClay() + $commerceMaterials->getOre() + $commerceMaterials->getStone() + $commerceMaterials->getLumber() + $commerceMaterials->getPress() + $commerceMaterials->getLoom() + $commerceMaterials->getGlassworks();
    $requiredGold = 0;
    foreach($commerceGain as $key => $value){
      if($value != 0){
        if($isLeft)
          $requiredGold = $requiredGold + $this->materialCostLeft[$key];
        else
          $requiredGold = $requiredGold + $this->materialCostRight[$key];
      }
    }    
    
    if($remainingCost == 0 && $requiredGold <= $this->coin - $this->coinRightCommerceBuy - $this->coinLeftCommerceBuy){
      if($isLeft){
        $player->setCoinRightCommerceSell( $player->setCoinRightCommerceSell() + 2 * $initialCost);
        $this->coinLeftCommerceBuy = $this->coinLeftCommerceBuy + 2 * $initialCost;
      }
      else{
        $player->setCoinLeftCommerceSell( $player->setCoinLeftCommerceSell() + 2 * $initialCost);
        $this->coinRightCommerceBuy = $this->coinRightCommerceBuy + 2 * $initialCost;
      }
      foreach($commerceGain as $key => $value){
        if($value != 0){
          $this->producedMaterialsAfterCommerce[$key] = $this->producedMaterialsAfterCommerce[$key] + $value;
        }
      }
      return $this;
    }

    if ($remainingCost == 0) {
      throw new Exception("You do not have enough coins!");
    }
    
    throw new Exception("Your neighbour does not have enough materials for sell!");

  }
  

  // @author: Meryem Banu Cavlak
  // this function is used by addCard($cardToUse, $wantsToBuildForFree, $leftPlayerCards, $rigthPlayerCards)
  private function isCardValid($cardToUse, $wantsToBuildForFree){
    // user cannot build a card with the same name twice
    $cards = $this->board->getCards();
    foreach ($cards as $hasSameName) {
      if($hasSameName->getName() == $cardToUse->getName()){
        return false;
      }
    }

    // if user wants to build for free, then no need to check for anything
    if($wantsToBuildForFree && $this->canBuildForFree){
      return true;
      $this->canBuildForFree = false;
    }

    $cost = $cardToUse->getCost();
    // the card can be free, and if so return true immediately
    $remainingCost = $cost->getCoin() + $cost->getClay() + $cost->getOre() + $cost->getStone() + $cost->getLumber() + $cost->getPress() + $cost->getLoom() + $cost->getGlassworks();
    if($remainingCost == 0){
      return true;
    }

    // if not and if there is a building that makes this building free return true
    if($cardToUse->getFriendCard() != null){
      $cards = $this->board->getCards();
      foreach ($cards as $freeConstruct) {
        if($freeConstruct->getName() == $cardToUse->getFriendCard()){
          return true;
        }
        // this is an optional case in which two cards can make this card free
        if($cardToUse->getFriendCard() == "East Trading Post-West Trading Post"){
          if( $freeConstruct->getName() == "East Trading Post" || $freeConstruct->getName() == "West Trading Post")
            return true;
        }
      }
    }

    // the card is not free and cannot be built through the free construction chain then check if it can be built without using optional materials
    if($this->coin - $this->coinRightCommerceBuy - $this->coinLeftCommerceBuy >= $cost->getCoin())
      $cost->setCoin(0);

    $this->checkMaterials($this->producedMaterialsAfterCommerce, $cost);
    $remainingCost = $cost->getCoin() + $cost->getClay() + $cost->getOre() + $cost->getStone() + $cost->getLumber() + $cost->getPress() + $cost->getLoom() + $cost->getGlassworks();
    if($remainingCost == 0){
      return true;
    }

    //coin cannot be produced optionally
    if($cost->getCoin() > 0)
      return false;

    // check the optional materials double array
    // there is only one card for the optional production of materials press, loom and glasswork so if the card needs more than 1 from these, return false
    if(($cost->getPress() + $cost->getLoom() + $cost->getGlassworks()) > 1)
      return false;
    else if(($cost->getPress() + $cost->getLoom() + $cost->getGlassworks()) <= 1){ // check if the player owns that card
      foreach($this->producedOptionalMaterialsAfterCommerce as $key => $innerArray){
        if($innerArray['press'] > 0){
          $cost->setPress(0);
          $cost->setLoom(0);
          $cost->setGlassworks(0);
          unset($this->producedOptionalMaterialsAfterCommerce[$key]);
        }
      }
    }
    // it is impossible to produce these materials now
    if(($cost->getPress() + $cost->getLoom() + $cost->getGlassworks()) > 0)
      return false;

    // calculate the required cost for clay, ore, stone, lumber
    $requiredCost = [0, 0, 0, 0];
    while($cost->getClay() > 0){
      $requiredCost[0] = $requiredCost[0] + 1;
      $cost->setClay($cost->getClay() - 1);
    }
    while($cost->getOre() > 0){
      $requiredCost[1] = $requiredCost[1] + 1;
      $cost->setOre($cost->getOre() - 1);
    }
    while($cost->getStone() > 0){
      $requiredCost[2] = $requiredCost[2] + 1;
      $cost->setStone($cost->getStone() - 1);
    }
    while($cost->getLumber() > 0){
      $requiredCost[3] = $requiredCost[3] + 1;
      $cost->setLumber($cost->getLumber() - 1);
    }

    // there are at most five cards that builds these materials optionally
    $intDoubleArray = array(array(0,0,0,0), array(0,0,0,0), array(0,0,0,0), array(0,0,0,0), array(0,0,0,0));
    $lastIndex = 0;
    foreach($this->producedOptionalMaterialsAfterCommerce as $key => $innerArray){
      if($innerArray['clay'] > 0)
        $intDoubleArray[$lastIndex][0] = 1;
      if($innerArray['ore'] > 0)
        $intDoubleArray[$lastIndex][1] = 1;
      if($innerArray['stone'] > 0)
        $intDoubleArray[$lastIndex][2] = 1;
      if($innerArray['lumber'] > 0)
        $intDoubleArray[$lastIndex][3] = 1;
      $lastIndex = $lastIndex + 1;
    }
    // if there are less than 5 cards, remove the remaining elements
    for ($x = $lastIndex + 1; $x <= 4; $x++) 
      unset($intDoubleArray[$x]);
    
    // now check if any possible combination of optional materials can satisfy the required cost
    // count($intDoubleArray) is at most five, so this is not time consuming and this function will be executed very rarely
    $currentProduction = [0, 0, 0, 0];
    for ($x = 0; $x < 4 ^ (count($intDoubleArray)); $x++){
      $currentProduction[0] = 0;
      $currentProduction[1] = 0;
      $currentProduction[2] = 0;
      $currentProduction[3] = 0;
      // for each element of the double array select one of the productions in an ordered way
      for ($y = 0; $y < count($intDoubleArray); $y++){
        if($y == 0){
          if(($x % 4) == 0)
            $currentProduction[0] = $currentProduction[0] + $intDoubleArray[0][0];
          else if(($x % 4) == 1)
            $currentProduction[1] = $currentProduction[1] + $intDoubleArray[0][1];
          else if(($x % 4) == 2)
            $currentProduction[2] = $currentProduction[2] + $intDoubleArray[0][2];
          else if(($x % 4) == 3)
            $currentProduction[3] = $currentProduction[3] + $intDoubleArray[0][3];
        }
        else if($y == 1){
          if(($x % 16) <= 3)
            $currentProduction[0] = $currentProduction[0] + $intDoubleArray[1][0];
          else if(($x % 16) <= 7)
            $currentProduction[1] = $currentProduction[1] + $intDoubleArray[1][1];
          else if(($x % 16) <= 11)
            $currentProduction[2] = $currentProduction[2] + $intDoubleArray[1][2];
          else if(($x % 16) <= 15)
            $currentProduction[3] = $currentProduction[3] + $intDoubleArray[1][3];
        }
        else if($y == 2){
          if(($x % 64) <= 15)
            $currentProduction[0] = $currentProduction[0] + $intDoubleArray[2][0];
          else if(($x % 64) <= 31)
            $currentProduction[1] = $currentProduction[1] + $intDoubleArray[2][1];
          else if(($x % 64) <= 47)
            $currentProduction[2] = $currentProduction[2] + $intDoubleArray[2][2];
          else if(($x % 64) <= 63)
            $currentProduction[3] = $currentProduction[3] + $intDoubleArray[2][3];
        }
        else if($y == 3){
          if(($x % 256) <= 63)
            $currentProduction[0] = $currentProduction[0] + $intDoubleArray[3][0];
          else if(($x % 256) <= 127)
            $currentProduction[1] = $currentProduction[1] + $intDoubleArray[3][1];
          else if(($x % 1-256) <= 191)
            $currentProduction[2] = $currentProduction[2] + $intDoubleArray[3][2];
          else if(($x % 256) <= 255)
            $currentProduction[3] = $currentProduction[3] + $intDoubleArray[3][3];
        }
        else if($y == 4){
          if(($x % 1024) <= 255)
            $currentProduction[0] = $currentProduction[0] + $intDoubleArray[4][0];
          else if(($x % 1024) <= 511)
            $currentProduction[1] = $currentProduction[1] + $intDoubleArray[4][1];
          else if(($x % 1024) <= 767)
            $currentProduction[2] = $currentProduction[2] + $intDoubleArray[4][2];
          else if(($x % 1024) <= 1023)
            $currentProduction[3] = $currentProduction[3] + $intDoubleArray[4][3];
        }
      }
      // now check if the current production can satisfy the required cost
      if($currentProduction[0] >= $requiredCost[0] && $currentProduction[1] >= $requiredCost[1] && $currentProduction[2] >= $requiredCost[2] && $currentProduction[3] >= $requiredCost[3])
        return true;
    }
    // now it is not possible to build the card
    return false;
  }

  // @author: Meryem Banu Cavlak
  function addCard($cardToUse, $wantsToBuildForFree, $leftPlayerCards, $rigthPlayerCards) {
    $isValid = $this->isCardValid($cardToUse, $wantsToBuildForFree);
    if($isValid){
      $this->board->addCardToBoard($cardToUse);
      $this->coin = $this->coin - $cardToUse->getCost()->getCoin();
      if($cardToUse->getColor() == "brown"){
        if($cardToUse->getLumber() >= 1)
          $this->producedMaterials['lumber'] = $this->producedMaterials['lumber'] + $cardToUse->getLumber();
        if($cardToUse->getStone() >= 1)
          $this->producedMaterials['stone'] = $this->producedMaterials['stone'] + $cardToUse->getStone();
        if($cardToUse->getClay() >= 1)
          $this->producedMaterials['clay'] = $this->producedMaterials['clay'] + $cardToUse->getClay();
        if($cardToUse->getOre() >= 1)
          $this->producedMaterials['ore'] = $this->producedMaterials['ore'] + $cardToUse->getLumber();

        if($cardToUse->getStone() == 0.5)
          array_push($this->producedOptionalMaterials, array("clay"=>0, "ore"=>0, "stone"=>1, "lumber"=>1, "press"=>0, "loom"=>0, "glassworks"=>0));
        else if($cardToUse->getOre() == 0.5)
          array_push($this->producedOptionalMaterials, array("clay"=>1, "ore"=>1, "stone"=>0, "lumber"=>0, "press"=>0, "loom"=>0, "glassworks"=>0));
        else if($cardToUse->getClay() == 0.5)
          array_push($this->producedOptionalMaterials, array("clay"=>1, "ore"=>0, "stone"=>0, "lumber"=>1, "press"=>0, "loom"=>0, "glassworks"=>0));
      }
      else if($cardToUse->getColor() == "gray"){
        if($cardToUse->getLoom() >= 1)
          $this->producedMaterials['loom'] = $this->producedMaterials['loom'] + $cardToUse->getLoom();
        if($cardToUse->getGlassworks() >= 1)
          $this->producedMaterials['glassworks'] = $this->producedMaterials['glassworks'] + $cardToUse->getGlassworks();
        if($cardToUse->getPress() >= 1)
          $this->producedMaterials['press'] = $this->producedMaterials['press'] + $cardToUse->getPress();
      }
      else if($cardToUse->getColor() == "red")
        $this->shield = $this->shield + 1;
      else if($cardToUse->getName() == "Forum"){
        array_push($this->producedOptionalMaterials, array("clay"=>0, "ore"=>0, "stone"=>0, "lumber"=>0, "press"=>1, "loom"=>1, "glassworks"=>1));
      }
      else if($cardToUse->getName() == "Caravansery"){
        array_push($this->producedOptionalMaterials, array("clay"=>1, "ore"=>1, "stone"=>1, "lumber"=>1, "press"=>0, "loom"=>0, "glassworks"=>0));
      }
      else if($cardToUse->getName() == "East Trading Post"){
        $this->materialCostRight['clay'] = 1;
        $this->materialCostRight['ore'] = 1;
        $this->materialCostRight['stone'] = 1;
        $this->materialCostRight['lumber'] = 1;
      }
      else if($cardToUse->getName() == "West Trading Post"){
        $this->materialCostLeft['clay'] = 1;
        $this->materialCostLeft['ore'] = 1;
        $this->materialCostLeft['stone'] = 1;
        $this->materialCostLeft['lumber'] = 1;
      }
      else if($cardToUse->getName() == "Market Place"){
        $this->materialCostLeft['press'] = 1;
        $this->materialCostLeft['loom'] = 1;
        $this->materialCostLeft['glassworks'] = 1;
        $this->materialCostRight['press'] = 1;
        $this->materialCostRight['loom'] = 1;
        $this->materialCostRight['glassworks'] = 1;
      }  
      else if($cardToUse->getName() == "Vineyard"){
        $cards = $this->board->getCards();
        foreach($cards as $aCard){
          if($aCard->getColor() == "brown")
            $this->coin = $this->coin + 1;
        }
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "brown")
            $this->coin = $this->coin + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "brown")
            $this->coin = $this->coin + 1;
        }
      }
      else if($cardToUse->getName() == "Bazar"){
        $cards = $this->board->getCards();
        foreach($cards as $aCard){
          if($aCard->getColor() == "gray")
            $this->coin = $this->coin + 1;
        }
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "gray")
            $this->coin = $this->coin + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "gray")
            $this->coin = $this->coin + 1;
        }
      }
      else if($cardToUse->getName() == "Haven"){
        $cards = $this->board->getCards();
        foreach($cards as $aCard){
          if($aCard->getColor() == "brown")
            $this->coin = $this->coin + 1;
        }
      }
      else if($cardToUse->getName() == "Lighthouse"){
        $cards = $this->board->getCards();
        foreach($cards as $aCard){
          if($aCard->getColor() == "yellow")
            $this->coin = $this->coin + 1;
        }
      }
      else if($cardToUse->getName() == "Chamber of Commerce"){
        $cards = $this->board->getCards();
        foreach($cards as $aCard){
          if($aCard->getColor() == "gray")
            $this->coin = $this->coin + 2;
        }
      }
      else if($cardToUse->getName() == "Arena"){
        $this->coin = $this->coin + $this->board->getWonderStage() * 3;
      }
      else if($cardToUse->getName() == "Tavern"){
        $this->coin = $this->coin + 5;
      }
      return true;
    }
    else 
      return false;
  }

  // @author: Meryem Banu Cavlak
  // this function is used by increaseWonderStage()
  private function canBuiltTheWonderStage(){
    $currentStage = $this->board->getWonderStage();
    // since array index's start with 0, next stages cost is equal to the currentStage index of stageCosts attribute of the board
    $cost = ($this->board->getStageCosts())[$currentStage];  

    // check if the wonder stage can be built without using optional materials
    $this->checkMaterials($this->producedMaterialsAfterCommerce, $cost);
    $remainingCost = + $cost->getClay() + $cost->getOre() + $cost->getStone() + $cost->getLumber() + $cost->getPress() + $cost->getLoom() + $cost->getGlassworks();
    if($remainingCost == 0){
      return true;
    }
    // all stage costs only contain one type of material
    // calculate max for each than place them into a new hash for comparison
    $maxArray = [0,0,0,0,0,0,0];
    foreach($this->producedOptionalMaterialsAfterCommerce as $key => $innerArray){
      if($innerArray['clay'] > 0)
        $maxArray[0] = $maxArray[0] + 1;
      if($innerArray['ore'] > 0)
        $maxArray[1] = $maxArray[1] + 1;
      if($innerArray['stone'] > 0)
        $maxArray[2] = $maxArray[2] + 1;
      if($innerArray['lumber'] > 0)
        $maxArray[3] = $maxArray[3] + 1;
      if($innerArray['press'] > 0){
        $maxArray[4] = $maxArray[4] + 1; 
        $maxArray[5] = $maxArray[5] + 1; 
        $maxArray[6] = $maxArray[6] + 1; 
      }     
    }
    // compare the productions and the cost
    if($maxArray[0] >= $cost->getClay() && $maxArray[1] >= $cost->getOre() && $maxArray[2] >= $cost->getStone() && $maxArray[3] >= $cost->getLumber() && $maxArray[4] >= ($cost->getPress() + $cost->getLoom() + $cost->getGlassworks() ) ) {
      return true;              
    }
    return false;
  }

  // @author: Meryem Banu Cavlak
  function increaseWonderStage(){
    $isValid = $this->canBuiltTheWonderStage();
    if($isValid){
      $this->board->setWonderStage( $this->board->setWonderStage + 1);
      if($this->board->getWonderStage() == 2){
        if($this->board->getWonderName() == "Rhodos")
          $this->shields = $this->shields + 1;
        else if($this->board->getWonderName() == "Olympia"){
          $this->canBuildForFree = true;
          $this->updateInNewAgeBuildForFree = true;
        }
        else if($this->board->getWonderName() == "Ephesos"){
          $this->coin = $this->coin + 9;
        }
        else if($this->board->getWonderName() == "Alexandria"){
          array_push($this->producedOptionalMaterials, array("clay"=>1, "ore"=>1, "stone"=>1, "lumber"=>1, "press"=>0, "loom"=>0, "glassworks"=>0));
        }
      }
      return true;
    }
    else 
      return false;
  }

  // @author: Meryem Banu Cavlak
  private function calculateScientificPoints($compasses, $wheels, $scripts){
    $thePoints = 0;
    $thePoints = $thePoints + ($compasses * $compasses);
    $thePoints = $thePoints + ($wheels * $wheels);
    $thePoints = $thePoints + ($scripts * $scripts);

    $theMin = $compasses;
    if($wheels < $theMin){
      $theMin = $wheels;
    }
    if($scripts < $theMin){
      $theMin = $scripts;
    }
    $thePoints = $thePoints + (7 * $theMin);
    return $thePoints;
  }

  // @author: Meryem Banu Cavlak
  private function calculateGuildPoints($guildCards, $leftPlayerCards, $rigthPlayerCards, $leftWonderStage, $rigthWonderStage, $leftDefeatToken, $rigthDefeatToken){
    $guildPoints = 0;
    $cards = $this->board->getCards();
    foreach ($guildCards as $aCard) {
      if($aCard->getName() == Constants::WORKERS_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "brown")
            $guildPoints = $guildPoints + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "brown")
            $guildPoints = $guildPoints + 1;
        }
      }
      else if($aCard->getName() == Constants::CRAFTSMAN_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "gray")
            $guildPoints = $guildPoints + 2;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "gray")
            $guildPoints = $guildPoints + 2;
        }
      }
      else if($aCard->getName() == Constants::TRADERS_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "yellow")
            $guildPoints = $guildPoints + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "yellow")
            $guildPoints = $guildPoints + 1;
        }
      }
      else if($aCard->getName() == Constants::PHILOSOPHERS_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "green")
            $guildPoints = $guildPoints + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "green")
            $guildPoints = $guildPoints + 1;
        }
      }
      else if($aCard->getName() == Constants::SPIES_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "red")
            $guildPoints = $guildPoints + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "red")
            $guildPoints = $guildPoints + 1;
        }
      }
      else if($aCard->getName() == Constants::STRATEGISTS_GUILD){
        $guildPoints = $guildPoints + $leftDefeatToken;
        $guildPoints = $guildPoints + $rigthDefeatToken;
      }
      else if($aCard->getName() == Constants::SHIPOWNERS_GUILD){
        foreach ($cards as $isBrownGrayPurple) {
          if($isBrownGrayPurple->getColor() == "brown" || $isBrownGrayPurple->getColor() == "gray" || $isBrownGrayPurple->getColor() == "purple"){
            $guildPoints = $guildPoints + 1;
          }
        }
      }
      else if($aCard->getName() == Constants::MAGICSTRATES_GUILD){
        foreach($leftPlayerCards as $aCard){
          if($aCard->getColor() == "blue")
            $guildPoints = $guildPoints + 1;
        }
        foreach($rigthPlayerCards as $aCard){
          if($aCard->getColor() == "blue")
            $guildPoints = $guildPoints + 1;
        }
      }
      else if($aCard->getName() == Constants::BUILDERS_GUILD){
        $guildPoints = $guildPoints + $leftWonderStage;
        $guildPoints = $guildPoints + $rigthWonderStage;
        $guildPoints = $guildPoints + $this->board->getWonderStage();
      }
    }
    return $guildPoints;
  }

  // @author: Meryem Banu Cavlak
  function calculateVictoryPoints($leftPlayerCards, $rigthPlayerCards, $leftWonderStage, $rigthWonderStage, $leftDefeatToken, $rigthDefeatToken){
    // calculate Military conflicts
    $this->victoryPoints = $this->victoryPoints + $this->victoryTokens;
    $this->victoryPoints = $this->victoryPoints + $this->defeatTokens;

    // calculate Treasury Contents
    $this->victoryPoints = $this->victoryPoints + $this->coin / 3;

    // calculate Wonder
    if($this->board->getWonderStage() >= 1)
      $this->victoryPoints = $this->victoryPoints + 3;
    if($this->board->getWonderStage() >= 2 && $this->board->getWonderName() == "Gizah")
      $this->victoryPoints = $this->victoryPoints + 5;
    if($this->board->getWonderStage() >= 3)
      $this->victoryPoints = $this->victoryPoints + 7;

    // calculate Civilian Structures
    $cards = $this->board->getCards();
    foreach ($cards as $isBlue) {
      if($isBlue->getColor() == "blue"){
        $this->victoryPoints = $this->victoryPoints + $isBlue->getVictoryPoints();
      }
    }

    // Calculate Scientific Structures
    $compassPoints = 0;
    $wheelPoints = 0;
    $scriptPoints = 0;
    $noOfOptionals = 0;
    $maxScientific = 0;
    foreach ($cards as $isScientific) {
      if($isScientific->getColor() == "green"){
        $compassPoints = $compassPoints + $isScientific->getCompassPoints();
        $wheelPoints = $wheelPoints + $isScientific->getWheelPoints();
        $scriptPoints = $scriptPoints + $isScientific->getScriptPoints();
      }
      else if($isScientific->getName() == Constants::SCIENTISTS_GUILD){
        $noOfOptionals = $noOfOptionals + 1;
      }
      else if($this->board->getWonderStage() >= 2 && $this->board->getWonderName() == "Babylon"){
        $noOfOptionals = $noOfOptionals + 1;
      }
    }
    // no of optionals can be at most 2
    if($noOfOptionals == 0){
      $maxScientific = $this->calculateScientificPoints($compassPoints, $wheelPoints, $scriptPoints);
    }
    else if($noOfOptionals == 1){
      // one optional can be added to any one of the three point types, the maximum of the return values will be added to the victoryPoints attribute
      $another = 0;
      $maxScientific = $this->calculateScientificPoints($compassPoints + 1, $wheelPoints, $scriptPoints);
      $another = $this->calculateScientificPoints($compassPoints, $wheelPoints + 1, $scriptPoints);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
      $another = $this->calculateScientificPoints($compassPoints, $wheelPoints, $scriptPoints + 1);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
    }
    else if($noOfOptionals == 2){
      // two optional can be added to any one or two of the three point types and, the maximum of the return values will be added to the victoryPoints attribute
      $another = 0;
      $maxScientific = $this->calculateScientificPoints($compassPoints + 2, $wheelPoints, $scriptPoints);
      $another = $this->calculateScientificPoints($compassPoints, $wheelPoints + 2, $scriptPoints);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
      $another = $this->calculateScientificPoints($compassPoints, $wheelPoints, $scriptPoints + 2);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
      $another = $this->calculateScientificPoints($compassPoints + 1, $wheelPoints + 1, $scriptPoints);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
      $another = $this->calculateScientificPoints($compassPoints + 1, $wheelPoints, $scriptPoints + 1);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
      $another = $this->calculateScientificPoints($compassPoints, $wheelPoints + 1, $scriptPoints + 1);
      if($another > $maxScientific){
        $maxScientific = $another;
      }
    }
    $this->victoryPoints = $this->victoryPoints + $maxScientific;

    // Calculate Commercial Structures
    $playerCards = $this->board->getCards();
    foreach ($cards as $isYellow){
      if($isYellow->getName() == "Haven"){
        foreach($playerCards as $aCard){
          if($aCard->getColor() == "brown")
          $this->victoryPoints = $this->victoryPoints + 1;
        }
      }
      else if($isYellow->getName() == "Lighthouse"){
        foreach($playerCards as $aCard){
          if($aCard->getColor() == "yellow")
            $this->victoryPoints = $this->victoryPoints + 1;
        }
      }
      else if($isYellow->getName() == "Chamber of Commerce"){
        foreach($playerCards as $aCard){
          if($aCard->getColor() == "gray")
            $this->victoryPoints = $this->victoryPoints + 2;
        }
      }
      else if($isYellow->getName() == "Arena"){
        $this->victoryPoints = $this->victoryPoints + $this->board->getWonderStage();
      }
    }

    // Calculate Guilds
    // collect purple cards first
    $purpleCards = array();
    foreach ($cards as $isPurple) {
      if($isScientific->getColor() == "purple"){
        array_push($purpleCards, $isPurple);
      }
    }  
    $this->victoryPoints = $this->victoryPoints + $this->calculateGuildPoints($purpleCards, $leftPlayerCards, $rigthPlayerCards, $leftWonderStage, $rigthWonderStage, $leftDefeatToken, $rigthDefeatToken);
  }

  function bsonSerialize()
  {
    return [
      '_id' => $this->id,
      'gameId' => $this->gameId,
      'name' => $this->name,
      'secretSkill' => $this->secretSkill,
      'cards' => $this->cards,
      'board' => $this->board,
      'leftPlayerId' => $this->leftPlayerId,
      'rightPlayerId' => $this->rightPlayerId,
      'victoryPoints' => $this->victoryPoints,
      'victoryTokens' => $this->victoryTokens,
      'defeatTokens' => $this->defeatTokens,
      'shields' => $this->shields,
      'isPlayedForTurn' => $this->isPlayedForTurn,
      'coin' => $this->coin,
      'coinLeftCommerceSell' => $this->coinLeftCommerceSell,
      'coinRightCommerceSell' => $this->coinRightCommerceSell,
      'coinLeftCommerceBuy' => $this->coinLeftCommerceBuy,
      'coinRightCommerceBuy' => $this->coinRightCommerceBuy,
      'materialCostLeft' => $this->materialCostLeft,
      'materialCostRight' => $this->materialCostRight,
      'producedMaterials' => $this->producedMaterials,
      'producedMaterialsAfterCommerce' => $this->producedMaterialsAfterCommerce,
      'producedOptionalMaterials' => $this->producedOptionalMaterials,
      'producedOptionalMaterialsAfterCommerce' => $this->producedOptionalMaterialsAfterCommerce,
      'canBuildForFree' => $this->canBuildForFree,
      'updateInNewAgeBuildForFree' => $this->updateInNewAgeBuildForFree
    ];
  }

  function bsonUnserialize(array $data)
  {
    $this->id = $data['_id'];
    $this->gameId = $data['gameId'];
    $this->name = $data['name'];
    $this->secretSkill = $data['secretSkill'];
    $this->cards = $data['cards'];
    $this->board = $data['board'];
    $this->leftPlayerId = $data['leftPlayerId'];
    $this->rightPlayerId = $data['rightPlayerId'];
    $this->victoryPoints = $data['victoryPoints'];
    $this->victoryTokens = $data['victoryTokens'];
    $this->defeatTokens = $data['defeatTokens'];
    $this->shields = $data['shields'];
    $this->isPlayedForTurn = $data['isPlayedForTurn'];
    $this->coin = $data['coin'];
    $this->coinLeftCommerceSell = $data['coinLeftCommerceSell'];
    $this->coinRightCommerceSell = $data['coinRightCommerceSell'];
    $this->coinLeftCommerceBuy = $data['coinLeftCommerceBuy'];
    $this->coinRightCommerceBuy = $data['coinRightCommerceBuy'];
    $this->materialCostLeft = $data['materialCostLeft'];
    $this->materialCostRight = $data['materialCostRight'];
    $this->producedMaterials = $data['producedMaterials'];
    $this->producedMaterialsAfterCommerce = $data['producedMaterialsAfterCommerce'];
    $this->producedOptionalMaterials = $data['producedOptionalMaterials'];
    $this->producedOptionalMaterialsAfterCommerce = $data['producedOptionalMaterialsAfterCommerce'];
    $this->canBuildForFree = $data['canBuildForFree'];
    $this->updateInNewAgeBuildForFree = $data['updateInNewAgeBuildForFree'];
  }

  function jsonArraySerialize()
  {

    $cards = array();
    foreach($this->cards as $key => $card) {
      array_push($cards, $card->jsonArraySerialize());
    }
    
    return [
      'id' => (string) $this->id,
      'gameId' => (string) $this->gameId,
      'name' => $this->name,
      'secretSkill' => $this->secretSkill,
      'cards' => $cards,
      'board' => $this->board->jsonArraySerialize(),
      'isPlayedForTurn' => $this->isPlayedForTurn,
      'leftPlayerId' => $this->leftPlayerId,
      'rightPlayerId' => $this->rightPlayerId,
      'victoryPoints' => $this->victoryPoints,
      'victoryTokens' => $this->victoryTokens,
      'defeatTokens' => $this->defeatTokens,
      'coin' => $this->coin,
      'canBuildForFree' => $this->canBuildForFree,
    ];
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
   * Get the value of gameId
   */ 
  public function getGameId()
  {
    return $this->gameId;
  }

  /**
   * Set the value of gameId
   *
   * @return  self
   */ 
  public function setGameId($gameId)
  {
    $this->gameId = $gameId;

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
   * Get the value of secretSkill
   */ 
  public function getSecretSkill()
  {
    return $this->secretSkill;
  }

  /**
   * Set the value of secretSkill
   *
   * @return  self
   */ 
  public function setSecretSkill($secretSkill)
  {
    $this->secretSkill = $secretSkill;

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
   * Get the value of board
   */ 
  public function getBoard()
  {
    return $this->board;
  }

  /**
   * Set the value of board
   *
   * @return  self
   */ 
  public function setBoard($board)
  {
    $this->board = $board;

    return $this;
  }

  /**
   * Get the value of leftPlayerId
   */ 
  public function getLeftPlayerId()
  {
    return $this->leftPlayerId;
  }

  /**
   * Set the value of leftPlayerId
   *
   * @return  self
   */ 
  public function setLeftPlayerId($leftPlayerId)
  {
    $this->leftPlayerId = $leftPlayerId;

    return $this;
  }

  /**
   * Get the value of rightPlayerId
   */ 
  public function getRightPlayerId()
  {
    return $this->rightPlayerId;
  }

  /**
   * Set the value of rightPlayerId
   *
   * @return  self
   */ 
  public function setRightPlayerId($rightPlayerId)
  {
    $this->rightPlayerId = $rightPlayerId;

    return $this;
  }

  /**
   * Get the value of victoryPoints
   */ 
  public function getVictoryPoints()
  {
    return $this->victoryPoints;
  }

  /**
   * Set the value of victoryPoints
   *
   * @return  self
   */ 
  public function setVictoryPoints($victoryPoints)
  {
    $this->victoryPoints = $victoryPoints;

    return $this;
  }

  /**
   * Get the value of victoryTokens
   */ 
  public function getVictoryTokens()
  {
    return $this->victoryTokens;
  }

  /**
   * Set the value of victoryTokens
   *
   * @return  self
   */ 
  public function setVictoryTokens($victoryTokens)
  {
    $this->victoryTokens = $victoryTokens;

    return $this;
  }

  /**
   * Get the value of defeatTokens
   */ 
  public function getDefeatTokens()
  {
    return $this->defeatTokens;
  }

  /**
   * Set the value of defeatTokens
   *
   * @return  self
   */ 
  public function setDefeatTokens($defeatTokens)
  {
    $this->defeatTokens = $defeatTokens;

    return $this;
  }

  /**
   * Get the value of shields
   */ 
  public function getShields()
  {
    return $this->shields;
  }

  /**
   * Set the value of shields
   *
   * @return  self
   */ 
  public function setShields($shields)
  {
    $this->shields = $shields;

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
   * Get the value of coinLeftCommerceSell
   */ 
  public function getCoinLeftCommerceSell()
  {
    return $this->coinLeftCommerceSell;
  }

  /**
   * Set the value of coinLeftCommerceSell
   *
   * @return  self
   */ 
  public function setCoinLeftCommerceSell($coinLeftCommerceSell)
  {
    $this->coinLeftCommerceSell = $coinLeftCommerceSell;

    return $this;
  }

  /**
   * Get the value of coinRightCommerceSell
   */ 
  public function getCoinRightCommerceSell()
  {
    return $this->coinRightCommerceSell;
  }

  /**
   * Set the value of coinRightCommerceSell
   *
   * @return  self
   */ 
  public function setCoinRightCommerceSell($coinRightCommerceSell)
  {
    $this->coinRightCommerceSell = $coinRightCommerceSell;

    return $this;
  }

  /**
   * Get the value of coinLeftCommerceBuy
   */ 
  public function getCoinLeftCommerceBuy()
  {
    return $this->coinLeftCommerceBuy;
  }

  /**
   * Set the value of coinLeftCommerceBuy
   *
   * @return  self
   */ 
  public function setCoinLeftCommerceBuy($coinLeftCommerceBuy)
  {
    $this->coinLeftCommerceBuy = $coinLeftCommerceBuy;

    return $this;
  }

  /**
   * Get the value of coinRightCommerceBuy
   */ 
  public function getCoinRightCommerceBuy()
  {
    return $this->coinRightCommerceBuy;
  }

  /**
   * Set the value of coinRightCommerceBuy
   *
   * @return  self
   */ 
  public function setCoinRightCommerceBuy($coinRightCommerceBuy)
  {
    $this->coinRightCommerceBuy = $coinRightCommerceBuy;

    return $this;
  }

  /**
   * Get the value of materialCostLeft
   */ 
  public function getMaterialCostLeft()
  {
    return $this->materialCostLeft;
  }

  /**
   * Set the value of materialCostLeft
   *
   * @return  self
   */ 
  public function setMaterialCostLeft($materialCostLeft)
  {
    $this->materialCostLeft = $materialCostLeft;

    return $this;
  }

  /**
   * Get the value of materialCostRight
   */ 
  public function getMaterialCostRight()
  {
    return $this->materialCostRight;
  }

  /**
   * Set the value of materialCostRight
   *
   * @return  self
   */ 
  public function setMaterialCostRight($materialCostRight)
  {
    $this->materialCostRight = $materialCostRight;

    return $this;
  }

  /**
   * Get the value of producedMaterials
   */ 
  public function getProducedMaterials()
  {
    return $this->producedMaterials;
  }

  /**
   * Set the value of producedMaterials
   *
   * @return  self
   */ 
  public function setProducedMaterials($producedMaterials)
  {
    $this->producedMaterials = $producedMaterials;

    return $this;
  }

  /**
   * Get the value of producedMaterialsAfterCommerce
   */ 
  public function getProducedMaterialsAfterCommerce()
  {
    return $this->producedMaterialsAfterCommerce;
  }

  /**
   * Set the value of producedMaterialsAfterCommerce
   *
   * @return  self
   */ 
  public function setProducedMaterialsAfterCommerce($producedMaterialsAfterCommerce)
  {
    $this->producedMaterialsAfterCommerce = $producedMaterialsAfterCommerce;

    return $this;
  }

  /**
   * Get the value of producedOptionalMaterials
   */ 
  public function getProducedOptionalMaterials()
  {
    return $this->producedOptionalMaterials;
  }

  /**
   * Set the value of producedOptionalMaterials
   *
   * @return  self
   */ 
  public function setProducedOptionalMaterials($producedOptionalMaterials)
  {
    $this->producedOptionalMaterials = $producedOptionalMaterials;

    return $this;
  }

  /**
   * Get the value of producedOptionalMaterialsAfterCommerce
   */ 
  public function getProducedOptionalMaterialsAfterCommerce()
  {
    return $this->producedOptionalMaterialsAfterCommerce;
  }

  /**
   * Set the value of producedOptionalMaterialsAfterCommerce
   *
   * @return  self
   */ 
  public function setProducedOptionalMaterialsAfterCommerce($producedOptionalMaterialsAfterCommerce)
  {
    $this->producedOptionalMaterialsAfterCommerce = $producedOptionalMaterialsAfterCommerce;

    return $this;
  }

  /**
   * Get the value of canBuildForFree
   */ 
  public function getCanBuildForFree()
  {
    return $this->canBuildForFree;
  }

  /**
   * Set the value of canBuildForFree
   *
   * @return  self
   */ 
  public function setCanBuildForFree($canBuildForFree)
  {
    $this->canBuildForFree = $canBuildForFree;

    return $this;
  }

  /**
   * Get the value of updateInNewAgeBuildForFree
   */ 
  public function getUpdateInNewAgeBuildForFree()
  {
    return $this->updateInNewAgeBuildForFree;
  }

  /**
   * Set the value of updateInNewAgeBuildForFree
   *
   * @return  self
   */ 
  public function setUpdateInNewAgeBuildForFree($updateInNewAgeBuildForFree)
  {
    $this->updateInNewAgeBuildForFree = $updateInNewAgeBuildForFree;

    return $this;
  }

  /**
   * Get the value of isPlayedForTurn
   */ 
  public function getIsPlayedForTurn()
  {
    return $this->isPlayedForTurn;
  }

  /**
   * Set the value of isPlayedForTurn
   *
   * @return  self
   */ 
  public function setIsPlayedForTurn($isPlayedForTurn)
  {
    $this->isPlayedForTurn = $isPlayedForTurn;

    return $this;
  }
}

?>
