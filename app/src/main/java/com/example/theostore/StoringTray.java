package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class StoringTray extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_storing_tray);

        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, Tray_choices.class)));

    }
}