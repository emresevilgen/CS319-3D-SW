<?php

/**
 * @author: Aziz Utku Kağıtcı
 */

 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$username = $_POST['username'];
$password = $_POST['password'];

try {
  $user = (new User())->setUsername($username)->setPassword($password); 
  $user->login();
  showResponseInJson($user->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
