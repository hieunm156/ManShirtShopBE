package com.ManShirtShop.controller;

import com.ManShirtShop.service.FirebaseFileService.FirebaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/upload-firebase")
public class FileController {

    @Autowired
    private FirebaseFileService firebaseFileService;
    @Operation(summary = "Upload ảnh bài viết")
    @ApiResponse(responseCode  = "200", description = "Thành công")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping(value = "",consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadFile( @RequestParam("files") MultipartFile[] files) {
        List<String> listURL = new ArrayList<>();
        for (MultipartFile file:files){
            String urlImage = "";
            try {
                urlImage = firebaseFileService.saveTest(file);
                listURL.add(urlImage);
            } catch (Exception e) {
                //  throw internal error;
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(listURL, HttpStatus.OK);
    }
}
