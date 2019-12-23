<?php

/**
 * @author: Aziz Utku Kağıtcı
 */

 
/*
 * Check all fields are filled.
 */
function checkFields($fields) {
  $errorMessage = "";
  foreach($fields as $key => $value){
    if(empty($value) && !isset($value)) {
      $errorMessage .= ucfirst($key) . " can't be blank.\n";
    }
  }

  if (strlen($errorMessage)) {
    throw new Exception(trim($errorMessage));
  }
}

function showResponseInJson($payload, $success = true, $message = "OK") {
  $response = array(
    'success' => $success,
    'message' => $message,
    'payload' => $success ? $payload : null,
  );
  
  echo json_encode($response,JSON_PRETTY_PRINT);
}

?>