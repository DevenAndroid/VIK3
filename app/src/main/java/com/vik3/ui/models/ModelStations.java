package com.vik3.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelStations implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("collaborators")
    @Expose
    private List<Object> collaborators = null;
    @SerializedName("relays")
    @Expose
    private List<Object> relays = null;
    @SerializedName("current_track")
    @Expose
    private CurrentTrack currentTrack;
    @SerializedName("history")
    @Expose
    private List<History> history = null;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("streaming_hostname")
    @Expose
    private String streamingHostname;
    @SerializedName("outputs")
    @Expose
    private List<Output> outputs = null;
    private final static long serialVersionUID = 7224153445960678708L;

    /**
     * No args constructor for use in serialization
     */
    public ModelStations() {
    }

    /**
     * @param outputs
     * @param currentTrack
     * @param streamingHostname
     * @param relays
     * @param collaborators
     * @param source
     * @param history
     * @param logoUrl
     * @param status
     */
    public ModelStations(String status, Source source, List<Object> collaborators, List<Object> relays, CurrentTrack currentTrack, List<History> history, String logoUrl, String streamingHostname, List<Output> outputs) {
        super();
        this.status = status;
        this.source = source;
        this.collaborators = collaborators;
        this.relays = relays;
        this.currentTrack = currentTrack;
        this.history = history;
        this.logoUrl = logoUrl;
        this.streamingHostname = streamingHostname;
        this.outputs = outputs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<Object> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Object> collaborators) {
        this.collaborators = collaborators;
    }

    public List<Object> getRelays() {
        return relays;
    }

    public void setRelays(List<Object> relays) {
        this.relays = relays;
    }

    public CurrentTrack getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(CurrentTrack currentTrack) {
        this.currentTrack = currentTrack;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStreamingHostname() {
        return streamingHostname;
    }

    public void setStreamingHostname(String streamingHostname) {
        this.streamingHostname = streamingHostname;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

}