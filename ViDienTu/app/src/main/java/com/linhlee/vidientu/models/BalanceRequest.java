package com.linhlee.vidientu.models;

/**
 * Created by lequy on 6/20/2017.
 */

public class BalanceRequest {
    private BalanceObject data;
    private String msg;
    private int errorCode;

    public BalanceObject getData() {
        return data;
    }

    public void setData(BalanceObject data) {
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
