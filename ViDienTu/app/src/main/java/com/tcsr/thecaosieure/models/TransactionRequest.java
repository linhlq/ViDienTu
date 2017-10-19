package com.tcsr.thecaosieure.models;

import java.util.ArrayList;

/**
 * Created by lequy on 10/18/2017.
 */

public class TransactionRequest {
    private ArrayList<TransactionObject> data;
    private String msg;
    private int errorCode;

    public ArrayList<TransactionObject> getData() {
        return data;
    }

    public void setData(ArrayList<TransactionObject> data) {
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
