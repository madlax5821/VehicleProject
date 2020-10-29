package com.ascending.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ascending.init.AppInitializer;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.ArgumentMatchers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceMockTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Mock
//    private AmazonS3 mockAmazonS3 = mock(AmazonS3.class);
//
    @InjectMocks
    private AWSS3Service awss3Service;

    @Mock
    private AmazonS3 mockAmazonS3;

    @Captor
    private ArgumentCaptor<String> stringBucketNameCaptor;

    @Before
    public void setup(){
        //MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBucketDoExist() {
//        when(mockAmazonS3.doesBucketExistV2(anyString())).thenReturn(Boolean.TRUE);
//        boolean isExist = awss3Service.isBucketExist(anyString());
//        verify(mockAmazonS3, times(1)).doesBucketExistV2(anyString());
        Mockito.doReturn(Boolean.TRUE).when(mockAmazonS3).doesBucketExistV2(anyString());
        boolean isExist = awss3Service.isBucketExist(anyString());
        verify(mockAmazonS3).doesBucketExistV2(anyString());
        Assert.assertTrue(isExist);
    }

    @Test
    public void testCreateBucket(){
        String bucketName = "testBucket";
        doReturn(new Bucket(bucketName)).when(mockAmazonS3).createBucket(anyString());
        Bucket bucket = mockAmazonS3.createBucket(bucketName);
        Assert.assertEquals("bucket name comparison",bucketName,bucket.getName());
        verify(mockAmazonS3).createBucket(anyString());
    }

    @Test
    public void testDeleteBucket(){
        String mockName = "testBucket";
        //Mockito.doReturn(mockName).when(mockBucket).getName();
        Mockito.doNothing().when(mockAmazonS3).deleteBucket(mockName);
        boolean ifDelete = awss3Service.deleteBucket(mockName);
        Assert.assertTrue(ifDelete);
        verify(mockAmazonS3).deleteBucket(mockName);
        logger.info("mock delete implemented successfully...");
    }

    @Test
    public void testUploadMultipartFile() throws IOException {
        //generate a multipartFile object as well as a new String which represents a bucket name.
        String bucketName = "testBucket";
        String fileName = "C:\\Users\\MADLAX\\Pictures\\Camera Roll\\EVE.jpg";
        File file = new File(fileName);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("multipartFile",file.getName(),"jpg",IOUtils.toByteArray(input));
        //generate the related mock objects which are used in mockAmazonS3`s put method.
        PutObjectResult putObjectResult = mock(PutObjectResult.class);
        ObjectMetadata objectMetadata = mock(ObjectMetadata.class);
        InputStream inputStream = mock(InputStream.class);
        //begin to implement the mock process
        doReturn(putObjectResult).when(mockAmazonS3).putObject(anyString(),anyString(),any(InputStream.class),any(ObjectMetadata.class));
        String objectKey = awss3Service.uploadMultipartFile(bucketName,multipartFile);
        verify(mockAmazonS3).putObject(anyString(),anyString(),any(InputStream.class),any(ObjectMetadata.class));

        doReturn(Boolean.TRUE).when(mockAmazonS3).doesObjectExist(anyString(),anyString());
        boolean ifUpdate = awss3Service.isObjectExist(bucketName, objectKey);
        verify(mockAmazonS3).doesObjectExist(anyString(),anyString());

        Assert.assertTrue(ifUpdate);

        //mockAmazonS3.
    }

    @Test
    public void testCreateBucketWithArgumentCaptor(){
        String bucketName = "testBucket";
        Mockito.doReturn(new Bucket(bucketName)).when(mockAmazonS3).createBucket(anyString());
        //when(mockAmazonS3.createBucket(anyString())).thenReturn(mockBucket);
        Bucket bucket = awss3Service.createBucket(bucketName);
        Mockito.verify(mockAmazonS3).createBucket(stringBucketNameCaptor.capture());
        Assert.assertEquals("bucket name comparison",bucketName,stringBucketNameCaptor.getValue());

    }
}
