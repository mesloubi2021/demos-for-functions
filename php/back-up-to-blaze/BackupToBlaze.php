<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;


class BackupToBlaze {
  
  private $client;
  
  
  public function __construct( $endpoint, $projectId, $apiKey ) {
    
    if (is_null($endpoint) || is_null($projectId) || is_null($apiKey)) {
       throw new Exception("Endpoint, Project ID, & Api Key are required");
    }
    
    $this->client = new Client();
    $this->client->setEndpoint($endpoint)->setProject($projectId)->setKey($apiKey);
    
    return $this;
  
  }
  
  
  public function getFile( $fileId ) {
  
    return $this;
    
  }
  
  
  public function backup( ) {
  
      return $this;
  
  }

  
}
