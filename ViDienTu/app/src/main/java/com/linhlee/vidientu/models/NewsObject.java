package com.linhlee.vidientu.models;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class NewsObject {
    private int imgRes;
    private String title;
    private String content;

    public NewsObject(int imgRes, String title, String content) {
        this.imgRes = imgRes;
        this.title = title;
        this.content = content;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
