package com.bf.authentication.domain.request;

import lombok.Data;

@Data
public class LoginByEmailRequest {
    private String email;
    private String password;
}
