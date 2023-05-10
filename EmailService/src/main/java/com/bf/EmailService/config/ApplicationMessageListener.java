package com.bf.EmailService.config;

import com.bf.EmailService.domain.MQMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMessageListener implements MessageListener {
    private ObjectMapper objectMapper;
    private JavaMailSender emailSender;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void onMessage(Message message) {
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        System.out.println(consumerQueue+message);

        switch (consumerQueue){
            case "registrationQueue": registrationHandler(message);
            break;
            case "applicationQueue": applicationStatusHandler(message);
            break;
        }
    }

    public void registrationHandler(Message message){
        try {
            MQMessage msg = objectMapper
                    .readValue(new String(message.getBody()), MQMessage.class);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("rabbitdemo123@outlook.com");
            mailMessage.setTo(msg.getEmail());
            mailMessage.setSubject("Registration Invitation");
            mailMessage.setText("Greetings!"+"\nPlease click the following link to register\n"+msg.getInvitationLink());
            System.out.println(msg);
//            emailSender.send(mailMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void applicationStatusHandler(Message message){
        try {
            MQMessage msg = objectMapper
                    .readValue(new String(message.getBody()), MQMessage.class);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("rabbitdemo123@outlook.com");
            mailMessage.setTo(msg.getEmail());
            mailMessage.setSubject("Application Status Update");
            mailMessage.setText("Greetings!"+"\nYour application gets rejected\nPlease log in for more info\n");
            System.out.println(msg);
//            emailSender.send(mailMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
