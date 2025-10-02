package com.example.ecom.config;

import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobConfig {

  // Read from Azure App Settings / env vars
  @Value("${AZURE_STORAGE_CONNECTION_STRING}")
  private String connectionString;

  // default to "images" if not set
  @Value("${AZURE_BLOB_CONTAINER:images}")
  private String containerName;

  @Bean
  public BlobServiceClient blobServiceClient() {
    return new BlobServiceClientBuilder()
        .connectionString(connectionString)
        .buildClient();
  }

  @Bean
  public BlobContainerClient blobContainerClient(BlobServiceClient svc) {
    BlobContainerClient client = svc.getBlobContainerClient(containerName);
    if (!client.exists()) client.create(); // create if missing
    return client;
  }
}