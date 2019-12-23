<?php

include_once dirname(__FILE__) . '/DatabaseManager.php';
include_once dirname(__FILE__) . '/TimeManager.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class ConfigManager {
  private $databaseManager;
  private $timeManager;

  function __construct()
  {
    $this->databaseManager = new DatabaseManager();
    $this->timeManager = new TimeManager();
  }

  public function getDatabase() {
    return $this->databaseManager->getDb();
  }
  
  function getClient() {
    return $this->databaseManager->getMongoClient();
  }

  function getCurrentTimestamp() {
    return $this->timeManager->getCurrentTimestamp();
  }

  function getExpiresIn() {
    return $this->timeManager::TOKEN_EXPIRES_IN;
  }
}

?>