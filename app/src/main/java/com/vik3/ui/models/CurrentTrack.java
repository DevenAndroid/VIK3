package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentTrack implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("artwork_url")
    @Expose
    private String artworkUrl;
    @SerializedName("artwork_url_large")
    @Expose
    private String artworkUrlLarge;
    private final static long serialVersionUID = -6906844661976507627L;

    /**
     * No args constructor for use in serialization
     */
    public CurrentTrack() {
    }

    /**
     * @param artworkUrlLarge
     * @param artworkUrl
     * @param startTime
     * @param title
     */
    public CurrentTrack(String title, String startTime, String artworkUrl, String artworkUrlLarge) {
        super();
        this.title = title;
        this.startTime = startTime;
        this.artworkUrl = artworkUrl;
        this.artworkUrlLarge = artworkUrlLarge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public String getArtworkUrlLarge() {
        return artworkUrlLarge;
    }

    public void setArtworkUrlLarge(String artworkUrlLarge) {
        this.artworkUrlLarge = artworkUrlLarge;
    }

}
