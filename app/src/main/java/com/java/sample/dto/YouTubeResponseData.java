package com.java.sample.dto;

import com.google.gson.reflect.TypeToken;
import com.java.sample.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class YouTubeResponseData implements Serializable {

    private String kind = "";
    private String etag = "";
    private List<YouTubeItem> items = new ArrayList<>();
    private YouTubePageInfo pageInfo;

    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getKind() {
        return kind;
    }
    public void setEtag(String etag) {
        this.etag = etag;
    }
    public String getEtag() {
        return etag;
    }

    public void setItems(List<YouTubeItem> items) {
        this.items = items;
    }
    public List<YouTubeItem> getItems() {
        return items;
    }

    public void setPageInfo(YouTubePageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    public YouTubePageInfo getPageInfo() {
        return pageInfo;
    }


    public static YouTubeResponseData from(JSONObject responseApi) {
        YouTubeResponseData response = new YouTubeResponseData();
        List<YouTubeItem> rows = new ArrayList<>();
        try {
            JSONObject pageInfoJsonObject = responseApi.getJSONObject("pageInfo");
            YouTubePageInfo pageInfo = Util.fromJson(pageInfoJsonObject.toString(), YouTubePageInfo.class);

            JSONArray dataResponse = responseApi.getJSONArray("items");
            List<YouTubeItem> items = Util.fromJson(dataResponse.toString(), new TypeToken<List<YouTubeItem>>(){});

            response.setKind(responseApi.getString("kind"));
            response.setEtag(responseApi.getString("etag"));
            response.setItems(items);
            response.setPageInfo(pageInfo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
