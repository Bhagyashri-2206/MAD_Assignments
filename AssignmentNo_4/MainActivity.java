package com.example.pr1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLion, btnTiger, btnElephant, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLion = findViewById(R.id.b1);
        btnTiger = findViewById(R.id.b2);
        btnElephant = findViewById(R.id.b3);
        btnBack = findViewById(R.id.btnBack);

        btnLion.setOnClickListener(v -> openAnimal("lion"));
        btnTiger.setOnClickListener(v -> openAnimal("tiger"));
        btnElephant.setOnClickListener(v -> openAnimal("elephant"));


        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void openAnimal(String animalName) {
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        intent.putExtra("animal", animalName);
        startActivity(intent);
    }
}
