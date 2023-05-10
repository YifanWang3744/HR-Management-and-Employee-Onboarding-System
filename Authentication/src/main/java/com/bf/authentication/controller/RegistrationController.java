package com.bf.authentication.controller;

import com.bf.authentication.domain.common.ResponseStatus;
import com.bf.authentication.domain.request.RemoteEmployeeRequest;
import com.bf.authentication.domain.request.RemoteTokenRequest;
import com.bf.authentication.domain.request.UserRequest;
import com.bf.authentication.entity.MQMessage;
import com.bf.authentication.entity.User;
import com.bf.authentication.exception.NoAuthorizationException;
import com.bf.authentication.exception.UsernameOrEmailExistException;
import com.bf.authentication.service.RegistrationService;
import com.bf.authentication.service.RemoteEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/register")
public class RegistrationController {
    private RegistrationService registrationService;
    private RemoteEmployeeService employeeService;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @Value("${security.jwt.JWT_TOKEN}")
    private String JWT_TOKEN;

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Autowired
    public void setEmployeeService(RemoteEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseStatus register(@RequestBody UserRequest request, @RequestParam String token) throws UsernameOrEmailExistException, NoAuthorizationException {
        User user = registrationService.register(request, token);
        RemoteEmployeeRequest rer = RemoteEmployeeRequest.builder().userId(user.getId()).email(user.getEmail()).build();
        employeeService.createEmployee(rer, JWT_TOKEN);
        return ResponseStatus.builder().success(true).message("successfully registered").build();
    }

    @PostMapping("/invite")
    public ResponseStatus invite(@RequestBody RemoteTokenRequest request) {
        String authorization = request.getAuthorization();
        if(authorization == null || authorization.length() < 8) return ResponseStatus.builder().success(false).message("authorization failed").build();

        String token;
        try{
            token = registrationService.generateToken(request.getEmail(), authorization);
        } catch (NoAuthorizationException e){
            return ResponseStatus.builder().success(false).message("authorization failed").build();
        }
        MQMessage message = MQMessage.builder().invitationLink("http://localhost:9000/authentication/register?token="+token).email(request.getEmail()).build();
        try{
            rabbitTemplate.convertAndSend("email.direct", "proj3.registration.email", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e){
            return ResponseStatus.builder().success(false).message("conversion failed").build();
        }
        return ResponseStatus.builder().success(true).message("Registration token generated and email sent").build();
    }
}
