package com.bf.authentication.exception;

public class NoAuthorizationException extends Exception{
    public NoAuthorizationException() {
        super("Current user do not have the authorization for this action");
    }
}
