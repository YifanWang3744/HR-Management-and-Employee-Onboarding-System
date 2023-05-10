package com.bf.HousingManagementService.exception;

public class NoAvailableHouseException extends Exception{
    public NoAvailableHouseException() {
        super("No available house for assignment");
    }
}
