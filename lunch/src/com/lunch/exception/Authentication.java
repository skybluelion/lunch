package com.lunch.exception;

public class Authentication extends Exception{
    public Authentication() {
        this("권한이 없습니다!");
    }

    public Authentication(String message) {
        super(message);
    }
}
