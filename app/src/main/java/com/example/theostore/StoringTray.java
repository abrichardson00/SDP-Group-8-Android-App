package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageButton;

public class StoringTray extends AppCompatActivity {

    private static final String TAG = "StoringTray";

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

        setContentView(R.layout.activity_storing_tray);

        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, Tray_choices.class)));

        Tray selectedTray = (Tray) getIntent().getSerializableExtra("TRAY");
        storeTray(selectedTray);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    private void storeTray(Tray tray) {
        threadHandler.post(new TCPClient.StoreTrayRunnable(tray.getTrayCode()));
    }

}