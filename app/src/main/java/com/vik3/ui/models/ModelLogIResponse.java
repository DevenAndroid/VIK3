package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLogIResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data ")
    @Expose
    private ModelLogInData data;

    /**
     * No args constructor for use in serialization
     */
    public ModelLogIResponse() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public ModelLogIResponse(String status, String message, ModelLogInData data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelLogInData getData() {
        return data;
    }

    public void setData(ModelLogInData data) {
        this.data = data;
    }

}