package com.ascending.config;


import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmazonSQS amazonSQS;

    @Bean(name = "awsQueueConnectionFactory")
    public SQSConnectionFactory getSQSConnectionFactory(){
        return new SQSConnectionFactory(new ProviderConfiguration(),amazonSQS);
    }

    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        //create jms=ListenerFactory
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        //use AWS queue connection factory to connect
        factory.setConnectionFactory(getSQSConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        //factory.setErrorHandler(defaultErrorHandler());
        return factory;
    }
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

//    @Bean
//    public ErrorHandler defaultErrorHandler() {
//        return new ErrorHandler() {
//            @Override
//            public void handleError(Throwable throwable) {
//                // print error...
//                // send email and SMS...
//                System.err.println(throwable.getMessage());
//            }
//        };
//    }

    /*
This method has nothing to do with asyn messaging, just want to show you how to
create a template object
 */
    @Bean
    public JmsTemplate jmsTemplate() {

        return new JmsTemplate(getSQSConnectionFactory());
    }

}
