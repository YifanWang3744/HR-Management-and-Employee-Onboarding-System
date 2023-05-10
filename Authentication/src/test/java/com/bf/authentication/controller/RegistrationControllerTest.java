package com.bf.authentication.controller;

//import com.bf.authentication.config.SecurityConfig;
import com.bf.authentication.config.SecurityConfig;
import com.bf.authentication.domain.common.ResponseStatus;
import com.bf.authentication.domain.request.RemoteEmployeeRequest;
import com.bf.authentication.domain.request.RemoteTokenRequest;
import com.bf.authentication.domain.request.UserRequest;
import com.bf.authentication.entity.User;
import com.bf.authentication.exception.NoAuthorizationException;
import com.bf.authentication.exception.UsernameOrEmailExistException;
import com.bf.authentication.security.JwtProvider;
import com.bf.authentication.service.RegistrationService;
import com.bf.authentication.service.RemoteEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = RegistrationController.class)
public class RegistrationControllerTest {
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private RemoteEmployeeService employeeService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtProvider jwtProvider;
    String realtoken = "bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJociIsInBlcm1pc3Npb25zIjpbeyJhdXRob3JpdHkiOiJIUiJ9XX0.cOCTF9zVVr6BgvZqhFkCFBk1ly246YYYHrMqNYi0ywM";

    @Test
    void test_register() throws Exception {
        User user = User.builder().id(1L).email("test").build();
        UserRequest r = new UserRequest();
        r.setEmail("test");
        String token = "t";
        Gson gson = new Gson();
        Mockito.when(registrationService.register(any(UserRequest.class), any(String.class))).thenReturn(user);
        ResponseStatus expected = ResponseStatus.builder().success(true).message("successfully registered").build();
        MvcResult result = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(r))
                        .param("token", token)
                        .header("Authorization", realtoken))
                .andReturn();
        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
        assertEquals(expected.toString(), actual.toString());

    }

    @Test
    void test_invite() throws Exception {
        Mockito.when(registrationService.generateToken(any(String.class), any(String.class))).thenReturn(realtoken);
        RemoteTokenRequest input = RemoteTokenRequest.builder().authorization(realtoken).email("x").build();
        ResponseStatus expected = ResponseStatus.builder()
                .success(true)
                .message("Registration token generated and email sent")
                .build();
        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(post("/register/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(input))
                        .header("Authorization", realtoken))
                .andReturn();
        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
        assertEquals(expected.toString(), actual.toString());
    }
}