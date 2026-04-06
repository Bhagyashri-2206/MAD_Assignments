package com.example.explicitintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(username.equals("bhagyashri") && password.equals("2206")) {

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this,
                            "Invalid Username or Password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
