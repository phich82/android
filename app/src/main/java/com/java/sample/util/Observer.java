package com.java.sample.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public enum Observer implements Observable {
    @SuppressLint("StaticFieldLeak")
    INSTANCE;

    private Context mContext;

    private final List<OnEventController> observers = new ArrayList<>();
    private Object message;
    private boolean changed;
    private final Object MUTEX = new Object();

    public static Observer newInstance(Context context) {
        INSTANCE.mContext = context;
        return INSTANCE;
    }

    /**
     * Register observer
     * @param onEvent OnEventController
     */
    @Override
    public void register(OnEventController onEvent) {
        if (onEvent == null) {
            throw new NullPointerException("Observer is null.");
        }
        synchronized (MUTEX) {
            if (!observers.contains(onEvent)) {
                observers.add(onEvent);
            }
        }
    }

    /**
     * Remove observer
     * @param onEvent OnEventController
     */
    @Override
    public void unregister(OnEventController onEvent) {
        synchronized (MUTEX) {
            observers.remove(onEvent);
        }
    }

    /**
     * Destroy all observers
     */
    @Override
    public void destroy() {
        observers.clear();
    }

    /**
     * Notify observers of change
     *
     * @param eventType Event Type
     * @param view View
     * @param data Data
     */
    @Override
    public void notify(int eventType, View view, Object data) {
        List<OnEventController> observersLocal = null;
        // Synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }
            observersLocal = new ArrayList<>(this.observers);
            this.changed = false;
        }
        for (OnEventController observer : observersLocal) {
            observer.onEvent(eventType, view, data);
        }
    }

    /**
     * Get updates from subject
     *
     * @param onEvent OnEventController
     * @return Object
     */
    @Override
    public Object getUpdate(OnEventController onEvent) {
        return message;
    }

    /**
     * Publish message to observer
     *
     * @param eventType Event Type
     * @param view View
     * @param msg Data
     */
    @Override
    public void publish(int eventType, View view, Object msg) {
        this.message = msg;
        this.changed = true;
        notify(eventType, view, msg);
    }

    /**
     * Publish message to observer
     *
     * @param eventType Event Type
     * @param msg Data
     */
    public void publish(int eventType, Object msg) {
        publish(eventType, null, msg);
    }

}