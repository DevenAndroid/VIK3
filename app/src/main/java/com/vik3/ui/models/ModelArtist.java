package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelArtist implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Bio")
    @Expose
    private String bio;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Social_links")
    @Expose
    private SocialLinks socialLinks;
    private final static long serialVersionUID = 2963957683940778114L;

    /**
     * No args constructor for use in serialization
     */
    public ModelArtist() {
    }

    /**
     * @param image
     * @param socialLinks
     * @param bio
     * @param id
     * @param title
     */
    public ModelArtist(Integer id, String title, String bio, String image, SocialLinks socialLinks) {
        super();
        this.id = id;
        this.title = title;
        this.bio = bio;
        this.image = image;
        this.socialLinks = socialLinks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SocialLinks getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(SocialLinks socialLinks) {
        this.socialLinks = socialLinks;
    }

}
