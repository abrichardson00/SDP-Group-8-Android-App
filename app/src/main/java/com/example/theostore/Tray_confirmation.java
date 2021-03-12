package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Tray_confirmation extends AppCompatActivity {

    ImageView tray_image;
    TextView tray_description;
    TextView tray_number;
    String tray_image_text;
    String tray_n;
    public static String IMAGE_URL = "com.example.theostore.IMAGE_URL";
    public static String TRAY_NO = "com.example.theostore.TRAY_NO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_tray_confirmation);

        tray_image = (ImageView) findViewById(R.id.tray_i);
        tray_description = (TextView) findViewById(R.id.tray_d);
        tray_number = (TextView) findViewById(R.id.tray_no);

        Intent intent = getIntent();

        tray_image_text = intent.getStringExtra(BrowseTrays.IMAGE_URL);

        int imageResource = getResources().getIdentifier(tray_image_text, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);

        tray_image.setImageDrawable(res);
        tray_description.setText(intent.getStringExtra(BrowseTrays.DESCRIPTION));
        tray_number.setText(intent.getStringExtra(BrowseTrays.TRAY_NO));
        tray_n = intent.getStringExtra(BrowseTrays.TRAY_NO);

        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, BrowseTrays.class)));


    }

    public void confirm(View view)
    {
        Intent intent = new Intent(this, Tray_choices.class);
        intent.putExtra(IMAGE_URL, tray_image_text);
        intent.putExtra(TRAY_NO, tray_n);
        startActivity(intent);
    }
}