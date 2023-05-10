package com.bf.authentication.controller;

import com.bf.authentication.domain.request.LoginByEmailRequest;
import com.bf.authentication.domain.request.LoginByUsernameRequest;
import com.bf.authentication.domain.response.LoginResponse;
import com.bf.authentication.domain.common.ResponseStatus;
import com.bf.authentication.security.AuthUserDetail;
import com.bf.authentication.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;



    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/username")
    public LoginResponse login(@RequestBody LoginByUsernameRequest request) throws AuthenticationException{
       return authenticate(request.getUsername(), request.getPassword());
    }

    @PostMapping("/email")
    public LoginResponse login(@RequestBody LoginByEmailRequest request) throws AuthenticationException{
        return authenticate(request.getEmail(), request.getPassword());
    }

    private LoginResponse authenticate(String identifier, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(identifier, password));

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        String token = jwtProvider.createToken(authUserDetail);

        return LoginResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Welcome " + authUserDetail.getUsername()).build())
                .token(token)
                .build();
    }
}
