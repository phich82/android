package com.java.sample.util;

import android.view.View;

//public interface OnEventController {
//
//    int PROVIDE_DATA = 999999;
//
//    /**
//     * Send data and Handle Event in App
//     * @param eventType Event Type
//     * @param view View
//     * @param data Data
//     */
//    void onEvent(int eventType, View view, Object data);
//
//    /**
//     * Send data and Handle Event in App
//     *
//     * @param eventType Event Type
//     * @param view View
//     * @param data Data
//     */
//    void onEvent(int eventType, View view, Object data, OnEventController eventController);
//
//}

//public interface OnEventController<T> {
//
//    int PROVIDE_DATA = 999999;
//
//    /**
//     * Send data and Handle Event in App
//     *
//     * @param eventType
//     * @param view
//     * @param data
//     */
//    void onEvent(int eventType, View view, T data);
//
//    /**
//     * Send data and Handle Event in App
//     *
//     * @param eventType Event Type
//     * @param view View
//     * @param data Data
//     */
//    void onEvent(int eventType, View view, T data, OnEventController<T> eventController);
//}

public interface OnEventController {

    int PROVIDE_DATA = 999999;

    /**
     * Send data and Handle Event in App
     * @param eventType Event Type
     * @param view View
     * @param data Data
     * @param <T> Data Type
     */
    <T> void onEvent(int eventType, View view, T data);

    /**
     * Send data and Handle Event in App
     *
     * @param eventType Event Type
     * @param view View
     * @param data Data
     */
    <T> void onEvent(int eventType, View view, T data, OnEventController eventController);
}

