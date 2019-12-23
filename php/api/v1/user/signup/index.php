<?php

/**
 * @author: Aziz Utku Kağıtcı
 */

 
header('Content-Type: application/json');
require_once dirname(__FILE__) . '/../../models/User.php';
include_once dirname(__FILE__) . '/../../utils/Utils.php';

$ip = $_SERVER['REMOTE_ADDR'];
$username = $_POST['username'];
$password = $_POST['password'];
$name = $_POST['name'];

try {
  $user = (new User())->setUsername($username)->setPassword($password)->setRegisteredIp($ip)->setName($name)->signup();
  showResponseInJson($user->jsonArraySerialize());
} catch (Exception $e) {
  showResponseInJson(null, false, $e->getMessage());
}

?>
