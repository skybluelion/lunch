package com.lunch.exception;

public class WrongDate extends Exception {
    public WrongDate() {
        this("잘못된 날짜입니다!");
    }
    public WrongDate(String message) {
        super(message);
    }
}
