package com.example.ecom.controllers;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

  private final BlobContainerClient container;

  public ImageController(BlobContainerClient container) {
    this.container = container;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
    String blobName = file.getOriginalFilename();
    BlockBlobClient blob = container.getBlobClient(blobName).getBlockBlobClient();
    blob.upload(file.getInputStream(), file.getSize(), true);
    return ResponseEntity.ok("Uploaded " + blobName);
  }

  @GetMapping("/{name}")
  public ResponseEntity<String> url(@PathVariable String name) {
    return ResponseEntity.ok(container.getBlobClient(name).getBlobUrl());
  }
}