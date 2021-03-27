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


public class Tray_choices extends AppCompatActivity {

    ImageView tray_image;
    TextView tray_no;
    TextView tray_description;

    Tray selectedTray;

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

        setContentView(R.layout.activity_tray_choices);

        tray_image = (ImageView) findViewById(R.id.tray_image);
        tray_no = (TextView) findViewById(R.id.tray_number);
        tray_description = (TextView) findViewById(R.id.tray_choices_description);


        selectedTray = (Tray) getIntent().getSerializableExtra("TRAY");

        tray_description.setText(selectedTray.getUserInfo());

//        Transformation imgTransform = new RoundedCornersTransformation(40, 0);
//        Picasso.get()
//                .load(selectedTray.getURI())
//                .placeholder(R.drawable.empty_tray)
//                .error(R.drawable.empty_tray)
//                .transform(imgTransform)
//                .into(tray_image);

        Glide.with(this).load(selectedTray.getURI()).into(tray_image);

//        Intent intent = getIntent();

//        tray_image_text = intent.getStringExtra(Tray_confirmation.IMAGE_URL);

//        int imageResource = getResources().getIdentifier(tray_image_text, null, getPackageName());

//        Drawable res = getResources().getDrawable(imageResource);

//        tray_image.setImageDrawable(res);
//        tray_no.setText(intent.getStringExtra(Tray_confirmation.TRAY_NO));

        ImageButton back_button = (ImageButton) findViewById(R.id.bt_back);

        back_button.setOnClickListener(v -> startActivity(new Intent(this, Tray_confirmation.class)));

    }

    public void bring(View view)
    {
        Intent intent = new Intent(this, BringingRandom.class);
        intent.putExtra("TRAY", selectedTray);
        startActivity(intent);
    }

    public void store(View view)
    {
        Intent intent = new Intent(this, StoringTray.class);
        intent.putExtra("TRAY", selectedTray);
        startActivity(intent);
    }

    public void update_info(View view)
    {
        Intent intent = new Intent(this, TrayUpdated.class);
        startActivity(intent);
    }
}