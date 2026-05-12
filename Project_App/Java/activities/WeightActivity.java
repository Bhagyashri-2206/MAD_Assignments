package com.example.aquaritual.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;

public class WeightActivity extends AppCompatActivity {

    TextView txtKg;
    SeekBar seekWeight;
    Button btnNext;
    ImageView btnBack;

    int selectedWeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        txtKg = findViewById(R.id.txtKg);
        seekWeight = findViewById(R.id.seekWeight);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);


        txtKg.setText("0 kg");


        seekWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedWeight = progress;
                txtKg.setText(progress + " kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        btnBack.setOnClickListener(v -> finish());


        btnNext.setOnClickListener(v -> {
            if (selectedWeight == 0) {
                Toast.makeText(this, "Select weight", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, WakeUpActivity.class);
            intent.putExtra("gender", getIntent().getStringExtra("gender"));
            intent.putExtra("weight", selectedWeight);
            startActivity(intent);
        });

    }
}

