package com.vik3.ui.models;

public class Author {
    private String user_login;
    private String user_url;
    private String user_nicename;
    private String user_registered;
    private String id;
    private String display_name;

    public Author() {
    }

    public Author(String user_login, String user_url, String user_nicename, String user_registered, String id, String display_name) {
        this.user_login = user_login;
        this.user_url = user_url;
        this.user_nicename = user_nicename;
        this.user_registered = user_registered;
        this.id = id;
        this.display_name = display_name;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getUser_registered() {
        return user_registered;
    }

    public void setUser_registered(String user_registered) {
        this.user_registered = user_registered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
