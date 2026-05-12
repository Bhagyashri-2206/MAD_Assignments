package com.example.aquaritual.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaritual.R;
import com.example.aquaritual.utils.DeviceIdUtil;
import com.google.firebase.database.*;

public class SettingActivity extends AppCompatActivity {

    TextView txtGender, txtWeight, txtWakeup, txtBedtime, txtTarget, txtInterval;

    DatabaseReference settingsRef;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        txtGender = findViewById(R.id.txtGender);
        txtWeight = findViewById(R.id.txtWeight);
        txtWakeup = findViewById(R.id.txtWakeup);
        txtBedtime = findViewById(R.id.txtBedtime);
        txtTarget = findViewById(R.id.txtTarget);
        txtInterval = findViewById(R.id.txtInterval);

        deviceId = DeviceIdUtil.getDeviceId(this);

        settingsRef = FirebaseDatabase.getInstance()
                .getReference("settings")
                .child(deviceId);

        loadData();

        txtGender.setOnClickListener(v -> showEditDialog("Gender", "gender"));
        txtWeight.setOnClickListener(v -> showEditDialog("Weight", "weight"));
        txtWakeup.setOnClickListener(v -> showEditDialog("Wake Time", "wakeup"));
        txtBedtime.setOnClickListener(v -> showEditDialog("Bed Time", "bedtime"));
        txtTarget.setOnClickListener(v -> showEditDialog("Target", "target"));
        txtInterval.setOnClickListener(v -> showEditDialog("Interval", "interval"));
    }

    private void loadData() {
        settingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {

                if (!snap.exists()) return;

                txtGender.setText("Gender: " + get(snap,"gender"));
                txtWeight.setText("Weight: " + get(snap,"weight")+" kg");
                txtWakeup.setText("Wake-up: " + get(snap,"wakeup"));
                txtBedtime.setText("Bed Time: " + get(snap,"bedtime"));
                txtTarget.setText("Target: " + get(snap,"target")+" ml");
                txtInterval.setText("Interval: " + get(snap,"interval")+" min");
            }

            @Override public void onCancelled(DatabaseError error) {}
        });
    }

    private String get(DataSnapshot s,String k){
        Object v=s.child(k).getValue();
        return v!=null?v.toString():"--";
    }

    private void showEditDialog(String title,String key){
        EditText input=new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Save",(d,w)->{
                    String val=input.getText().toString().trim();
                    if(!val.isEmpty()){
                        settingsRef.child(key).setValue(val);
                        Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
}