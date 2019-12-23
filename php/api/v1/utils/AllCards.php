<?php


require_once dirname(__FILE__) . '/../models/Card.php';
require_once dirname(__FILE__) . '/../models/RedCard.php';
require_once dirname(__FILE__) . '/../models/BlueCard.php';
require_once dirname(__FILE__) . '/../models/BrownCard.php';
require_once dirname(__FILE__) . '/../models/GreenCard.php';
require_once dirname(__FILE__) . '/../models/GrayCard.php';
require_once dirname(__FILE__) . '/../models/PurpleCard.php';
require_once dirname(__FILE__) . '/../models/YellowCard.php';
require_once dirname(__FILE__) . '/../models/Materials.php';
require_once dirname(__FILE__) . '/Constants.php';

/**
 * @author: Meryem Banu Cavlak
 */
class AllCards {

  private $allCards;
  private $cardsForAge1;
  private $cardsForAge2;
  private $cardsForAge3;

  function __construct()
  {
    $this->prepareCards();
    $this->shuffleCards();
  }

  /**
   * Give one wonder board and remove it from array.
   *
   * @return Card[]
   */ 
  function takeSevenCards($ageNumber) {
    if (isset($this->allCards[$ageNumber - 1])) {
      //Get last seven cards in the array of cards for that age.
      $cards = array();
      for ($i = 0; $i < 7; ++$i) {
        $lastIndex = count($this->allCards[$ageNumber - 1]) - 1;
        $card =  $this->allCards[$ageNumber - 1][$lastIndex];
        unset($this->allCards[$ageNumber - 1][$lastIndex]);
        array_push($cards, $card);
      }
    
      return $cards;
    } else {
      throw new Exception("Cards for age " . $ageNumber . " are not initiliazed!");
    }
  }

  private function prepareCards() {
    $this->allCards = array();
    $this->cardsForAge1 = array();
    $this->cardsForAge2 = array();
    $this->cardsForAge3 = array();

    // age 1 initialization
    array_push($this->cardsForAge1, (new BrownCard())->setId('1')->setName('Lumber Yard')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setLumber(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('2')->setName('Lumber Yard')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setLumber(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('3')->setName('Stone Pit')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setStone(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('4')->setName('Clay Pool')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setClay(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('5')->setName('Ore Vein')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setOre(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('6')->setName('Ore Vein')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setOre(1));
    array_push($this->cardsForAge1, (new BrownCard())->setId('7')->setName('Excavation')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setLumber(0.5)->setClay(0.5));
    array_push($this->cardsForAge1, (new BrownCard())->setId('8')->setName('Clay Pit')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setOre(0.5)->setClay(0.5));
    array_push($this->cardsForAge1, (new BrownCard())->setId('9')->setName('Timber Yard')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setLumber(0.5)->setStone(0.5));
    array_push($this->cardsForAge1, (new GrayCard())->setId('10')->setName('Loom')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setLoom(1));
    array_push($this->cardsForAge1, (new GrayCard())->setId('11')->setName('Glassworks')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setGlassworks(1));
    array_push($this->cardsForAge1, (new GrayCard())->setId('12')->setName('Press')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setPress(1));
    array_push($this->cardsForAge1, (new BlueCard())->setId('13')->setName('Pawnshop')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setVictoryPoints(3));
    array_push($this->cardsForAge1, (new BlueCard())->setId('14')->setName('Baths')->setCost((new Materials())->setStone(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setVictoryPoints(3));
    array_push($this->cardsForAge1, (new BlueCard())->setId('15')->setName('Altar')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setVictoryPoints(2));
    array_push($this->cardsForAge1, (new BlueCard())->setId('16')->setName('Theater')->setCost(new Materials())->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setVictoryPoints(2));
    array_push($this->cardsForAge1, (new RedCard())->setId('17')->setName('Stockade')->setCost((new Materials())->setLumber(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setShield(1));
    array_push($this->cardsForAge1, (new RedCard())->setId('18')->setName('Barracks')->setCost((new Materials())->setOre(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setShield(1));
    array_push($this->cardsForAge1, (new RedCard())->setId('19')->setName('Guard Tower')->setCost((new Materials())->setClay(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setShield(1));
    array_push($this->cardsForAge1, (new RedCard())->setId('20')->setName('Guard Tower')->setCost((new Materials())->setClay(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setShield(1));
    array_push($this->cardsForAge1, (new GreenCard())->setId('21')->setName('Apothecary')->setCost((new Materials())->setLoom(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setCompassPoints(1));
    array_push($this->cardsForAge1, (new GreenCard())->setId('22')->setName('Workshop')->setCost((new Materials())->setGlassworks(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setWheelPoints(1));
    array_push($this->cardsForAge1, (new GreenCard())->setId('23')->setName('Scriptorium')->setCost((new Materials())->setPress(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setScriptPoints(1));
    array_push($this->cardsForAge1, (new GreenCard())->setId('24')->setName('Scriptorium')->setCost((new Materials())->setPress(1))->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setScriptPoints(1));
    array_push($this->cardsForAge1, (new YellowCard())->setId('25')->setName('Tavern')->setCost((new Materials()))->setFriendCard(null)->setAge(1)->setPlayerLimit(4)->setGoldCoin(5));
    array_push($this->cardsForAge1, (new YellowCard())->setId('26')->setName('East Trading Post')->setCost((new Materials()))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setRightCostReduction(true));
    array_push($this->cardsForAge1, (new YellowCard())->setId('27')->setName('West Trading Post')->setCost((new Materials()))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setLeftCostReduction(true));
    array_push($this->cardsForAge1, (new YellowCard())->setId('28')->setName('Market Place')->setCost((new Materials()))->setFriendCard(null)->setAge(1)->setPlayerLimit(3)->setLeftCostReduction(true)->setRightCostReduction(true));

    // age 2 initialization
    array_push($this->cardsForAge2, (new BrownCard())->setId('29')->setName('SawMill')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setLumber(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('30')->setName('SawMill')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setLumber(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('31')->setName('Quarry')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setStone(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('32')->setName('Quarry')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setStone(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('33')->setName('Brickyard')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setClay(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('34')->setName('Brickyard')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setClay(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('35')->setName('Foundry')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setOre(2));
    array_push($this->cardsForAge2, (new BrownCard())->setId('36')->setName('Foundry')->setCost((new Materials())->setCoin(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setOre(2));
    array_push($this->cardsForAge2, (new GrayCard())->setId('37')->setName('Loom')->setCost(new Materials())->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setLoom(1));
    array_push($this->cardsForAge2, (new GrayCard())->setId('38')->setName('Glassworks')->setCost(new Materials())->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setGlassworks(1));
    array_push($this->cardsForAge2, (new GrayCard())->setId('39')->setName('Press')->setCost(new Materials())->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setPress(1));
    array_push($this->cardsForAge2, (new BlueCard())->setId('40')->setName('Aqueduct')->setCost((new Materials())->setStone(3))->setFriendCard('Baths')->setAge(2)->setPlayerLimit(3)->setVictoryPoints(5));
    array_push($this->cardsForAge2, (new BlueCard())->setId('41')->setName('Temple')->setCost((new Materials())->setLumber(1)->setClay(1)->setGlassworks(1))->setFriendCard('Altar')->setAge(2)->setPlayerLimit(3)->setVictoryPoints(3));
    array_push($this->cardsForAge2, (new BlueCard())->setId('42')->setName('Statue')->setCost((new Materials())->setLumber(1)->setOre(2))->setFriendCard('Theater')->setAge(2)->setPlayerLimit(3)->setVictoryPoints(4));
    array_push($this->cardsForAge2, (new BlueCard())->setId('43')->setName('Courthouse')->setCost((new Materials())->setClay(2)->setLoom(1))->setFriendCard('Scriptorium')->setAge(2)->setPlayerLimit(3)->setVictoryPoints(4));
    array_push($this->cardsForAge2, (new RedCard())->setId('44')->setName('Walls')->setCost((new Materials())->setStone(3))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setShield(2));
    array_push($this->cardsForAge2, (new RedCard())->setId('45')->setName('Training Ground')->setCost((new Materials())->setLumber(1)->setOre(2))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setShield(2));
    array_push($this->cardsForAge2, (new RedCard())->setId('46')->setName('Stables')->setCost((new Materials())->setLumber(1)->setClay(1)->setOre(1))->setFriendCard('Apothecary')->setAge(2)->setPlayerLimit(3)->setShield(2));
    array_push($this->cardsForAge2, (new RedCard())->setId('47')->setName('Archery Range')->setCost((new Materials())->setLumber(2)->setOre(1))->setFriendCard('Workshop')->setAge(2)->setPlayerLimit(3)->setShield(2));
    array_push($this->cardsForAge2, (new GreenCard())->setId('48')->setName('Dispensary')->setCost((new Materials())->setOre(2)->setGlassworks(1))->setFriendCard('Apothecary')->setAge(2)->setPlayerLimit(3)->setCompassPoints(1));
    array_push($this->cardsForAge2, (new GreenCard())->setId('49')->setName('Dispensary')->setCost((new Materials())->setOre(2)->setGlassworks(1))->setFriendCard('Apothecary')->setAge(2)->setPlayerLimit(4)->setCompassPoints(1));
    array_push($this->cardsForAge2, (new GreenCard())->setId('50')->setName('Laboratory')->setCost((new Materials())->setClay(2)->setPress(1))->setFriendCard('Workshop')->setAge(2)->setPlayerLimit(3)->setWheelPoints(1));
    array_push($this->cardsForAge2, (new GreenCard())->setId('51')->setName('Library')->setCost((new Materials())->setStone(2)->setLoom(1))->setFriendCard('Scriptorium')->setAge(2)->setPlayerLimit(3)->setScriptPoints(1));
    array_push($this->cardsForAge2, (new GreenCard())->setId('52')->setName('School')->setCost((new Materials())->setLumber(1)->setPress(1))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setScriptPoints(1));
    array_push($this->cardsForAge2, (new YellowCard())->setId('53')->setName('Forum')->setCost((new Materials())->setClay(2))->setFriendCard('East Trading Post-West Trading Post')->setAge(2)->setPlayerLimit(3)->setLoom(0.5)->setGlassworks(0.5)->setPress(0.5));
    array_push($this->cardsForAge2, (new YellowCard())->setId('54')->setName('Caravansery')->setCost((new Materials())->setLumber(2))->setFriendCard('Market Place')->setAge(2)->setPlayerLimit(3)->setClay(0.5)->setOre(0.5)->setLumber(0.5)->setOre(0.5));
    array_push($this->cardsForAge2, (new YellowCard())->setId('55')->setName('Vineyard')->setCost((new Materials()))->setFriendCard(null)->setAge(2)->setPlayerLimit(3)->setGoldForBrown(true));
    array_push($this->cardsForAge2, (new YellowCard())->setId('56')->setName('Bazar')->setCost((new Materials()))->setFriendCard(null)->setAge(2)->setPlayerLimit(4)->setGoldForGray(true));


    // age 3 initialization
    array_push($this->cardsForAge3, (new BlueCard())->setId('57')->setName('Pantheon')->setCost((new Materials())->setClay(2)->setOre(2)->setPress(1)->setLoom(1)->setGlassworks(1))->setFriendCard('Temple')->setAge(3)->setPlayerLimit(3)->setVictoryPoints(7));
    array_push($this->cardsForAge3, (new BlueCard())->setId('58')->setName('Gardens')->setCost((new Materials())->setLumber(1)->setClay(2))->setFriendCard('Statue')->setAge(3)->setPlayerLimit(3)->setVictoryPoints(5));
    array_push($this->cardsForAge3, (new BlueCard())->setId('59')->setName('Gardens')->setCost((new Materials())->setLumber(1)->setClay(2))->setFriendCard('Statue')->setAge(3)->setPlayerLimit(4)->setVictoryPoints(5));
    array_push($this->cardsForAge3, (new BlueCard())->setId('60')->setName('Town Hall')->setCost((new Materials())->setStone(2)->setOre(1)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3)->setVictoryPoints(6));
    array_push($this->cardsForAge3, (new BlueCard())->setId('61')->setName('Palace')->setCost((new Materials())->setLumber(1)->setStone(1)->setClay(1)->setOre(1)->setPress(1)->setLoom(1)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3)->setVictoryPoints(8));
    array_push($this->cardsForAge3, (new BlueCard())->setId('62')->setName('Senate')->setCost((new Materials())->setLumber(2)->setStone(1)->setOre(1))->setFriendCard('Library')->setAge(3)->setPlayerLimit(3)->setVictoryPoints(6));
    array_push($this->cardsForAge3, (new RedCard())->setId('63')->setName('Fortifications')->setCost((new Materials())->setStone(1)->setOre(3))->setFriendCard('Walls')->setAge(3)->setPlayerLimit(3)->setShield(3));
    array_push($this->cardsForAge3, (new RedCard())->setId('64')->setName('Circus')->setCost((new Materials())->setStone(3)->setOre(1))->setFriendCard('Training Ground')->setAge(3)->setPlayerLimit(4)->setShield(3));
    array_push($this->cardsForAge3, (new RedCard())->setId('65')->setName('Arsenal')->setCost((new Materials())->setLumber(2)->setOre(1)->setLoom(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3)->setShield(3));
    array_push($this->cardsForAge3, (new RedCard())->setId('66')->setName('Arsenal')->setCost((new Materials())->setLumber(2)->setOre(1)->setLoom(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(4)->setShield(3));
    array_push($this->cardsForAge3, (new RedCard())->setId('67')->setName('Siege Workshop')->setCost((new Materials())->setLumber(1)->setClay(3))->setFriendCard('Laboratory')->setAge(3)->setPlayerLimit(3)->setShield(3));
    array_push($this->cardsForAge3, (new GreenCard())->setId('68')->setName('Lodge')->setCost((new Materials())->setClay(2)->setPress(1)->setLoom(1))->setFriendCard('Dispensary')->setAge(3)->setPlayerLimit(3)->setCompassPoints(1));
    array_push($this->cardsForAge3, (new GreenCard())->setId('69')->setName('Observatory')->setCost((new Materials())->setOre(2)->setLoom(1)->setGlassworks(1))->setFriendCard('Laboratory')->setAge(3)->setPlayerLimit(3)->setWheelPoints(1));
    array_push($this->cardsForAge3, (new GreenCard())->setId('70')->setName('University')->setCost((new Materials())->setLumber(2)->setPress(1)->setGlassworks(1))->setFriendCard('Library')->setAge(3)->setPlayerLimit(3)->setScriptPoints(1));
    array_push($this->cardsForAge3, (new GreenCard())->setId('71')->setName('University')->setCost((new Materials())->setLumber(2)->setPress(1)->setGlassworks(1))->setFriendCard('Library')->setAge(3)->setPlayerLimit(4)->setScriptPoints(1));
    array_push($this->cardsForAge3, (new GreenCard())->setId('72')->setName('Academy')->setCost((new Materials())->setStone(3)->setGlassworks(1))->setFriendCard('School')->setAge(3)->setPlayerLimit(3)->setCompassPoints(1));
    array_push($this->cardsForAge3, (new GreenCard())->setId('73')->setName('Study')->setCost((new Materials())->setLumber(1)->setPress(1)->setLoom(1))->setFriendCard('School')->setAge(3)->setPlayerLimit(3)->setWheelPoints(1));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('74')->setName(Constants::WORKERS_GUILD)->setCost((new Materials())->setLumber(1)->setStone(1)->setClay(1)->setOre(2))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('75')->setName(Constants::CRAFTSMAN_GUILD)->setCost((new Materials())->setStone(2)->setOre(2))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('76')->setName(Constants::TRADERS_GUILD)->setCost((new Materials())->setPress(1)->setLoom(1)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('77')->setName(Constants::PHILOSOPHERS_GUILD)->setCost((new Materials())->setClay(3)->setPress(1)->setLoom(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('78')->setName(Constants::SPIES_GUILD)->setCost((new Materials())->setClay(3)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('79')->setName(Constants::STRATEGISTS_GUILD)->setCost((new Materials())->setStone(1)->setOre(2)->setLoom(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('80')->setName(Constants::SHIPOWNERS_GUILD)->setCost((new Materials())->setLumber(3)->setPress(1)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('81')->setName(Constants::SCIENTISTS_GUILD)->setCost((new Materials())->setLumber(2)->setOre(2)->setPress(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('82')->setName(Constants::MAGICSTRATES_GUILD)->setCost((new Materials())->setLumber(3)->setStone(1)->setLoom(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new PurpleCard())->setId('83')->setName(Constants::BUILDERS_GUILD)->setCost((new Materials())->setStone(2)->setClay(2)->setGlassworks(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(3));
    array_push($this->cardsForAge3, (new YellowCard())->setId('84')->setName('Haven')->setCost((new Materials())->setLumber(1)->setOre(1)->setLoom(1))->setFriendCard('Forum')->setAge(3)->setPlayerLimit(3)->setGoldVictoryForBrown(true));
    array_push($this->cardsForAge3, (new YellowCard())->setId('85')->setName('Haven')->setCost((new Materials())->setLumber(1)->setOre(1)->setLoom(1))->setFriendCard('Forum')->setAge(3)->setPlayerLimit(4)->setGoldVictoryForBrown(true));
    array_push($this->cardsForAge3, (new YellowCard())->setId('86')->setName('Lighthouse')->setCost((new Materials())->setStone(1)->setGlassworks(1))->setFriendCard('Caravansery')->setAge(3)->setPlayerLimit(3)->setGoldVictoryForYellow(true));
    array_push($this->cardsForAge3, (new YellowCard())->setId('87')->setName('Chamber of Commerce')->setCost((new Materials())->setClay(2)->setPress(1))->setFriendCard(null)->setAge(3)->setPlayerLimit(4)->setGoldVictoryForGray(true));
    array_push($this->cardsForAge3, (new YellowCard())->setId('88')->setName('Arena')->setCost((new Materials())->setStone(2)->setOre(1))->setFriendCard('Dispensary')->setAge(3)->setPlayerLimit(3)->setGoldVictoryForWonderStages(true));
  
    return $this;
  }

  private function shuffleCards() {
    $this->allCards = array();

    shuffle($this->cardsForAge1);
    shuffle($this->cardsForAge2);
    shuffle($this->cardsForAge3);

    array_push($this->allCards, $this->cardsForAge1);
    array_push($this->allCards, $this->cardsForAge2);
    array_push($this->allCards, $this->cardsForAge3);

    return $this;
  }

  // Getters and Setters

  /**
   * Get the value of cardsForAge1
   */ 
  public function getCardsForAge1()
  {
    return $this->cardsForAge1;
  }

  /**
   * Set the value of cardsForAge1
   *
   * @return  self
   */ 
  public function setCardsForAge1($cardsForAge1)
  {
    $this->cardsForAge1 = $cardsForAge1;

    return $this;
  }

  /**
   * Get the value of cardsForAge2
   */ 
  public function getCardsForAge2()
  {
    return $this->cardsForAge2;
  }

  /**
   * Set the value of cardsForAge2
   *
   * @return  self
   */ 
  public function setCardsForAge2($cardsForAge2)
  {
    $this->cardsForAge2 = $cardsForAge2;

    return $this;
  }

  /**
   * Get the value of cardsForAge3
   */ 
  public function getCardsForAge3()
  {
    return $this->cardsForAge3;
  }

  /**
   * Set the value of cardsForAge3
   *
   * @return  self
   */ 
  public function setCardsForAge3($cardsForAge3)
  {
    $this->cardsForAge3 = $cardsForAge3;

    return $this;
  }

  /**
   * Get the value of allCards
   */ 
  public function getAllCards()
  {
    return $this->allCards;
  }

  /**
   * Set the value of allCards
   *
   * @return  self
   */ 
  public function setAllCards($allCards)
  {
    $this->allCards = $allCards;

    return $this;
  }
}

?>
