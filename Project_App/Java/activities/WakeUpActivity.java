package com.example.aquaritual.activities;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.aquaritual.R;

import java.lang.reflect.Field;

public class WakeUpActivity extends AppCompatActivity {

    NumberPicker pickerHour, pickerMinute;
    ImageView btnBack;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        pickerHour = findViewById(R.id.pickerHour);
        pickerMinute = findViewById(R.id.pickerMinute);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);


        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(12);
        pickerHour.setValue(6);
        pickerHour.setWrapSelectorWheel(true);


        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(59);
        pickerMinute.setValue(0);
        pickerMinute.setFormatter(value -> String.format("%02d", value));
        pickerMinute.setWrapSelectorWheel(true);


        styleNumberPicker(pickerHour);
        styleNumberPicker(pickerMinute);


        btnBack.setOnClickListener(v -> finish());


        btnNext.setOnClickListener(v -> {

            Intent intent = new Intent(WakeUpActivity.this, BedTimeActivity.class);

            intent.putExtra("gender", getIntent().getStringExtra("gender"));
            intent.putExtra("weight", getIntent().getIntExtra("weight", 60));
            intent.putExtra("wakeHour", pickerHour.getValue());
            intent.putExtra("wakeMinute", pickerMinute.getValue());

            startActivity(intent);
        });

    }

    private void styleNumberPicker(NumberPicker picker) {
        try {

            Field divider = NumberPicker.class.getDeclaredField("mSelectionDivider");
            divider.setAccessible(true);
            divider.set(picker, null);


            for (int i = 0; i < picker.getChildCount(); i++) {
                if (picker.getChildAt(i) instanceof EditText) {
                    EditText editText = (EditText) picker.getChildAt(i);
                    editText.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    editText.setTextSize(32);
                    editText.setTypeface(editText.getTypeface(), android.graphics.Typeface.BOLD);
                    editText.setBackground(null);
                }
            }


            int min = picker.getMinValue();
            int max = picker.getMaxValue();
            picker.setMinValue(min);
            picker.setMaxValue(max);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
