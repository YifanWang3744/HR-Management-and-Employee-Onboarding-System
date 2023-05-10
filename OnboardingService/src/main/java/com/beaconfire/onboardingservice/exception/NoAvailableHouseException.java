package com.beaconfire.onboardingservice.exception;

public class NoAvailableHouseException extends Exception{
    public NoAvailableHouseException() {
        super("No available house for assignment");
    }
}
