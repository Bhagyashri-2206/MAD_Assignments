package com.example.internalstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btnSave, btnRead;
    TextView txtOutput;

    String fileName = "myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        txtOutput = findViewById(R.id.txtOutput);


        btnSave.setOnClickListener(view -> {
            try {
                String data = editText.getText().toString();

                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();

                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                editText.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnRead.setOnClickListener(view -> {
            try {
                FileInputStream fis = openFileInput(fileName);

                int i;
                StringBuilder data = new StringBuilder();

                while ((i = fis.read()) != -1) {
                    data.append((char) i);
                }

                fis.close();
                txtOutput.setText(data.toString());

            } catch (Exception e) {
                txtOutput.setText("No data found");
            }
        });
    }
}