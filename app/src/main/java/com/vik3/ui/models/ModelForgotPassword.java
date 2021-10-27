package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelForgotPassword {

    @SerializedName("user_login")
    @Expose
    private String userLogin;

    /**
     * No args constructor for use in serialization
     */
    public ModelForgotPassword() {
    }

    /**
     * @param userLogin
     */
    public ModelForgotPassword(String userLogin) {
        super();
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

}