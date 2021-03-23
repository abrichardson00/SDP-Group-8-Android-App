package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

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

        List<Tray> allTrays = trayDatabase.getStoredTrays();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                trayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        trayDatabase.close();

    }
}