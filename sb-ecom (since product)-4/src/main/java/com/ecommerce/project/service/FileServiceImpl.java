package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service

public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String imageFilePath, MultipartFile imageFile) throws IOException {
        //file name of current imagefile
        String fileName = imageFile.getOriginalFilename();
        if(fileName == null){
            throw new APIException("File name is empty");
        }
        //generate a unique name of the image, so there is no conflict for upload. concat it with extension of og file
        String randomId = UUID.randomUUID().toString();

        String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf('.')));
        String filePath = imageFilePath + File.separator + newFileName;//path separator is different for different os. so didnt use "/"

        //see if path doesnt exist and create
        System.out.println(filePath+" is the filepath");
        File folder = new File(imageFilePath);
        if (!folder.exists()) {
            System.out.println("Folder does not exist..making folder " + folder.getAbsolutePath());
            boolean staus = folder.mkdirs();
            System.out.println(staus ? "Folder created successfully..." : "Folder not created...fuck");
        }

        //upload IMAGE to server
        Files.copy(imageFile.getInputStream(), Paths.get(filePath));

        return newFileName;

    }
}
