package com.example.demo.Controllers;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.AccessTier;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedInputStream;

@Controller
public class UploadController {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @GetMapping("/up")
    public String index() {
        return "upload";
    }
    
    @PostMapping("/uploadimg")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("message", "Please select a file to upload.");
            return ResponseEntity.badRequest().body(response);
        }

        try (InputStream originalInputStream = file.getInputStream();
             BufferedInputStream inputStream = new BufferedInputStream(originalInputStream)) {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            // Create a blob client
            BlockBlobClient blobClient = new BlobClientBuilder()
                    .connectionString(connectionString) // Make sure connectionString is defined
                    .containerName(containerName) // Make sure containerName is defined
                    .blobName(fileName)
                    .buildClient()
                    .getBlockBlobClient();

            // Upload the file
            blobClient.upload(inputStream, file.getSize(), true);
            blobClient.setAccessTier(AccessTier.COOL);

            String fileUrl = "https://innotest.blob.core.windows.net/webform/" + fileName;

            response.put("message", "File uploaded successfully: " + fileName);
            response.put("url", fileUrl);
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "Failed to upload file: " + e.getMessage());
            response.put("success", "false");
            return ResponseEntity.status(500).body(response);
        }
    }
}
