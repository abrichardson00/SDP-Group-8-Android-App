package com.example.theostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_page extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private boolean isValid = false;
    private String uName = "Admin";
    private String uPassword = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById((R.id.etPassword));
        Info = (TextView) findViewById((R.id.tvInfo));
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("Number of attempts remaining: " + 5);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputName = Name.getText().toString();
                String inputPass = Password.getText().toString();

                if (inputName.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(Login_page.this, "Please enter all details correctly!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    isValid = validate(Name.getText().toString(), Password.getText().toString());

                    if(!isValid) {
                        counter--;

                        Info.setText("Number of attempts remaining: " + String.valueOf(counter));
                        Toast.makeText(Login_page.this, "Incorrect Credentials!", Toast.LENGTH_SHORT).show();
                        if(counter == 0)
                            Login.setEnabled(false);
                    }

                    else {
                        Toast.makeText(Login_page.this, "Login Successful :)", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_page.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });
    }

    private boolean validate(String name, String password)
    {
        if((name.equals(uName)) && (password.equals((uPassword))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}