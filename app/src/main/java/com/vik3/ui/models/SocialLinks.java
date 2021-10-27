package com.vik3.ui.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLinks implements Serializable {

    @SerializedName("Facebook")
    @Expose
    private String facebook;
    @SerializedName("YouTube")
    @Expose
    private String youTube;
    @SerializedName("Twitter")
    @Expose
    private String twitter;
    @SerializedName("Instagram")
    @Expose
    private String instagram;
    private final static long serialVersionUID = -8640031801480419079L;

    /**
     * No args constructor for use in serialization
     */
    public SocialLinks() {
    }

    /**
     * @param twitter
     * @param facebook
     * @param youTube
     * @param instagram
     */
    public SocialLinks(String facebook, String youTube, String twitter, String instagram) {
        super();
        this.facebook = facebook;
        this.youTube = youTube;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYouTube() {
        return youTube;
    }

    public void setYouTube(String youTube) {
        this.youTube = youTube;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

}