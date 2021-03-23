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

        String createTableSQL = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s REAL);",
                TRAY_TABLE,
                COLUMN_ID,
                COLUMN_TRAY_CODE,
                COLUMN_USER_INFO,
                COLUMN_EXTRACTED_INFO,
                COLUMN_STATUS,
                COLUMN_CAPACITY);

        String insertSQL = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES " +
                        "(1, \"FL0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(2, \"FR0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(3, \"BL0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(4, \"BR0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(5, \"FL1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(6, \"FR1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(7, \"BL1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(8, \"BR1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(9, \"FL2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(10, \"FR2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(11, \"BL2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(12, \"BR2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(13, \"FL3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(14, \"FR3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(15, \"BL3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(16, \"BR3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(17, \"FL4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(18, \"FR4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(19, \"BL4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0)," +
                        "(20, \"BR4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0);",
                TRAY_TABLE,
                COLUMN_ID,
                COLUMN_TRAY_CODE,
                COLUMN_USER_INFO,
                COLUMN_EXTRACTED_INFO,
                COLUMN_STATUS,
                COLUMN_CAPACITY);

        db.execSQL(createTableSQL);
        db.execSQL(insertSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public boolean addOne(Tray tray) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_ID, tray.getId());
//        cv.put(COLUMN_TRAY_CODE, tray.getTrayCode());
//        cv.put(COLUMN_USER_INFO, tray.getUserInfo());
//        cv.put(COLUMN_EXTRACTED_INFO, tray.getExtractedInfo());
//        cv.put(COLUMN_STATUS, tray.getStatus());
//        cv.put(COLUMN_CAPACITY, tray.getCapacity());
//
//        db.insert(TRAY_TABLE, null, cv);
//        return true;
//    }

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
        }

        cursor.close();
        db.close();
        return trayList;
    }

    public List<Tray> getStoredTrays() {
        List<Tray> trayList = new ArrayList<>();

        String getTraysSQL = "SELECT * FROM TRAY_TABLE AS T WHERE T.STATUS = \"STORED\";";

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
        }

        cursor.close();
        db.close();
        return trayList;
    }

    public boolean markTrayStored(Tray tray) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("STATUS", "STORED");

        int changes = db.update(TRAY_TABLE, cv, "WHERE ID = " + tray.getId(), new String[] {});

        return changes > 0;
    }

    public boolean markTrayOut(Tray tray) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("STATUS", "OUT");

        int changes = db.update(TRAY_TABLE, cv, "WHERE ID = " + tray.getId(), new String[] {});

        return changes > 0;
    }


    //    public boolean addOne(Tray tray) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_ID, tray.getId());
//        cv.put(COLUMN_TRAY_CODE, tray.getTrayCode());
//        cv.put(COLUMN_USER_INFO, tray.getUserInfo());
//        cv.put(COLUMN_EXTRACTED_INFO, tray.getExtractedInfo());
//        cv.put(COLUMN_STATUS, tray.getStatus());
//        cv.put(COLUMN_CAPACITY, tray.getCapacity());
//
//        db.insert(TRAY_TABLE, null, cv);
//        return true;
//    }



}
