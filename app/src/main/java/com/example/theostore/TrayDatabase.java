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

    private static final String TRAY_TABLE = "TRAY_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TRAY_CODE = "TRAY_CODE";
    private static final String COLUMN_USER_INFO = "USER_INFO";
    private static final String COLUMN_EXTRACTED_INFO = "EXTRACTED_INFO";
    private static final String COLUMN_STATUS = "STATUS";
    private static final String COLUMN_CAPACITY = "CAPACITY";
    private static final String COLUMN_IMAGE_VERSION = "IMAGE_VERSION";

    public TrayDatabase(@Nullable Context context) {
        super(context, "tray.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPopulatedDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE TRAY_TABLE;");
        createPopulatedDatabase(db);
    }

    private void createPopulatedDatabase(SQLiteDatabase db) {

        String createTableSQL = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s REAL, %s INTEGER);",
                TRAY_TABLE,
                COLUMN_ID,
                COLUMN_TRAY_CODE,
                COLUMN_USER_INFO,
                COLUMN_EXTRACTED_INFO,
                COLUMN_STATUS,
                COLUMN_CAPACITY,
                COLUMN_IMAGE_VERSION);

        String insertSQL = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES " +
                        "(1, \"FL0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(2, \"FR0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(3, \"BL0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(4, \"BR0\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(5, \"FL1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(6, \"FR1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(7, \"BL1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(8, \"BR1\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(9, \"FL2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(10, \"FR2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(11, \"BL2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(12, \"BR2\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(13, \"FL3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(14, \"FR3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(15, \"BL3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(16, \"BR3\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(17, \"FL4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(18, \"FR4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(19, \"BL4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0)," +
                        "(20, \"BR4\", \"No user description\", \"No extracted info\", \"STORED\", 0.0, 0);",
                TRAY_TABLE,
                COLUMN_ID,
                COLUMN_TRAY_CODE,
                COLUMN_USER_INFO,
                COLUMN_EXTRACTED_INFO,
                COLUMN_STATUS,
                COLUMN_CAPACITY,
                COLUMN_IMAGE_VERSION);

        db.execSQL(createTableSQL);
        db.execSQL(insertSQL);
    }

    public List<Tray> getAllTrays() {
        String queryString = "SELECT * FROM TRAY_TABLE;";
        return traysFromQuery(queryString);
    }

    public Tray getTray(int tray_id) {
        String queryString = "SELECT * FROM TRAY_TABLE AS T WHERE T.ID = " + tray_id + ";";
        List<Tray> trayList = traysFromQuery(queryString);

        if (trayList.size() == 0) {
            return null;
        } else {
            return trayList.get(0);
        }
    }

    public Tray getTray(String tray_code) {
        String queryString = "SELECT * FROM TRAY_TABLE AS T WHERE T.TRAY_CODE = \"" + tray_code + "\";";
        List<Tray> trayList = traysFromQuery(queryString);

        if (trayList.size() == 0) {
            return null;
        } else {
            return trayList.get(0);
        }
    }

    public List<Tray> getStoredTrays() {
        String queryString = "SELECT * FROM TRAY_TABLE AS T WHERE T.STATUS = \"STORED\";";
        return traysFromQuery(queryString);
    }

    public List<Tray> getOpenTrays() {
        String queryString = "SELECT * FROM TRAY_TABLE AS T WHERE T.STATUS = \"OUT\";";
        return traysFromQuery(queryString);
    }

    public boolean markTrayStored(String trayCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STATUS", "STORED");
        cv.put("IMAGE_VERSION", (System.currentTimeMillis() / 1000));

        int changes = db.update(TRAY_TABLE, cv, "TRAY_CODE = \"" + trayCode + "\";", new String[] {});

        return changes > 0;
    }

    public boolean markTrayOut(Tray tray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STATUS", "OUT");

        int changes = db.update(TRAY_TABLE, cv, "ID = " + tray.getId(), new String[] {});

        return changes > 0;
    }

    public boolean changeExtractedInfoWithTrayCode(String trayCode, String extractedInfo, float capacity) {

        if (extractedInfo == null) {
            System.out.println("oh no, null");
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EXTRACTED_INFO", extractedInfo);
        cv.put("CAPACITY", capacity);

        int changes = db.update(TRAY_TABLE, cv, "TRAY_CODE = \"" + trayCode + "\"", new String[] {});

        return changes > 0;
    }

    public List<Tray> getEmptyTrays() {
        System.out.println("about to crash");
        String queryString = "SELECT * FROM TRAY_TABLE AS T WHERE T.CAPACITY < 0.01 AND T.STATUS = \"STORED\";";
        System.out.println(queryString);
        return traysFromQuery(queryString);
    }

    private List<Tray> traysFromQuery(String SQLQuery) {
        List<Tray> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQLQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String trayCode = cursor.getString(1);
                String userInfo = cursor.getString(2);
                String extractedInfo = cursor.getString(3);
                String status = cursor.getString(4);
                float capacity = cursor.getFloat(5);
                int imageVersion = cursor.getInt(6);

                Tray newTray = new Tray(id, trayCode, userInfo, extractedInfo, status, capacity, imageVersion);
                result.add(newTray);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return result;
    }



}
