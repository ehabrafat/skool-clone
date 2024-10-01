package com.example.Skool.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam(value = "file") MultipartFile[] files
    ){
        return ResponseEntity.ok(this.storageService.uploadFile(files));
    }
}
