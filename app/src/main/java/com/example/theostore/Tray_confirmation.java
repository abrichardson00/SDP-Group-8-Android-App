package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Tray_confirmation extends AppCompatActivity {

    ImageView tray_image;
    TextView tray_description;
    TextView tray_number;

    Tray selectedTray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_tray_confirmation);

        tray_image = (ImageView) findViewById(R.id.tray_confirm);
        tray_description = (TextView) findViewById(R.id.tray_d);
        tray_number = (TextView) findViewById(R.id.tray_no);

        selectedTray = (Tray) getIntent().getSerializableExtra("TRAY");

//        Transformation imgTransform = new RoundedCornersTransformation(40, 0);
//        Picasso.get()
//                .load(selectedTray.getURI())
//                .placeholder(R.drawable.empty_tray)
//                .error(R.drawable.empty_tray)
//                .transform(imgTransform)
//                .into(tray_image);

        Glide.with(this).load(selectedTray.getURI()).into(tray_image);


        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, BrowseTrays.class)));


    }

    public void confirm(View view)
    {
        Intent intent = new Intent(this, Tray_choices.class);
        intent.putExtra("TRAY", selectedTray);
        startActivity(intent);
    }
}