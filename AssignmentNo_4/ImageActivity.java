package com.example.pr1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        imageView = findViewById(R.id.imgAnimal);
        btnBack = findViewById(R.id.btnBack);

        String animal = getIntent().getStringExtra("animal");

        if (animal != null) {
            switch (animal) {
                case "lion":
                    imageView.setImageResource(R.drawable.lion);
                    break;

                case "tiger":
                    imageView.setImageResource(R.drawable.tiger);
                    break;

                case "elephant":
                    imageView.setImageResource(R.drawable.ele);
                    break;
            }
        }

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
