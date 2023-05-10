package com.bf.housing.aop;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.exception.AccessDeniedException;
import com.bf.housing.exception.FacilityReportNotFoundException;
import com.bf.housing.exception.NoAvailableHouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingCenter {

    @ExceptionHandler(value = {NoAvailableHouseException.class, FacilityReportNotFoundException.class})
    public ResponseEntity<ResponseStatus> handleExpirationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message(e.getMessage()).build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ResponseStatus> handleAccessDeniedException(Exception e){
        return new ResponseEntity<>(
                ResponseStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
