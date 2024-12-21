package com.java.sample.dto;

import java.io.Serializable;


public class YouTubeSnippet implements Serializable {

    private String publishedAt = ""; // 2024-12-03T15:32:15Z
    private String channelId = "";
    private String title = "";
    private String description = "";
    private YouTubeThumbnails thumbnails;
    private String channelTitle = "";
    private String playlistId = "";
    private Integer position;
    private YouTubeResourceId resourceId;
    private String videoOwnerChannelTitle;
    private String videoOwnerChannelId;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public YouTubeThumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(YouTubeThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public YouTubeResourceId getResourceId() {
        return resourceId;
    }

    public void setResourceId(YouTubeResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public String getVideoOwnerChannelTitle() {
        return videoOwnerChannelTitle;
    }

    public void setVideoOwnerChannelTitle(String videoOwnerChannelTitle) {
        this.videoOwnerChannelTitle = videoOwnerChannelTitle;
    }

    public String getVideoOwnerChannelId() {
        return videoOwnerChannelId;
    }

    public void setVideoOwnerChannelId(String videoOwnerChannelId) {
        this.videoOwnerChannelId = videoOwnerChannelId;
    }
}
