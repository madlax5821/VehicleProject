package com.ascending.controller;

import com.ascending.dao.UserDao;
import com.ascending.model.Image;
import com.ascending.model.User;
import com.ascending.service.AWSS3Service;
import com.ascending.service.ImageService;
import com.ascending.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = {"/AWSS3"})
public class AWSS3Controller {
    private static final String queueName = "training_queue_ascending_com";
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageService imageService;

    @Autowired
    private AWSS3Service awsS3Service;

    @Autowired
    UserService userService;

//    @Autowired
//    private AWSMessageService messageService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //you can return either s3 key or file url
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("id")long id) {
        logger.info("test file name:"+file.getOriginalFilename());
        try {
            String key = awsS3Service.uploadFile(file);
            User user = userService.getUserById(id);
            Image image = new Image();
            image.setImageKey(key);
            imageService.saveImage(image,user);
            return key;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
//    public ResponseEntity<String> getFileUrlForDownloading(@PathVariable String fileName, HttpServletRequest request) {
    @GetMapping(params={"fileName","bucketName"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFileUrlForDownloading(@RequestParam("bucketName") String buckName,
                                                           @RequestParam("fileName") String fileName) {
//        request.getSession()
//        Resource resource = null;
        String msg = "The file doesn't exist.";
        ResponseEntity responseEntity;

        try {
            String url = awsS3Service.generatePresignedURLForDownloading(buckName,fileName);
            logger.debug(msg);
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(url);
        }
        catch (Exception ex) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
            logger.debug(ex.getMessage());
        }

        return responseEntity;
    }
    @GetMapping(params = "bucketName",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean downloadFile(@RequestParam("bucketName")String bucketName){
        List<Image> list = imageService.getAllImages();
        int ifDownload = 0;
        try {
            for (Image image:list)
                awsS3Service.downloadObject(bucketName,image.getImageKey(),"E:\\"+image.getImageKey());
                ifDownload = 1;
            } catch (IOException e) {
                logger.error("failed to download files, error={}",e.getMessage());
            }
        return ifDownload>0;
    }

}
