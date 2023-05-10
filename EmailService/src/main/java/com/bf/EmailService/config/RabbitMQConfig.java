package com.bf.EmailService.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private ApplicationMessageListener applicationListener;

    @Autowired
    public void setRabbitListener(ApplicationMessageListener applicationListener) {
        this.applicationListener = applicationListener;
    }

    ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    Queue rqueue(){
        return QueueBuilder.durable("registrationQueue").build();
    }
    Queue aqueue(){
        return QueueBuilder.durable("applicationQueue").build();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setQueues(rqueue(), aqueue());
        messageListenerContainer.setMessageListener(applicationListener);
        return messageListenerContainer;
    }
}
