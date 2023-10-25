package hu.pte.frontendkeretrendszerekbackend.controller;

import hu.pte.frontendkeretrendszerekbackend.service.ResponsePackaging;
import hu.pte.frontendkeretrendszerekbackend.service.SaveMultipartFileToFolder;
import hu.pte.frontendkeretrendszerekbackend.service.SentenceToList;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
public class translateController {

    private ResponsePackaging responsePackaging;

    public translateController(ResponsePackaging responsePackaging) {
        this.responsePackaging = responsePackaging;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/textTranslate")
    public ResponseEntity<Resource> handleFileUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            if (!multipartFile.getOriginalFilename().endsWith(".txt")) {
                return ResponseEntity.badRequest().body(new ByteArrayResource("Only .txt files are allowed".getBytes()));
            }

            responsePackaging.packaging(multipartFile);

            Path filePath = Path.of("/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/result.txt");
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.txt");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(Files.size(filePath)));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(Files.size(filePath))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ByteArrayResource("File upload failed.".getBytes()));
        }
    }



}