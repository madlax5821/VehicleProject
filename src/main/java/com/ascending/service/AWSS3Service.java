package com.ascending.service;

import com.amazonaws.HttpMethod;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.clouddirectory.model.ListObjectParentsResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import com.ascending.dao.ImageDao;
import com.ascending.model.Image;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AWSS3Service {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String awsRegion;

    public AWSS3Service(){

//        amazonS3 = getS3ClientUsingDefaultChain();
//        amazonS3 = getS3ClientWithEnvironmentCredentials();
//        amazonS3 = getS3ClientWithSuppliedCredentials();

    }

//    private AmazonS3 getS3ClientUsingDefaultChain(){
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
//
//        return s3Client;
//    }

    public boolean isBucketExist(String bucketName){
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        return isExist;
    }

    public Bucket createBucket(String bucketName){
        Bucket bucket = null;
        if (amazonS3.doesBucketExistV2(bucketName)){
            logger.error("Bucket %s already exists.\n",bucketName);

        }else {
            try{
                bucket = amazonS3.createBucket(bucketName);
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }
        return bucket;
    }

    public boolean deleteBucket(String bucketName) {
        int ifDelete = 0;
        try {
            amazonS3.deleteBucket(bucketName);
            ifDelete = 1;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ifDelete>0;
    }

    public List<Bucket> getBuckets(){
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket:buckets){
            logger.info("Your Amazon S3 buckets are=={}",bucket.getName());
        }
        return buckets;
    }

    public  List<String> getObjectKeys(String bucketName){
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> summaries = result.getObjectSummaries();
        List<String> list = new ArrayList<>();
        for (S3ObjectSummary summary:summaries){
            list.add(summary.getKey());
        }
        return list;
    }

    public String uploadMultipartFile(String bucketName, MultipartFile multipartFile) throws IOException {
        //create a random object(file) name;
        String uuid = UUID.randomUUID().toString();
        String oriFileName = multipartFile.getOriginalFilename();
        int dotIndex = oriFileName.indexOf(".");
        String newRandomFileName = oriFileName.substring(0,dotIndex)+"_"+uuid+oriFileName.substring(dotIndex);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        amazonS3.putObject(bucketName,newRandomFileName,multipartFile.getInputStream(),objectMetadata);
        return newRandomFileName;
    }


    public String uploadFile(MultipartFile file) throws IOException {
        return uploadMultipartFile(bucketName,file);
    }

    public void normalUploadFile(String bucketName, String fileName, String filePath){
        amazonS3.putObject(bucketName,fileName,new File(filePath));
    }

    public File downloadObject(String bucketName, String objectName, String destinationFullPath) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        File destFile = new File(destinationFullPath);
        FileUtils.copyInputStreamToFile(inputStream, destFile);
        return destFile;
    }

    public String generatePresignedURL(String bucketName, String objectKey, String httpMethodString) {
        // Set the pre-signed URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL.
        logger.info("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.valueOf(httpMethodString))
                        .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public String generatePresignedURLForUploading(String bucketName, String objectKey) {
//        return generatePresignedURL(bucketName, objectKey, "PUT");
        return generatePresignedURL(bucketName, objectKey, "PUT");
    }

    public String generatePresignedURLForDownloading(String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public String generatePresignedURLForDownloading(String bucketName, String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public boolean isObjectExist(String bucketName, String objectKey){
        boolean ifObjectExist = amazonS3.doesObjectExist(bucketName,objectKey);
        return ifObjectExist;
    }
}
