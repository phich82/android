package com.java.sample.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insertBlob(String sql, Object[] bindings) {
        System.out.println("insertBlob ===> start");
        System.out.println("insertBlob ===> sql:" + sql);
        System.out.println("insertBlob ===> bindings:" + bindings);
        // Sql format: ?, ?, ? -> new String[] {1, 2, 3}
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        int index = 1;
        for (Object v: bindings) {
            if (v == null) {
                statement.bindNull(index);
            } else if (v instanceof String) {
                statement.bindString(index, (String) v);
            } else if (v instanceof Integer) {
                statement.bindLong(index, (Integer) v);
            } else if (v instanceof Long) {
                statement.bindLong(index, (Long) v);
            } else if (v instanceof Double) {
                statement.bindDouble(index, (Double) v);
            } else if (v instanceof byte[]) {
                statement.bindBlob(index, (byte[]) v);
            }
            index++;
        }
        statement.executeInsert();
        System.out.println("insertBlob ===> end");
    }

    public void execute(String sql, Object[] bindings) {
        System.out.println("execute ===> start");
        SQLiteDatabase db = getWritableDatabase();
        System.out.println("execute: sql ===> " + sql);
        db.execSQL(sql, bindings);
        System.out.println("execute ===> end");
    }

    public void execute(String sql) {
        execute(sql, new Object[]{});
    }

    public Cursor select(String sql, String[] binding) {
        System.out.println("select ===> start");
        System.out.println("select: sql ===> " + sql);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, binding);
        System.out.println("select ===> end");
        return cursor;
    }

    public Cursor select(String sql) {
        return select(sql, new String[]{});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
