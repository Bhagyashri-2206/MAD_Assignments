package com.example.aquaritual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;
import com.example.aquaritual.utils.DeviceIdUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private TextView txtSelectedDate, txtWaterConsumed;
    private CalendarView calendarView;

    private DatabaseReference waterRef;
    private String deviceId;

    private ValueEventListener waterListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtWaterConsumed = findViewById(R.id.txtWaterConsumed);
        calendarView = findViewById(R.id.calendarView);

        deviceId = DeviceIdUtil.getDeviceId(this);

        waterRef = FirebaseDatabase.getInstance()
                .getReference("water_logs")
                .child(deviceId);

        setupTabs();

        // ✅ Show today's data by default
        String today = formatDate(Calendar.getInstance());
        showWaterForDate(today);

        // ✅ Date selection
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            showWaterForDate(formatDate(cal));
        });
    }

    // ✅ Show water for selected date
    private void showWaterForDate(String dateKey) {

        txtSelectedDate.setText("Date: " + dateKey);

        // 🔥 Remove old listener (safe)
        if (waterListener != null) {
            waterRef.child(dateKey).removeEventListener(waterListener);
        }

        waterListener = waterRef.child(dateKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        int total = 0;

                        if (snapshot.exists()) {

                            // ✅ 1. Direct total value (current system)
                            Integer val = snapshot.child("total").getValue(Integer.class);
                            if (val != null) total = val;

                            // ✅ 2. If logs exist → calculate sum (future-proof)
                            if (snapshot.hasChild("logs")) {
                                total = 0;
                                for (DataSnapshot logSnap : snapshot.child("logs").getChildren()) {
                                    Integer amount = logSnap.child("amount").getValue(Integer.class);
                                    if (amount != null) total += amount;
                                }
                            }
                        }

                        // ✅ Show ML + Liters
                        float liters = total / 1000f;

                        txtWaterConsumed.setText(
                                "Water Consumed: " + total + " ml (" +
                                        String.format(Locale.getDefault(), "%.2f", liters) + " L)"
                        );
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        txtWaterConsumed.setText("Failed to load");
                    }
                });
    }

    // ✅ Format date
    private String formatDate(Calendar cal) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(cal.getTime());
    }

    // ✅ Tabs navigation
    private void setupTabs() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.selectTab(tabLayout.getTabAt(1));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
                    finish();
                }

                if (tab.getPosition() == 2) {
                    startActivity(new Intent(HistoryActivity.this, SettingActivity.class));
                    finish();
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // ✅ Prevent memory leak
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (waterListener != null) {
            waterRef.removeEventListener(waterListener);
        }
    }
}