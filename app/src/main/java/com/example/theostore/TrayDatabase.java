package com.example.theostore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrayDatabase extends SQLiteOpenHelper {

    public static final String TRAY_TABLE = "TRAY_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TRAY_CODE = "TRAY_CODE";
    public static final String COLUMN_USER_INFO = "USER_INFO";
    public static final String COLUMN_EXTRACTED_INFO = "EXTRACTED_INFO";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_CAPACITY = "CAPACITY";

    public TrayDatabase(@Nullable Context context) {
        super(context, "tray.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TRAY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TRAY_CODE + " TEXT, " + COLUMN_USER_INFO + " TEXT, " + COLUMN_EXTRACTED_INFO + " TEXT, " + COLUMN_STATUS + " TEXT, " + COLUMN_CAPACITY + " REAL)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Tray tray) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, tray.getId());
        cv.put(COLUMN_TRAY_CODE, tray.getTrayCode());
        cv.put(COLUMN_USER_INFO, tray.getUserInfo());
        cv.put(COLUMN_EXTRACTED_INFO, tray.getExtractedInfo());
        cv.put(COLUMN_STATUS, tray.getStatus());
        cv.put(COLUMN_CAPACITY, tray.getCapacity());

        long insert = db.insert(TRAY_TABLE, null, cv);
        return true;
    }

    public List<Tray> getTrays() {
        List<Tray> trayList = new ArrayList<>();

        String getTraysSQL = "SELECT * FROM " + TRAY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(getTraysSQL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String trayCode = cursor.getString(1);
                String userInfo = cursor.getString(2);
                String extractedInfo = cursor.getString(3);
                String status = cursor.getString(4);
                float capacity = cursor.getFloat(5);

                Tray newTray = new Tray(id, trayCode, userInfo, extractedInfo, status, capacity);
                trayList.add(newTray);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return trayList;
    }

}
