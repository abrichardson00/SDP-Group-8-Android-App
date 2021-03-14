package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView trayRecyclerView;
    private TrayListAdapter trayAdapter;
    private RecyclerView.LayoutManager trayLayoutManager;

    TrayDatabase trayDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_trays);

        trayDatabase = new TrayDatabase(RecyclerActivity.this);

        List<Tray> allTrays = trayDatabase.getTrays();

        trayRecyclerView = findViewById(R.id.recyclerView);
        trayRecyclerView.setHasFixedSize(true);
        trayLayoutManager = new LinearLayoutManager(this);

        trayAdapter = new TrayListAdapter(this, allTrays);

        trayRecyclerView.setLayoutManager(trayLayoutManager);
        trayRecyclerView.setAdapter(trayAdapter);

        trayAdapter.setOnItemClickListener(new TrayListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Tray clickedTray = allTrays.get(position);
                Intent intent = new Intent(RecyclerActivity.this, Tray_confirmation.class);
                intent.putExtra("TRAY", clickedTray);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        trayDatabase.close();

    }
}