package com.ascending.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.ascending.init.AppInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSMessageServiceMockTest {
    @Spy
    private Logger logger;

    @InjectMocks
    AWSMessageService awsMessageService;

    @Mock (answer = Answers.RETURNS_DEEP_STUBS)
    AmazonSQS mockAmazonSQS;

    private List<Message> messages;
    private String queueName = "test_queue.com";
    private String fakeQueueUrl = "www.testQueueUrl.com";
    private String msg = "test message";

    @Before
    public void init(){
        logger.info("");
        messages = new ArrayList<>();
        messages.add(new Message());
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetQueueUrl(){
        GetQueueUrlResult getQueueUrlResult = mock(GetQueueUrlResult.class);
        Mockito.when(mockAmazonSQS.getQueueUrl(anyString())).thenReturn(getQueueUrlResult);
        when(getQueueUrlResult.getQueueUrl()).thenReturn(fakeQueueUrl);
        //doReturn(anyString()).when(mockAmazonSQS.getQueueUrl(anyString()).getQueueUrl());
        String queueUrl = awsMessageService.getQueueUrl(queueName);
        Assert.assertEquals("name comparision",queueUrl,fakeQueueUrl);
        verify(mockAmazonSQS,times(1)).getQueueUrl(anyString());
        //verify(logger,times(1)).info(anyString());
    }

    @Test
    public void createQueue_happyPath(){
        when(mockAmazonSQS.createQueue(any(CreateQueueRequest.class)).getQueueUrl()).thenReturn(fakeQueueUrl);
        when(mockAmazonSQS.getQueueUrl(anyString()).getQueueUrl()).thenThrow(new QueueDoesNotExistException("the queue does not exist."));
        String queueUrl = awsMessageService.createQueue(queueName);
        Assert.assertEquals(fakeQueueUrl,queueUrl);
        verify(mockAmazonSQS,times(1)).createQueue(any(CreateQueueRequest.class));
    }

    @Test
    public void createQueue_withExist_queueUrl(){
        GetQueueUrlResult getQueueUrlResult = mock(GetQueueUrlResult.class);
        when(mockAmazonSQS.getQueueUrl(anyString())).thenReturn(getQueueUrlResult);
        when(getQueueUrlResult.getQueueUrl()).thenReturn(fakeQueueUrl);

        String queueUrl = awsMessageService.createQueue(queueName);
        Assert.assertEquals(fakeQueueUrl,queueUrl);
        verify(mockAmazonSQS,never()).createQueue(any(CreateQueueRequest.class));
    }
}
