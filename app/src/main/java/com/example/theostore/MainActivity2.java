package com.example.theostore;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;


public class MainActivity2 extends AppCompatActivity {

    // Hello World Test

    private TextView traysInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // On page load...

        /* You have to point this at your own computer's network interface if you're hosting the
           API server locally.
           localhost doesn't work because it points to the Android simulator's own localhost.
           Use ipconfig on Windows or ifconfig on Linux.
           A timeout message is displayed in the app after 10s.
         */
        final String LOCALIP = "192.168.x.xxx";
        final String PORT = "5000";

        // Get the field to display the results on
        traysInfoView = (TextView) findViewById(R.id.textView2);

        // Brrrrrrr
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + LOCALIP + ":" + PORT + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit does its magic here and lets us call getTrays to get all the trays from the API
        THEOStoreApi theoStoreApi = retrofit.create(THEOStoreApi.class);
        Call<List<Tray>> call = theoStoreApi.getTrays();

        // We could just execute the call but this would do it on the main thread and stall the app
        // Instead, Retrofit gives us this enqueue thing that does the HTTP stuff on another thread
        call.enqueue(new Callback<List<Tray>>() {
            @Override
            public void onResponse(Call<List<Tray>> call, Response<List<Tray>> response) {
                // If we don't get a HTTP 2XX response (e.g. 404), show the response code
                if (!response.isSuccessful()) {
                    traysInfoView.append("Response code: " + response.code());
                    return;
                }

                // Get the list of trays from the HTTP response
                List<Tray> trays = response.body();

                // For each tray, list each field and append to the
                for (Tray tray : trays) {
                    String content = "";
                    content += "Name: " + tray.getName() + "\n";
                    content += "Info: " + tray.getInfo() + "\n";
                    content += "Currently out: " + tray.getCurrentlyOut() + "\n";
                    content += "Capacity: " + tray.getCapacity() + "\n";
                    content += "Timestamp: " + tray.getTimestamp() + "\n\n";
                    traysInfoView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Tray>> call, Throwable t) {
                traysInfoView.append(t.getMessage());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}