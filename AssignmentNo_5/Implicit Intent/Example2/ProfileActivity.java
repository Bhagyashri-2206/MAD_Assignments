package com.example.explicitintent;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;   // ✅ IMPORTANT IMPORT
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnB = findViewById(R.id.btnB);

        btnB.setOnClickListener(v -> {
            finish();
        });
    }
}
