package com.example.multibuttons;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.b1);
        b1.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.layoutstype",
                    "com.example.layoutstype.MainActivity"
            ));
            startActivity(intent);
        });

        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.relativelayout",
                    "com.example.relativelayout.MainActivity"
            ));
            startActivity(intent);
        });
        Button b3 = findViewById(R.id.b3);
        b3.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.absolutelayout",
                    "com.example.absolutelayout.MainActivity"
            ));
            startActivity(intent);
        });
        Button b4 = findViewById(R.id.b4);
        b4.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.gridlayout",
                    "com.example.gridlayout.MainActivity"
            ));
            startActivity(intent);
        });
        Button b5 = findViewById(R.id.b5);
        b5.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.framelayout",
                    "com.example.framelayout.MainActivity"
            ));
            startActivity(intent);
        });
        Button b6 = findViewById(R.id.b6);
        b6.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.tablelayout",
                    "com.example.tablelayout.MainActivity"
            ));
            startActivity(intent);
        });
        Button b7 = findViewById(R.id.b7);
        b7.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(

                    "com.example.explicitintent",
                    "com.example.explicitintent.MainActivity"
            ));
            startActivity(intent);
        });
        Button b8 = findViewById(R.id.b8);
        b8.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.implicitintent1",
                    "com.example.implicitintent1.MainActivity"
            ));
            startActivity(intent);
        });
        Button b9 = findViewById(R.id.b9);
        b9.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.implicitintent2",
                    "com.example.implicitintent2.MainActivity"
            ));
            startActivity(intent);
        });
        Button b10 = findViewById(R.id.b10);
        b10.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.pr1",
                    "com.example.pr1.MainActivity"
            ));
            startActivity(intent);
        });
        Button b11 = findViewById(R.id.b11);
        b11.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.example.calculator",
                    "com.example.calculator.MainActivity"
            ));
            startActivity(intent);
        });

    }
}
