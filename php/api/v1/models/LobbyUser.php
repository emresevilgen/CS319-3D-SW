<?php

require_once dirname(__FILE__) . '/../../../libraries/vendor/autoload.php';
include_once dirname(__FILE__) . '/../configs/ConfigManager.php';
include_once dirname(__FILE__) . '/../utils/Utils.php';
include_once dirname(__FILE__) . '/../interfaces/JSONResponse.php';

/**
 * @author: Aziz Utku Kağıtcı
 */
class LobbyUser implements MongoDB\BSON\Persistable, JSONResponse {

  private $username;
  private $isReady;
  private $isActive;
  private $lastActiveAt;

  function __construct($username)
  {
    $this->username = $username;
    $this->isReady = false;
    $this->isActive = true;
    $this->lastActiveAt = new MongoDB\BSON\UTCDateTime;
  }

  // Needed for MongoDB\BSON\Persistable
  function bsonSerialize()
  {
    return [
      'username' => $this->username,
      'isReady' => $this->isReady,
      'isActive' => $this->isActive,
      'lastActiveAt' => $this->lastActiveAt
    ];
  }

  // Needed for MongoDB\BSON\Persistable
  function bsonUnserialize(array $data)
  {
    $this->username  = $data['username'];
    $this->isReady  = $data['isReady'];
    $this->isActive  = $data['isActive'];
    $this->lastActiveAt  = $data['lastActiveAt'];
  }

  // Needed for JSONResponse
  function jsonArraySerialize()
  {
    return [
      'username' => $this->username,
      'isReady' => $this->isReady,
      'isActive' => $this->isActive,
    ];
  }

  // Getters and setters
  
  /**
   * Get the value of username
   */ 
  public function getUsername()
  {
    return $this->username;
  }

  /**
   * Set the value of username
   *
   * @return  self
   */ 
  public function setUsername($username)
  {
    $this->username = $username;

    return $this;
  }

  /**
   * Get the value of isReady
   */ 
  public function getIsReady()
  {
    return $this->isReady;
  }

  /**
   * Set the value of isReady
   *
   * @return  self
   */ 
  public function setIsReady($isReady)
  {
    $this->isReady = $isReady;

    return $this;
  }

  /**
   * Get the value of isActive
   */ 
  public function getIsActive()
  {
    return $this->isActive;
  }

  /**
   * Set the value of isActive
   *
   * @return  self
   */ 
  public function setIsActive($isActive)
  {
    $this->isActive = $isActive;

    return $this;
  }

  /**
   * Get the value of lastActiveAt
   */ 
  public function getLastActiveAt()
  {
    return $this->lastActiveAt;
  }

  /**
   * Set the value of lastActiveAt
   *
   * @return  self
   */ 
  public function setLastActiveAt($lastActiveAt)
  {
    $this->lastActiveAt = $lastActiveAt;

    return $this;
  }
}

?>