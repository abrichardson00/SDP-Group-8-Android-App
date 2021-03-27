package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BringingRandom extends AppCompatActivity {

    private static final String TAG = "BringingRandom";

    private HandlerThread handlerThread = new HandlerThread("HandlerThread");
    private Handler threadHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper());

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_bringing_random);
        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, Tray_choices.class)));

        Tray selectedTray = (Tray) getIntent().getSerializableExtra("TRAY");
        retrieveTray(selectedTray);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void retrieveTray(Tray tray) {
        threadHandler.post(new TCPClient.RetrieveTrayRunnable(tray.getTrayCode()));
    }




}