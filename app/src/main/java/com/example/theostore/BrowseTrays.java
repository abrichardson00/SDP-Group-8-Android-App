package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BrowseTrays extends AppCompatActivity {

    public static String DESCRIPTION = "com.example.theostore.DESCRIPTION";
    public static String IMAGE_URL = "com.example.theostore.IMAGE_URL";
    public static String TRAY_NO = "com.example.theostore.TRAY_NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_browse_trays);
    }

    public void tray1(View view) {
        Intent intent = new Intent(this, Tray_confirmation.class);
        intent.putExtra(DESCRIPTION, "Tray no:1 - bournville chocolate, apple, banana, pencil, pen, ruler");
        intent.putExtra(IMAGE_URL, "@drawable/tray1");
        intent.putExtra(TRAY_NO, "Tray no: 1");
        startActivity(intent);
    }

    public void tray2(View view) {
        Intent intent = new Intent(this, Tray_confirmation.class);
        intent.putExtra(DESCRIPTION, "Tray no:2 - blue eye glasses case, utility knife, deck of cards, black eye glasses case");
        intent.putExtra(IMAGE_URL, "@drawable/tray2");
        intent.putExtra(TRAY_NO, "Tray no: 2");
        startActivity(intent);
    }

    public void tray3(View view) {
        Intent intent = new Intent(this, Tray_confirmation.class);
        intent.putExtra(DESCRIPTION, "Books");
        intent.putExtra(IMAGE_URL, "@drawable/tray3");
        intent.putExtra(TRAY_NO, "Tray no: 3");
        startActivity(intent);
    }
}