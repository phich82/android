package com.java.sample.http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.java.sample.util.Callback;

import org.json.JSONArray;

import java.util.function.BiFunction;


public class Http {

    public void request(String url, Context context, BiFunction<Boolean, JSONArray, Void> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
        requestQueue.add(jsonArrayRequest);
    }

    public void request(String url, Context context, Callback<Boolean, JSONArray> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                        e.printStackTrace();
                        callback.setResult(false, null);
                        try {
                            callback.call();
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
