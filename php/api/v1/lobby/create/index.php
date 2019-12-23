<?php
/**
 * @author: Aziz Utku Kağıtcı
 */
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
require_once dirname(__FILE__) . '/../../models/Mode.php';
require_once dirname(__FILE__) . '/../../models/Lobby.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$username = $_POST['username'];
$token = $_POST['token'];
$name = $_POST['name'];
$modeJSON = $_POST['mode'];

try {
  $user = (new User())->setUsername($username)->setToken($token); 
  $user->checkAuthenticationWithToken()->isAvailableForLobby();

  $modeArr = json_decode($modeJSON, true);
  $mode = new Mode($modeArr['shufflePlaces'], $modeArr['secretSkills'], $modeArr['invalidMovePenalty'], $modeArr['loot']);

  $lobby = (new Lobby())->setAdmin($username)->setName($name)->setMode($mode)->createLobby();

  $user->updateJoinedLobby($lobby->getId());
  $user->updateLastTimestamp();

  showResponseInJson($lobby->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
