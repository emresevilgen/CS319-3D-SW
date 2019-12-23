<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class DatabaseManager {
  private $db;
  private $mongoClient;

  function __construct()
  {
    $this->connectToDatabase();
  }

  private function connectToDatabase() {
    try { 
      $this->mongoClient = new MongoDB\Client('mongodb://azizutku:VpJi4BZhFSiX3@ds243518.mlab.com:43518/cs319_sw');
      $this->db = $this->mongoClient->cs319_sw;
    } catch (\Exception $e) {
      //\MongoDB\Driver\Exception\BulkWriteException
      echo $e->getMessage();
    }
 
  }

  function getDb() {
    return $this->db;
  }

  function getMongoClient() {
    return $this->mongoClient;
  }
}

?>