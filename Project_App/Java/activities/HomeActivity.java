package com.example.aquaritual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;
import com.example.aquaritual.utils.DeviceIdUtil;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private DonutProgress progress;
    private Button btnDrink, btnReset;

    private int consumed = 0;
    private int target = 2000;

    private DatabaseReference waterRef, settingsRef;
    private String deviceId;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        setupTabs(tabLayout, 0);

        progress = findViewById(R.id.progressCircle);
        btnDrink = findViewById(R.id.btnDrink);
        btnReset = findViewById(R.id.btnRefresh);

        deviceId = DeviceIdUtil.getDeviceId(this);

        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Calendar.getInstance().getTime());

        waterRef = FirebaseDatabase.getInstance()
                .getReference("water_logs")
                .child(deviceId)
                .child(todayDate);

        settingsRef = FirebaseDatabase.getInstance()
                .getReference("settings")
                .child(deviceId);

        loadTarget();
        loadTodayWater();

        // ✅ Drink button
        btnDrink.setOnClickListener(v -> addWater(250));

        // ✅ Reset button
        btnReset.setOnClickListener(v -> resetWater());
    }

    private void loadTarget() {
        settingsRef.child("target")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            try {
                                target = Integer.parseInt(snapshot.getValue(String.class));
                            } catch (Exception e) {
                                target = 2000;
                            }
                        }
                        progress.setMax(target);
                    }

                    @Override public void onCancelled(DatabaseError error) {}
                });
    }

    private void loadTodayWater() {
        waterRef.child("total")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        consumed = 0;

                        if (snapshot.exists()) {
                            Integer val = snapshot.getValue(Integer.class);
                            if (val != null) consumed = val;
                        }

                        updateProgress();
                    }

                    @Override public void onCancelled(DatabaseError error) {}
                });
    }

    private void addWater(int amount) {
        consumed += amount;

        if (consumed > target) {
            consumed = target;
        }

        waterRef.child("total").setValue(consumed);
        updateProgress();
    }

    private void resetWater() {
        consumed = 0;
        waterRef.child("total").setValue(0);
        updateProgress();
    }

    private void updateProgress() {
        progress.setMax(target);
        progress.setProgress(consumed);

        int percent = (int) ((consumed * 100.0f) / target);
        progress.setText(percent + "%");
    }

    private void setupTabs(TabLayout tabLayout, int selected) {
        tabLayout.selectTab(tabLayout.getTabAt(selected));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 1:
                        startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                        finish();
                        break;

                    case 2:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        finish();
                        break;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}