<?php
/**
 * @author: Aziz Utku Kağıtcı
 */
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../models/User.php';
require_once dirname(__FILE__) . '/../models/Game.php';
include_once dirname(__FILE__) . '/../utils/Utils.php';

$username = $_POST['username'];
$token = $_POST['token'];

try {
  $user = (new User())->setUsername($username)->setToken($token); 
  $user->checkAuthenticationWithToken()->retrieveUserData();

  $game = (new Game())->setId($user->getJoinedGame());
  $game->retrieveGameDataViaId();

  // To show this user' wonder board bottom of the screen.
  $game->changePlayersOrder($username);

  $user->updateLastTimestamp();

  showResponseInJson($game->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
