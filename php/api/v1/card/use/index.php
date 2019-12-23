<?php
/**
 * @author: Aziz Utku Kağıtcı
 */
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
require_once dirname(__FILE__) . '/../../models/Game.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$username = $_POST['username'];
$token = $_POST['token'];
$cardId = $_POST['cardId'];
$selectionType = $_POST['selectionType'];
$freeBuilding = $_POST['freeBuilding'];

try {
  $user = (new User())->setUsername($username)->setToken($token); 
  $user->checkAuthenticationWithToken()->retrieveUserData();

  $game = (new Game())->setId($user->getJoinedGame());
  $game->retrieveGameDataViaId();

  $game->useCard($username, $cardId, $selectionType, $freeBuilding);

  $user->updateLastTimestamp();

  showResponseInJson($game->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>

