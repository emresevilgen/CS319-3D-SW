<?php
/**
 * @author: Aziz Utku Kağıtcı
 */

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
require_once dirname(__FILE__) . '/../../models/Materials.php';
require_once dirname(__FILE__) . '/../../models/Game.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$username = $_POST['username'];
$token = $_POST['token'];
$isWithLeft = $_POST['isWithLeft'];
$commerceMaterialsJSON = $_POST['commerceMaterials'];

try {
  $user = (new User())->setUsername($username)->setToken($token); 
  $user->checkAuthenticationWithToken()->retrieveUserData();

  $game = (new Game())->setId($user->getJoinedGame());
  $game->retrieveGameDataViaId();

  
  $commerceMaterialsArr = json_decode($commerceMaterialsJSON, true);
  $commerceMaterials = new Materials($commerceMaterialsArr['coin'], $commerceMaterialsArr['clay'], $commerceMaterialsArr['ore'], $commerceMaterialsArr['stone'], $commerceMaterialsArr['lumber'], $commerceMaterialsArr['press'], $commerceMaterialsArr['loom'], $commerceMaterialsArr['glassworks']);

  $game->commerce($isWithLeft, $username, $commerceMaterials);

  $user->updateLastTimestamp();

  showResponseInJson($game->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
