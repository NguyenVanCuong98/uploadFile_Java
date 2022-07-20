package com.example.demo_uloadfile_java.controller;


import com.example.demo_uloadfile_java.entity.bo.Response;
import com.example.demo_uloadfile_java.entity.bo.SystemResponse;
import com.example.demo_uloadfile_java.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController

public class UploadFileController {
    @Autowired
    private ImgService imgService;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<SystemResponse<Object>> upload(@RequestParam("file") MultipartFile[] files)
    {
        return imgService.SaveImg(files);
    }
    @GetMapping("/files/{fileName:.+}")
    // /files/06a290064eb94a02a58bfeef36002483.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = imgService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<SystemResponse<Object>> getUploadedFiles() {
        try {
            List<String> urls = imgService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request "readDetailFile")
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(UploadFileController.class,
                                "readDetailFile", path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return Response.ok("List files successfully",urls);
        }catch (Exception exception) {
            return Response.badRequest("List files failed"+exception);
        }
    }

}

