package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLogInParameter {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     */
    public ModelLogInParameter() {
    }

    /**
     * @param username
     * @param username
     */

    public ModelLogInParameter(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}