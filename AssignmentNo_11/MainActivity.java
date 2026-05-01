package com.example.sqlitedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName;
    Button btnInsert, btnUpdate, btnDelete, btnView;
    TextView tvResult;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        tvResult = findViewById(R.id.tvResult);

        
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS student(id INTEGER PRIMARY KEY, name TEXT)");

    
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();

                if (id.isEmpty() || name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor c = db.rawQuery("SELECT * FROM student WHERE id=?", new String[]{id});

                    if (c.getCount() > 0) {
                        Toast.makeText(MainActivity.this, "ID already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        db.execSQL("INSERT INTO student(id,name) VALUES(?,?)", new Object[]{id, name});
                        Toast.makeText(MainActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();

                        etId.setText("");
                        etName.setText("");
                    }
                    c.close();
                }
            }
        });

        
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();

                if (id.isEmpty() || name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter ID and Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor c = db.rawQuery("SELECT * FROM student WHERE id=?", new String[]{id});

                if (c.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Record not found", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("UPDATE student SET name=? WHERE id=?", new Object[]{name, id});
                    Toast.makeText(MainActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();

                    etId.setText("");
                    etName.setText("");
                }
                c.close();
            }
        });

        
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor c = db.rawQuery("SELECT * FROM student WHERE id=?", new String[]{id});

                if (c.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Record not found", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("DELETE FROM student WHERE id=?", new Object[]{id});
                    Toast.makeText(MainActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();

                    etId.setText("");
                    etName.setText("");
                }
                c.close();
            }
        });

        
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor c = db.rawQuery("SELECT * FROM student", null);

                if (c.getCount() == 0) {
                    tvResult.setText("No Records Found");
                    c.close();
                    return;
                }

                StringBuilder buffer = new StringBuilder();

                while (c.moveToNext()) {
                    buffer.append("ID: ").append(c.getString(0)).append("\n");
                    buffer.append("Name: ").append(c.getString(1)).append("\n\n");
                }

                tvResult.setText(buffer.toString());
                c.close();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
}
