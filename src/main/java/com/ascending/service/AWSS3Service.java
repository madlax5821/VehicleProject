package com.ascending.service;

import com.amazonaws.regions.Regions;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AWSS3Service {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String awsRegion;

    public AWSS3Service(){
        amazonS3 = getS3ClientUsingDefaultChain();
    }

    private AmazonS3 getS3ClientUsingDefaultChain(){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        return s3Client;
    }

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



}
