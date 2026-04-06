package com.example.implicitintent1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnOpen, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = findViewById(R.id.btnOpen);
        btnBack = findViewById(R.id.btnBack);

        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.instagram.com/bhagyashrii_2207?igsh=dDhzMml3M2Exb2Jw"));
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
