package com.ascending.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("dev123")
public class AWSS3Config {
    @Value("${aws.region}")
    private String awsRegion;


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3(){
        return AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(awsRegion).build();
    }

    //supply credential method
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public AmazonS3 getEnvironmentAmazonS3(){
//        return AmazonS3ClientBuilder.standard().withCredentials(new EnvironmentVariableCredentialsProvider()).withRegion(awsRegion).build();
//    }
    //environment variable credential method
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public AmazonS3 getSupplyCredentialAmazonS3(){
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIA3KDQ4VLMFR7VKTJE","gEqDcW7M9aKw4JvEuqZmsUdulha498BcSW5hu1a6");
//        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider()).withRegion(awsRegion).build();
//    }

    @Bean
    @Scope (value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS getAmazonSQS(){
        return AmazonSQSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(awsRegion).build();
    }


}
