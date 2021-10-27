package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class History implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    private String url;
    private final static long serialVersionUID = 8745102633089022635L;

    /**
     * No args constructor for use in serialization
     */
    public History() {
    }

    /**
     * @param title
     */
    public History(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}