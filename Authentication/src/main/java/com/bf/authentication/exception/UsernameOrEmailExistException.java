package com.bf.authentication.exception;

public class UsernameOrEmailExistException extends Exception{
    public UsernameOrEmailExistException() {
        super("Username or email exists in the database");
    }
}
