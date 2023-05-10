package com.bf.authentication.domain.request;

import lombok.Data;

@Data
public class LoginByUsernameRequest {
    private String username;
    private String password;
}
