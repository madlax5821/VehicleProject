package com.ascending.service;

import com.amazonaws.services.sqs.model.Message;
import com.ascending.init.AppInitializer;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSMessageServiceTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSMessageService awsMessageService;

    private String queueName;
    private String getQueueName1;

    @Before
    public void setup(){}

    @Test
    public void getQueueUrlTest(){
        queueName = "testQueue";
        String queueUrl = awsMessageService.getQueueUrl(queueName);
        assertNotNull(queueUrl);
        logger.info("==============,queueUrl={}",queueUrl);
    }

    @Test
    public void getMultipleMessagesTest() {
        queueName = "testQueue";
        int maximumNumberOfMessages = 7;
        int waitTimeInSeconds = 12;
        List<Message> messages = awsMessageService.getMessages(queueName);
        assertEquals(2,messages.size());
    }


}
