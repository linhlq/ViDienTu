package com.linhlee.vidientu.models;

/**
 * Created by lequy on 10/18/2017.
 */

public class FullNameRequest {
    private String data;
    private String msg;
    private int errorCode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
