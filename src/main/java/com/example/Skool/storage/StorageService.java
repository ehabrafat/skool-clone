package com.example.Skool.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public List<String> uploadFile(MultipartFile...files) {
        List<String> fileUrls = new ArrayList<>();
        Arrays.stream(files).forEach(multipartFile -> {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename().replace(" ", "_");
            File file = convertMultipartFileToFile(multipartFile);
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
            file.delete();
            String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
            fileUrls.add(fileUrl);
        });
        return fileUrls;
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File result = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try(FileOutputStream outputStream = new FileOutputStream(result)){
            outputStream.write(file.getBytes());
        } catch (IOException exception){
            log.error("Error converting multipart file", exception);
        }
        return result;
    }
}
