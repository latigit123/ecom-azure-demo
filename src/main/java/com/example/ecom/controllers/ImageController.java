package com.example.ecom.controllers;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

  private final BlobContainerClient container;

  public ImageController(BlobContainerClient container) {
    this.container = container;
  }

  // Match the UI: POST /api/images/upload/{filename}
  @PostMapping("/upload/{filename}")
  public ResponseEntity<String> uploadWithPath(
      @PathVariable String filename,
      @RequestParam("file") MultipartFile file) throws Exception {

    BlockBlobClient blob = container.getBlobClient(filename).getBlockBlobClient();
    blob.upload(file.getInputStream(), file.getSize(), true);
    return ResponseEntity.ok("/api/images/" + filename);
  }

  // Simple fetch: GET /api/images/{filename} -> returns the public URL
  @GetMapping("/{filename}")
  public ResponseEntity<String> getUrl(@PathVariable String filename) {
    return ResponseEntity.ok(container.getBlobClient(filename).getBlobUrl());
  }
}