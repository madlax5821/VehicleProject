package com.ascending.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ascending.init.AppInitializer;
import com.ascending.model.Image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSS3Service awsS3Service;

    @Autowired
    private ImageService imageService;

    private AmazonS3 s3Client;

    private String testBucketName;

    @Before
    public void setup(){
        testBucketName = "debug-14-xiaofeitest";
        try {
            awsS3Service.createBucket(testBucketName);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
    @After
    public void tearDown(){
        awsS3Service.deleteBucket(testBucketName);
    }

    @Test
    public void isBucketExist(){
        boolean isExist = awsS3Service.isBucketExist(testBucketName);
        logger.info("bucket name is =={}", testBucketName);
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

    @Test
    public void uploadMultipartFileTest() throws IOException {
        String fileName = "C:\\Users\\MADLAX\\Pictures\\Camera Roll\\EVE.jpg";
        File file = new File(fileName);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("multipartFile",file.getName(),"jpg",IOUtils.toByteArray(input));
        String objectName = awsS3Service.uploadMultipartFile("debug-14-test1",multipartFile);

        List<String> list = awsS3Service.getObjectKeys("debug-14-test1");
        logger.info(objectName);
        Assert.assertTrue(list.contains(objectName));
    }
    @Test
    public void testPresignedURLForUploading()  {
        String bucketName = "debug-14-test4";
        String filename = "bucket-1/images/plain-text-uploaded.txt";
        String urlString = awsS3Service.generatePresignedURLForUploading(testBucketName, filename);
        logger.info("==== testBucketName ={}", testBucketName);
        logger.info("==== PUT presigneURL string ={}", urlString);
        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text uploaded as an object via presigned URL.");
            out.close();

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testPresignedURLForDownloading() {
//        String bucketName = "debug-14-test1";
//        String filename = "bucket-1/my-folder/my-sub-folder/my-text-file.txt";
//        String destFilename = "/home/michael/Documents/downloads/my-text-file-downloaded.txt";
        String filename = "bucket-1/images/star.jpg";
        String destFilename = "/home/michael/Documents/downloads/star-image-3.jpg";
        String urlString = awsS3Service.generatePresignedURLForDownloading(testBucketName, filename);
        logger.info("==== GET presigneURL string ={}", urlString);

        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            File destFile = new File(destFilename);
            FileUtils.touch(destFile);
            InputStream is = connection.getInputStream();

            FileUtils.copyInputStreamToFile(is, destFile);

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

            IOUtils.closeQuietly(is);
            IOUtils.close(connection);

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testDirectlyUsingPresignedURLForDownloading() {
//        String bucketName = "mgao-s3-bucket-1";
//        String filename = "presignedTest.txt";
        String destFilename = "/home/michael/Documents/downloads/star-image.jpg";
        //String urlString = "https://debug-14-1.s3.amazonaws.com/bucket-1/images/star.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201006T144854Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIAUH7NDCSMB4JXR7OC%2F20201006%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=82ca67421b6c070290819dbfcbed609dc026b79fc23c921c07bab16cae8f8614";
        String urlString = "https://debug-14-1.s3.amazonaws.com/bucket-1/images/green-square.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201007T204251Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIAUH7NDCSMB4JXR7OC%2F20201007%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=674badfe9d797881073682e28c856baf6431c41fabb849bc39344d695c03051e";
        logger.info("==== GET presigneURL string ={}", urlString);

        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            File destFile = new File(destFilename);
            FileUtils.touch(destFile);
            InputStream is = connection.getInputStream();

            FileUtils.copyInputStreamToFile(is, destFile);

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

            IOUtils.closeQuietly(is);
            IOUtils.close(connection);

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void downloadTest() throws IOException {
        List<Image> list = imageService.getAllImages();
        for (Image image:list){
            awsS3Service.downloadObject("debug-14-test3",image.getImageKey(),"E:\\"+image.getImageKey());
        }
    }
}
