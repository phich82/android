package com.java.sample.dto;


import java.io.Serializable;

public class YouTubeResourceId implements Serializable {

    private String kind = "";
    private String videoId = "";

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
