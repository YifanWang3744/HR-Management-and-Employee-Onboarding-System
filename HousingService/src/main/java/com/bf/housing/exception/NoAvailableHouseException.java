package com.bf.housing.exception;

public class NoAvailableHouseException extends Exception{
    public NoAvailableHouseException() {
        super("No available house for assignment");
    }
}
