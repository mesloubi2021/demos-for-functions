<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;
use BackblazeB2\Client as BBClient;
use BackblazeB2\Bucket as BBBucket;


class BackupToBlaze {
  
  private $client;
  
  private $file;
  
  private $bbClient;
  
  private $bbBucketName; //
  
  
  public function __construct( $endpoint, $projectId, $apiKey ) {
    
    if (is_null($endpoint) || is_null($projectId) || is_null($apiKey)) {
       throw new Exception("Endpoint, Project ID, & Api Key are required");
    }
    
    $this->client = new Client();
    $this->client->setEndpoint($endpoint)->setProject($projectId)->setKey($apiKey);
    
    return $this;
  
  }
  
  
  public function getFile( $fileId ) {
    
    $storage = new Storage( $this->client );
    
    $this->file = $storage->getFileDownload( $fileId );
    
    return $this;
    
  }
  
  
  public function authorizeBB( $accountId, $applicationKey, $bucketName, $options = [] ) {
    
    if (is_null($accountId) || is_null($applicationKey) || is_null($bucketName)) {
       throw new Exception("Failed to authorize");
    }

    $this->bbClient = new BBClient( $accountId , $applicationKey, $options);
    
    $this->bbBucketName = $bucketName;
  
    return $this;
    
  }
  
  
  public function backup( ) {
    
    
      $this->bbClient->upload([
          'BucketName' => $this->bbBucketName,
          'FileName' => 'path/to/upload/to',
          'Body' => 'I am the file content',
      ]);
  
      return $this;
  
  }

  
}
