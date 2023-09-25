package com.lunch.exception;

public class Duplicated extends Exception{
    public Duplicated() {
        this("중복되었습니다!");
    }
    public Duplicated(String message) {
        super(message);
    }

}
