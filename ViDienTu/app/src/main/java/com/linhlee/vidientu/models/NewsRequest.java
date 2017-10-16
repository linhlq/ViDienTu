package com.linhlee.vidientu.models;

import java.util.ArrayList;

/**
 * Created by lequy on 10/16/2017.
 */

public class NewsRequest {
    private ArrayList<NewsObject> data;
    private String msg;
    private int errorCode;

    public ArrayList<NewsObject> getData() {
        return data;
    }

    public void setData(ArrayList<NewsObject> data) {
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
