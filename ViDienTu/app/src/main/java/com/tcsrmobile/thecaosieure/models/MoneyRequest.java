package com.tcsrmobile.thecaosieure.models;

/**
 * Created by lequy on 11/16/2017.
 */

public class MoneyRequest {
    private int data;
    private String msg;
    private int errorCode;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
