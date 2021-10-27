package com.vik3.ui.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRegisterParameter {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     */
    public ModelRegisterParameter() {
    }

    /**
     * @param firstName
     * @param lastName
     * @param password
     * @param role
     * @param email
     */
    public ModelRegisterParameter(String role, String firstName, String lastName, String email, String password) {
        super();
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}