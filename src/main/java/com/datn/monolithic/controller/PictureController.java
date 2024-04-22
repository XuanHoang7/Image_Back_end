package com.datn.monolithic.controller;

import com.datn.monolithic.entity.Picture;
import com.datn.monolithic.service.PictureService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/picture")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/upload")
    public Object upload(@RequestParam("image") MultipartFile file, @RequestParam("username") String username) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("image", ".jpg");
        file.transferTo(tempFile);

        return pictureService.uploadImageToDrive(tempFile, username);

    }

    @PostMapping("/home")
    public List<Picture> home(@RequestParam("username") String username) {
        for (Picture data : pictureService.getPicturesByUsername(username)) {
            System.out.println(data.getPath());
        }
        return pictureService.getPicturesByUsername(username);
    }
}
