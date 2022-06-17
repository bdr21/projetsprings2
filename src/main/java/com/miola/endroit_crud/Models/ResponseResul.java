package com.miola.endroit_crud.Models;

public class ResponseResul {


    private int statusCode;
    private String message;

    public ResponseResul(){}
    public ResponseResul(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getstatusCode() {
        return statusCode;
    }

    public void setstatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
