package com.team3.employeeservice.AOP;

import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.response.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<ResponseStatus> handleNotFoundException(Exception e){
        return new ResponseEntity<>(
                ResponseStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
