package com.example.demo_uloadfile_java.service.imp;

import com.example.demo_uloadfile_java.entity.bo.Response;
import com.example.demo_uloadfile_java.entity.bo.SystemResponse;
import com.example.demo_uloadfile_java.service.ImgService;
import com.example.demo_uloadfile_java.utils.StringResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ImgServiceImp implements ImgService {
    private final Path storageFolder = Paths.get("uploads");

    private boolean isImageFile(MultipartFile file) {
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png","jpg","jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public ResponseEntity<SystemResponse<Object>> SaveImg(MultipartFile[] files) {
        List<String> url = new ArrayList<>();
        for(int i = 0; i < files.length;i++)
        {
            if (files[i].isEmpty()) {
                return Response.badRequest(StringResponse.Error_Img_empty);
            }
            if(!isImageFile(files[i]))
            {
                return Response.badRequest(StringResponse.Error_Img_file);
            }
            String fileExtension = FilenameUtils.getExtension(files[i].getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = files[i].getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            url.add("http://localhost:8080/api/upfile/files/"+generatedFileName);

        }

        return Response.ok(url);

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            //list all files in storageFolder
            //How to fix this ?
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load stored files", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(((UrlResource) resource).getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }
}

