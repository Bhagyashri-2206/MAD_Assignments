package com.example.assignment6;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;
    RadioGroup radioGroup;
    CheckBox checkReading, checkSports, checkMusic;
    ToggleButton toggleNotification;
    Button btnSubmit;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        radioGroup = findViewById(R.id.radioGroup);

        checkReading = findViewById(R.id.checkReading);
        checkSports = findViewById(R.id.checkSports);
        checkMusic = findViewById(R.id.checkMusic);

        toggleNotification = findViewById(R.id.toggleNotification);
        btnSubmit = findViewById(R.id.btnSubmit);
        textResult = findViewById(R.id.textResult);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String email = editEmail.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedId);
                String gender = (selectedRadio != null) ?
                        selectedRadio.getText().toString() : "Not Selected";


                String hobbies = "";
                if (checkReading.isChecked()) hobbies += "Reading ";
                if (checkSports.isChecked()) hobbies += "Sports ";
                if (checkMusic.isChecked()) hobbies += "Music ";

                if (hobbies.isEmpty()) hobbies = "None";


                String notification = toggleNotification.isChecked() ? "Enabled" : "Disabled";

                String result = "Name: " + name +
                        "\nEmail: " + email +
                        "\nGender: " + gender +
                        "\nHobbies: " + hobbies +
                        "\nNotifications: " + notification;

                textResult.setText(result);
            }
        });
    }
}
