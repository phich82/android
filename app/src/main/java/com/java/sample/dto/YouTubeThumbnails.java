package com.java.sample.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;


public class YouTubeThumbnails implements Serializable {

    @SerializedName(value = "default")
    private YouTubeThumbnail _default;
    @SerializedName(value = "medium")
    private YouTubeThumbnail medium;
    @SerializedName(value = "high")
    private YouTubeThumbnail high;
    @SerializedName(value = "standard")
    private YouTubeThumbnail standard;
    @SerializedName(value = "maxres")
    private YouTubeThumbnail maxres;


    public void setDefault(YouTubeThumbnail _default) {
        this._default = _default;
    }

    public YouTubeThumbnail getDefault() {
        return _default;
    }

    public YouTubeThumbnail getMedium() {
        return medium;
    }

    public void setMedium(YouTubeThumbnail medium) {
        this.medium = medium;
    }

    public YouTubeThumbnail getHigh() {
        return high;
    }

    public void setHigh(YouTubeThumbnail high) {
        this.high = high;
    }

    public YouTubeThumbnail getStandard() {
        return standard;
    }

    public void setStandard(YouTubeThumbnail standard) {
        this.standard = standard;
    }

    public YouTubeThumbnail getMaxres() {
        return maxres;
    }

    public void setMaxres(YouTubeThumbnail maxres) {
        this.maxres = maxres;
    }
}

