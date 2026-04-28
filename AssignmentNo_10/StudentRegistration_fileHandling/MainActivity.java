package com.example.filehandling;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name, roll, email, address;
    RadioGroup genderGroup;
    Button saveBtn, loadBtn;
    ListView listView;

    ArrayList<String> records = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String fileName = "students.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        roll = findViewById(R.id.roll);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        genderGroup = findViewById(R.id.genderGroup);
        saveBtn = findViewById(R.id.saveBtn);
        loadBtn = findViewById(R.id.loadBtn);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);

        saveBtn.setOnClickListener(v -> saveData());
        loadBtn.setOnClickListener(v -> loadData());
    }

    private void saveData() {
        String sName = name.getText().toString();
        String sRoll = roll.getText().toString();
        String sEmail = email.getText().toString();
        String sAddress = address.getText().toString();

        int selectedId = genderGroup.getCheckedRadioButtonId();
        RadioButton rb = findViewById(selectedId);
        String gender = (rb != null) ? rb.getText().toString() : "Not Selected";

        String data = sName + "," + sRoll + "," + sEmail + "," + gender + "," + sAddress;

        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
            fos.write((data + "\n").getBytes());
            fos.close();

            Toast.makeText(this, "Info saved successfully", Toast.LENGTH_SHORT).show();

            name.setText("");
            roll.setText("");
            email.setText("");
            address.setText("");
            genderGroup.clearCheck();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        records.clear();

        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }

            reader.close();
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}