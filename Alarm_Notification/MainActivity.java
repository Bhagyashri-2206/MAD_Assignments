package com.example.alarmapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnDate, btnTime, btnSetAlarm;

    int year, month, day, hour, minute;

    Calendar selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);

        selectedDateTime = Calendar.getInstance();

        year = selectedDateTime.get(Calendar.YEAR);
        month = selectedDateTime.get(Calendar.MONTH);
        day = selectedDateTime.get(Calendar.DAY_OF_MONTH);
        hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        minute = selectedDateTime.get(Calendar.MINUTE);

        // Notification Permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101
                );
            }
        }

        // Date Picker
        btnDate.setOnClickListener(v -> {

            Calendar current = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;

                        btnDate.setText(
                                selectedDay + "/" +
                                        (selectedMonth + 1) + "/" +
                                        selectedYear
                        );
                    },
                    current.get(Calendar.YEAR),
                    current.get(Calendar.MONTH),
                    current.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.getDatePicker().setMinDate(
                    System.currentTimeMillis() - 1000
            );

            datePickerDialog.show();
        });

        // Time Picker
        btnTime.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    MainActivity.this,
                    (view, selectedHour, selectedMinute) -> {

                        hour = selectedHour;
                        minute = selectedMinute;

                        String amPm = (selectedHour >= 12) ? "PM" : "AM";

                        int displayHour;
                        if (selectedHour == 0) {
                            displayHour = 12;
                        } else if (selectedHour > 12) {
                            displayHour = selectedHour - 12;
                        } else {
                            displayHour = selectedHour;
                        }

                        btnTime.setText(
                                displayHour + ":" +
                                        String.format("%02d", selectedMinute) +
                                        " " + amPm
                        );
                    },
                    hour,
                    minute,
                    false
            );

            timePickerDialog.show();
        });

        // Set Alarm
        btnSetAlarm.setOnClickListener(v -> {

            selectedDateTime.set(Calendar.YEAR, year);
            selectedDateTime.set(Calendar.MONTH, month);
            selectedDateTime.set(Calendar.DAY_OF_MONTH, day);
            selectedDateTime.set(Calendar.HOUR_OF_DAY, hour);
            selectedDateTime.set(Calendar.MINUTE, minute);
            selectedDateTime.set(Calendar.SECOND, 0);
            selectedDateTime.set(Calendar.MILLISECOND, 0);

            // Check future time
            if (selectedDateTime.getTimeInMillis() <= System.currentTimeMillis()) {
                Toast.makeText(
                        MainActivity.this,
                        "Select future time!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent = new Intent(
                    MainActivity.this,
                    AlarmReceiver.class
            );

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT |
                            PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager =
                    (AlarmManager) getSystemService(ALARM_SERVICE);

            if (alarmManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                    if (alarmManager.canScheduleExactAlarms()) {

                        alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                selectedDateTime.getTimeInMillis(),
                                pendingIntent
                        );

                    } else {

                        Toast.makeText(
                                MainActivity.this,
                                "Enable exact alarm permission in settings",
                                Toast.LENGTH_LONG
                        ).show();

                        Intent intentSettings = new Intent(
                                android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        );

                        startActivity(intentSettings);
                        return;
                    }

                } else {

                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            selectedDateTime.getTimeInMillis(),
                            pendingIntent
                    );
                }

                Toast.makeText(
                        MainActivity.this,
                        "Alarm Set Successfully!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
