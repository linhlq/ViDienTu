package com.linhlee.vidientu.models;

import java.util.ArrayList;

/**
 * Created by lequy on 10/16/2017.
 */

public class CardRequest {
    private ArrayList<CardObject> data;
    private String msg;
    private int errorCode;

    public ArrayList<CardObject> getData() {
        return data;
    }

    public void setData(ArrayList<CardObject> data) {
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
