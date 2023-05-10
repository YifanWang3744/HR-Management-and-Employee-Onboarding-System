package com.bf.authentication.controller;

import com.bf.authentication.entity.User;
import com.bf.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserService service;

    @GetMapping("/test")
    public Object test(){
        User user = service.test(1L);
        return user;
    }
}
