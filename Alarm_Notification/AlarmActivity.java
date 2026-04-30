package com.example.alarmapplication;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    Button btnDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        btnDismiss = findViewById(R.id.btnDismiss);

        btnDismiss.setOnClickListener(v -> {
            if (AlarmReceiver.mediaPlayer != null) {
                AlarmReceiver.mediaPlayer.stop();
                AlarmReceiver.mediaPlayer.release();
            }
            finish();
        });
    }
}