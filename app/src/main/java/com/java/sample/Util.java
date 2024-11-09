package com.java.sample;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Util {

    @SuppressWarnings({"unchecked", "deprecation"})
    @Nullable
    public static <T extends Serializable> T getSerializable(@Nullable Bundle bundle, @Nullable String key, @NonNull Class<T> clazz) {
        if (bundle == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return bundle.getSerializable(key, clazz);
        }
        try {
            return (T) bundle.getSerializable(key);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static String readSql(Context context, int resRawSqlName) {
        StringBuilder linesSql = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resRawSqlName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                linesSql.append(line);
                linesSql.append("");
            }
            br.close();
            System.out.println("===> sql: " + linesSql.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===> error: " + e.getMessage());
        }
        return linesSql.toString();
    }
}
