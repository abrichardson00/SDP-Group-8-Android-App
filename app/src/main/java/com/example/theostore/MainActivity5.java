package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
    }

    public void bring(View view)
    {
        Intent intent = new Intent(this, BringingRandom.class);
        startActivity(intent);
    }

    public void store(View view)
    {
        Intent intent = new Intent(this, StoringTray.class);
        startActivity(intent);
    }

    public void update_info(View view)
    {
        Intent intent = new Intent(this, TrayUpdated.class);
        startActivity(intent);
    }
}