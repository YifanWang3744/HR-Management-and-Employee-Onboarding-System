package com.bf.HousingManagementService.aop;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.exception.AccessDeniedException;
import com.bf.HousingManagementService.exception.NoAvailableHouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ExceptionHandlingCenter {

    @ExceptionHandler(NoAvailableHouseException.class)
    public ResponseEntity<ResponseStatus> handleExpirationException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(
                ResponseStatus.builder().success(false).message("No available houses to assign to current employee").build(),
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
