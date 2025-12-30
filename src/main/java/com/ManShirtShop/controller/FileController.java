package com.ManShirtShop.controller;

import com.ManShirtShop.service.LocalFileService.LocalFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class FileController {

    @Autowired
    private LocalFileService localFileService;

    @Operation(summary = "Upload ảnh bài viết")
    @ApiResponse(responseCode = "200", description = "Thành công")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping(value = "/upload-firebase", consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadFile(@RequestParam("files") MultipartFile[] files) {
        List<String> listURL = new ArrayList<>();
        for (MultipartFile file : files) {
            String urlImage = "";
            try {
                urlImage = localFileService.saveFile(file);
                listURL.add(urlImage);
            } catch (Exception e) {
                //  throw internal error;
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(listURL, HttpStatus.OK);
    }

    @Operation(summary = "Download/View file")
    @ApiResponse(responseCode = "200", description = "Thành công")
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = localFileService.getFile(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "application/octet-stream";
                try {
                    contentType = java.nio.file.Files.probeContentType(filePath);
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }
                } catch (Exception e) {
                    // Use default content type
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
