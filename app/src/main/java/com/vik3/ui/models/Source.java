package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("collaborator")
    @Expose
    private Object collaborator;
    @SerializedName("relay")
    @Expose
    private Object relay;
    private final static long serialVersionUID = -935857278853781902L;

    /**
     * No args constructor for use in serialization
     */
    public Source() {
    }

    /**
     * @param relay
     * @param type
     * @param collaborator
     */
    public Source(String type, Object collaborator, Object relay) {
        super();
        this.type = type;
        this.collaborator = collaborator;
        this.relay = relay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Object collaborator) {
        this.collaborator = collaborator;
    }

    public Object getRelay() {
        return relay;
    }

    public void setRelay(Object relay) {
        this.relay = relay;
    }

}