package com.example.theostore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
public class LoggedOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_logged_out);

    }

    public void login(View view)
    {
        Intent intent = new Intent(this, Login_page.class);
        startActivity(intent);
    }

    public void register(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
