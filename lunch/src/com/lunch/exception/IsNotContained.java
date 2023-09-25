package com.lunch.exception;

public class IsNotContained extends Exception {
    public IsNotContained() {
        this("포함되어 있지 않습니다!!");
    }
    public IsNotContained(String message) {
        super(message);
    }
}
