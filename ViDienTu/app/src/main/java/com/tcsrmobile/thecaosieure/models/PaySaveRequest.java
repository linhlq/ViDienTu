package com.tcsrmobile.thecaosieure.models;

import java.util.ArrayList;

/**
 * Created by lequy on 10/31/2017.
 */

public class PaySaveRequest {
    private ArrayList<PaySaveObject> data;
    private String msg;
    private int errorCode;

    public ArrayList<PaySaveObject> getData() {
        return data;
    }

    public void setData(ArrayList<PaySaveObject> data) {
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