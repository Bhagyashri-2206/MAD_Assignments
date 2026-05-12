package com.example.aquaritual.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import com.example.aquaritual.utils.DeviceIdUtil;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;
import com.example.aquaritual.model.UserProfile;
import com.example.aquaritual.utils.FirebaseManager;
import com.example.aquaritual.utils.NotificationHelper;
import com.example.aquaritual.utils.PrefManager;
import com.example.aquaritual.utils.WaterCalculator;

public class ResultActivity extends AppCompatActivity {

    private static final int REQ_CODE = 101;

    private int wakeHour, sleepHour;
    private String gender;
    private int weight;
    private int dailyWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        gender = getIntent().getStringExtra("gender");
        weight = getIntent().getIntExtra("weight", 60);
        wakeHour = getIntent().getIntExtra("wakeHour", 6);
        sleepHour = getIntent().getIntExtra("sleepHour", 22);

        dailyWater = WaterCalculator.calculate(
                gender, weight, wakeHour, sleepHour
        );

        TextView txtWaterValue = findViewById(R.id.txtWaterValue);
        txtWaterValue.setText(String.valueOf(dailyWater));

        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {

            // ✅ 1. Create profile (already exists)
            UserProfile profile = new UserProfile(
                    gender,
                    weight,
                    String.format("%02d:%02d", wakeHour, 0),
                    String.format("%02d:%02d", sleepHour, 0),
                    dailyWater
            );

            FirebaseManager.saveUserProfile(profile);

            // ✅ 2. 🔥 ADD THIS BLOCK HERE (VERY IMPORTANT)
            DatabaseReference settingsRef = FirebaseDatabase.getInstance()
                    .getReference("settings")
                    .child(DeviceIdUtil.getDeviceId(this));

            HashMap<String, Object> data = new HashMap<>();
            data.put("gender", gender);
            data.put("weight", String.valueOf(weight));
            data.put("wakeup", String.format("%02d:00", wakeHour));
            data.put("bedtime", String.format("%02d:00", sleepHour));
            data.put("target", String.valueOf(dailyWater));
            data.put("interval", "15");

            settingsRef.setValue(data);

            // ✅ 3. Continue existing flow
            new PrefManager(this).setOnboardingDone(true);

            NotificationHelper.createChannel(this);

            requestNotificationPermission();
        });
    }


    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_CODE
                );

            } else {
                startAppFlow();
            }

        } else {
            startAppFlow();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CODE) {
            startAppFlow();
        }
    }

    private void startAppFlow() {

        NotificationHelper.startSmartReminder(
                this,
                wakeHour,
                sleepHour,
                15
        );

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}