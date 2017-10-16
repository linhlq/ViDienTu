package com.linhlee.vidientu.models;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class NewsObject {
    private String imageurl;
    private String title;
    private String abstract_;

    public NewsObject(String imageurl, String title, String abstract_) {
        this.imageurl = imageurl;
        this.title = title;
        this.abstract_ = abstract_;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract_() {
        return abstract_;
    }

    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }
}
