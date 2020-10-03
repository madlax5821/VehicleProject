package com.ascending.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.ascending.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSS3Service awsS3Service;

    private AmazonS3 s3Client;

    private String bucketName;

    @Before
    public void setup(){
        bucketName = "debug-14-xiaofeitest";
        try {
            awsS3Service.createBucket(bucketName);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
    @After
    public void tearDown(){
        awsS3Service.deleteBucket(bucketName);
    }

    @Test
    public void isBucketExist(){
        boolean isExist = awsS3Service.isBucketExist(bucketName);
        logger.info("bucket name is =={}",bucketName);
        Assert.assertTrue(isExist);
    }

    @Test
    public void getBucketsTest(){
        List<Bucket> buckets = awsS3Service.getBuckets();
        for (Bucket bucket : buckets){
            logger.info("this bucket is =={}",bucket.getName());
        }
        Assert.assertEquals("Buckets amount comparison",4,buckets.size());
    }

    @Test
    public void saveBucketTest(){
        String savedBucket = "xiaofei-savedbucket";
        Bucket bucket = awsS3Service.createBucket(savedBucket);
        Assert.assertNotNull(bucket);
    }

    @Test
    public void deleteBucketTest(){
        boolean ifDelete = awsS3Service.deleteBucket("xiaofei-savedbucket");
        logger.info("delete initiated...");
        Assert.assertTrue(ifDelete);
    }

    

}
