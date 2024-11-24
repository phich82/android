package com.java.sample.dto;


import org.json.JSONException;
import org.json.JSONObject;

public class ResponseStudent {

    private Boolean success = false;
    private String code = "";
    private String message = "";
    private Object data;

    public Boolean isSuccess() {
        return success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseStudent from(JSONObject responseApi) {
        ResponseStudent response = new ResponseStudent();
        try {
            response.setSuccess(responseApi.getBoolean("success"));
            response.setCode(responseApi.getString("code"));
            response.setMessage(responseApi.getString("message"));
            response.setData(responseApi.get("data"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
