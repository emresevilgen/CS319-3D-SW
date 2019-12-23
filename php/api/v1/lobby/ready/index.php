<?php
/**
 * @author: Aziz Utku Kağıtcı
 */
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
require_once dirname(__FILE__) . '/../../models/Lobby.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$username = $_POST['username'];
$token = $_POST['token'];
$ready = $_POST['ready'];

try {
  $user = (new User())->setUsername($username)->setToken($token); 
  $user->checkAuthenticationWithToken()->retrieveUserData();

  $lobby = (new Lobby())->setId($user->getJoinedLobby());
  $lobby->ready($username, $ready);
  $lobby->retrieveLobbyDataViaId($username);

  // Check all users is ready if the admin click the game start button
  if ($username == $lobby->getAdmin()) {
    $lobbyUsers = $lobby->getUsers();
    // If there are enough users in the lobby. (3 or 4 users are needed to start game)
    if (count($lobbyUsers) == 3 || count($lobbyUsers) == 4) {
      $isAllReady = true;
      foreach ($lobbyUsers as $key => $lobbyUser) {
        $isAllReady = $isAllReady && $lobbyUser->getIsReady();
      }

      // If all users are ready, start the game.
      if($isAllReady) {
        $game = $lobby->startGame();
        $lobby->setGameId((string)$game->getId());
      }
    }
  }

  $user->updateLastTimestamp();

  showResponseInJson($lobby->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
