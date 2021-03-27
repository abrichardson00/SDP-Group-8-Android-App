package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StoringTray extends AppCompatActivity {

    private static final String TAG = "StoringTray";

//    private HandlerThread handlerThread = new HandlerThread("HandlerThreadStoring");
//    private Handler threadHandler;

    private ProgressBar progressBar;
    private TextView progressText;

    private Handler mainHandler = new Handler();

    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storing_tray);




//        try
//        {
//            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}

        progressBar = findViewById(R.id.store_progress);
        progressText = findViewById(R.id.store_text);

        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, Tray_choices.class)));

        Tray selectedTray = (Tray) getIntent().getSerializableExtra("TRAY");
        storeTray();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        // Disable back until tray stored
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        handlerThread.quit();
//    }



    private void storeTray() {
        new Thread(new TCPClient.StoreTrayRunnable(this, progressBar, progressText)).start();
    }

//    private void storeTray() {
//        new Thread(new StoreRunnable()).start();
//    }

//    class StoreRunnable implements Runnable {
//
//        StoreRunnable(){};
//
//        @Override
//        public void run() {
//
//            for (int i = 0; i <= 100; i++) {
//                int copyi = i;
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressBar.setProgress(copyi);
//                    }
//                });
//
//            }
//
//        }
//    }



}