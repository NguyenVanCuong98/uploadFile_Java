package com.example.demo_uloadfile_java.service;

import com.example.demo_uloadfile_java.entity.bo.SystemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
@Service
public interface ImgService {
    ResponseEntity<SystemResponse<Object>> SaveImg(MultipartFile[] files);
    Stream<Path> loadAll();
    byte[] readFileContent(String fileName);
}
