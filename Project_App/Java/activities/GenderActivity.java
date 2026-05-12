package com.example.aquaritual.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;

public class GenderActivity extends AppCompatActivity {

    private String selectedGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnNext = findViewById(R.id.btnNext);

        LinearLayout layoutMale = findViewById(R.id.layoutMale);
        LinearLayout layoutFemale = findViewById(R.id.layoutFemale);

        TextView txtMale = findViewById(R.id.txtMale);
        TextView txtFemale = findViewById(R.id.txtFemale);


        btnBack.setOnClickListener(v -> finish());

        layoutMale.setOnClickListener(v -> {
            layoutMale.setBackgroundResource(R.drawable.bg_selected);
            layoutFemale.setBackgroundResource(R.drawable.bg_unselected);

            txtMale.setTextColor(Color.WHITE);
            txtFemale.setTextColor(Color.parseColor("#0D47A1"));

            selectedGender = "Male";
        });

        layoutFemale.setOnClickListener(v -> {
            layoutFemale.setBackgroundResource(R.drawable.bg_selected);
            layoutMale.setBackgroundResource(R.drawable.bg_unselected);

            txtFemale.setTextColor(Color.WHITE);
            txtMale.setTextColor(Color.parseColor("#0D47A1"));

            selectedGender = "Female";
        });


        btnNext.setOnClickListener(v -> {
            if (selectedGender == null) {
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(GenderActivity.this, WeightActivity.class);
                intent.putExtra("gender", selectedGender);
                startActivity(intent);

            }
        });
    }
}

