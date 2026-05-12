package com.example.aquaritual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;
import com.example.aquaritual.utils.PrefManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager prefManager = new PrefManager(this);

        if (prefManager.isOnboardingDone()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_welcome);

        Button btnLetsGo = findViewById(R.id.btnLetsGo);
        btnLetsGo.setOnClickListener(v ->
        {
            Intent intent = new Intent(WelcomeActivity.this, GenderActivity.class);
            startActivity(intent);
        });
    }
}
