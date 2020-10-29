package com.ascending.service;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AWSMessageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Autowired
    private AmazonSQS amazonSQS;

    public String getQueueUrl(String queueName) {
        GetQueueUrlResult getQueueUrlResult = amazonSQS.getQueueUrl(queueName);
        logger.info("QueueUrl=={}", getQueueUrlResult.getQueueUrl());
        return getQueueUrlResult.getQueueUrl();
    }

    public String createQueue(String queueName) {
        String queueUrl = null;
        try {
            queueUrl = getQueueUrl(queueName);
        } catch (QueueDoesNotExistException e) {
            CreateQueueRequest createQueueRequest = new CreateQueueRequest();
            queueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        }
        logger.info(queueUrl);
        return queueUrl;
    }

    public void sendMessage(String queueUrl, String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(message).withDelaySeconds(5);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    public List<Message> getMessages(String queueUrl) {
        List<Message> messages = amazonSQS.receiveMessage(queueUrl).getMessages();
        return messages;
    }

    @JmsListener(destination = "${aws.sqs.name}")
    //@JmsListener(destination = "testQueue")
    public void receiveMessage(String message) {
        logger.info("jms listener message = {}", message);
    }

}
