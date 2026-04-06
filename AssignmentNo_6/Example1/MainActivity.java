package com.example.inputcontrol;



import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ControlsActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    RatingBar ratingBar;
    ProgressBar progressBar;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        // Link UI
        toggleButton = findViewById(R.id.toggleButton);
        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
        btnShow = findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toggleState = toggleButton.isChecked() ? "ON" : "OFF";
                float rating = ratingBar.getRating();
                int progress = progressBar.getProgress();

                String message = "Toggle: " + toggleState +
                        "\nRating: " + rating +
                        "\nProgress: " + progress + "%";

                AlertDialog.Builder builder = new AlertDialog.Builder(ControlsActivity.this);
                builder.setTitle("User Data");
                builder.setMessage(message);

                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("Cancel", null);

                builder.show();
            }
        });
    }
}