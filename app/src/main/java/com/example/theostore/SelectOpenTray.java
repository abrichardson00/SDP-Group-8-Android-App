package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SelectOpenTray extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_select_open_tray);
    }
}