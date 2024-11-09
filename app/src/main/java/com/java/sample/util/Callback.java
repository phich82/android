package com.java.sample.util;

import java.util.concurrent.Callable;

public abstract class Callback<Boolean, T> implements Callable<Void> {
    public Boolean success;
    public T data;

    public void setResult (Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public abstract Void call();
}
