package com.java.sample.util;

import android.view.View;

public interface Observable {

    /**
     * Register observer
     * @param onEvent OnEventController
     */
    void register(OnEventController onEvent);

    /**
     * Remove observer
     * @param onEvent OnEventController
     */
    void unregister(OnEventController onEvent);

    /**
     * Destroy all observers
     */
    void destroy();

    /**
     * Get updates from subject
     *
     * @param onEvent OnEventController
     * @return Object
     */
    Object getUpdate(OnEventController onEvent);

    /**
     * Notify observers of change
     *
     * @param eventType Event Type
     * @param view View
     * @param data Data
     */
    void notify(int eventType, View view, Object data);

    /**
     * Publish message to observer
     *
     * @param eventType Event Type
     * @param view View
     * @param msg Data
     */
    void publish(int eventType, View view, Object msg);

//    /**
//     * Publish message to observer
//     *
//     * @param eventType Event Type
//     * @param msg Data
//     */
//    void publish(int eventType, Object msg);

}


