package com.example.ise;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button b1Log,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        b1Log = findViewById(R.id.b1Log);
        btnBack = findViewById(R.id.btnBack);

        b1Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUser.getText().toString();
                String password = etPass.getText().toString();

                if(username.equals("bhagyashri") && password.equals("2222")) {

                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(
                            "com.example.profile_app",
                            "com.example.profile_app.ProfileActivity"
                    ));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
