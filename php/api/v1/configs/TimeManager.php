<?php

/**
 * @author: Aziz Utku Kağıtcı
 */
class TimeManager {
  const TOKEN_EXPIRES_IN = 36000;
  private $currentTimestamp;

  function __construct()
  {
    $this->setTimezone();
  }

  private function setTimezone() {
    date_default_timezone_set('Europe/Istanbul');
  }

  function getCurrentTimestamp() {
    $this->currentTimestamp = date_timestamp_get(date_create());
    return $this->currentTimestamp;
  }
}

?>