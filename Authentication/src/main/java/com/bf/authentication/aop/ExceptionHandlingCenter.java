package com.bf.authentication.aop;

import com.bf.authentication.domain.response.LoginResponse;
import com.bf.authentication.domain.common.ResponseStatus;
import com.bf.authentication.exception.NoAuthorizationException;
import com.bf.authentication.exception.UsernameOrEmailExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingCenter {
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<LoginResponse> handleAuthenticationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(LoginResponse.builder()
                .status(ResponseStatus.builder().success(false).message("Provided credential is invalid").build())
                .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UsernameOrEmailExistException.class)
    public ResponseEntity<ResponseStatus> handleRegistrationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message("Provided credential invalid").build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NoAuthorizationException.class)
    public ResponseEntity<ResponseStatus> handleAuthorizationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message("Performing an unauthorized action").build(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ResponseStatus> handleConversionException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message("Internal error").build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ResponseStatus> handleExpirationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message("Authorization expired").build(),
                HttpStatus.NOT_ACCEPTABLE
        );
    }
}
