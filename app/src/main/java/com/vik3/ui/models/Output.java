package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Output implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("bitrate")
    @Expose
    private Integer bitrate;
    private final static long serialVersionUID = 6116174699788004508L;

    /**
     * No args constructor for use in serialization
     */
    public Output() {
    }

    /**
     * @param name
     * @param format
     * @param bitrate
     */
    public Output(String name, String format, Integer bitrate) {
        super();
        this.name = name;
        this.format = format;
        this.bitrate = bitrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

}