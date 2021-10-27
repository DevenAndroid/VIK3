package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -5528412031906022989L;

    /**
     * No args constructor for use in serialization
     */
    public ModelResponse() {
    }
 
    /**
     * @param message
     * @param status
     */
    public ModelResponse(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}