package com.example.aquaritual;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.activities.HomeActivity;
import com.example.aquaritual.activities.WakeUpActivity;
import com.example.aquaritual.utils.PrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Optional UI edge-to-edge (can keep or remove)
        EdgeToEdge.enable(this);

        // ✅ Check onboarding status
        PrefManager pref = new PrefManager(this);

        if (pref.isOnboardingDone()) {
            // 👉 User already completed setup
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            // 👉 First time user
            startActivity(new Intent(this, WakeUpActivity.class));
        }

        finish(); // 🔥 close MainActivity so user can't come back
    }
}