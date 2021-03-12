package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);
    }

    public void select(View view)
    {
        Intent intent = new Intent(this, BrowseTrays.class);
        startActivity(intent);
    }

    public void help(View view)
    {
        Intent intent = new Intent(this, HelpSupport.class);
        startActivity(intent);
    }
}