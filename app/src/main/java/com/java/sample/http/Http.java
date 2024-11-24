package com.java.sample.http;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.java.sample.util.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.function.BiFunction;


public class Http {

    public void request(Context context, String url, BiFunction<Boolean, JSONObject, Void> callback) {
        request(context, url, Request.Method.GET, callback);
    }

    public void request(Context context, String url, int method, BiFunction<Boolean, JSONObject, Void> callback) {
        request(context, url, method, null, callback);
    }

    public void request(Context context, String url, int method, JSONObject requestBody, BiFunction<Boolean, JSONObject, Void> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.apply(true, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                        callback.apply(false, null);
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void request(Context context, String url, Callback<Boolean, JSONObject> callback) {
        request(context, url, Request.Method.GET, null, callback);
    }

    public void request(Context context, String url, int method, Callback<Boolean, JSONObject> callback) {
        request(context, url, method, null, callback);
    }

    public void request(Context context, String url, int method, JSONObject requestBody, Callback<Boolean, JSONObject> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                method,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.setResult(true, response);
                            callback.call();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        System.out.println("===> error (http): " + e.getMessage());
                        e.printStackTrace();
                        callback.setResult(false, null);
                        try {
                            callback.call();
                        } catch(Exception ex) {
                            System.out.println("===> error (callback): " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
//            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
