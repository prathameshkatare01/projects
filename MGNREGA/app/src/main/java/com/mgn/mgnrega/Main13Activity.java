package com.mgn.mgnrega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Main13Activity extends AppCompatActivity {
    Button b1;
    private long exit1;
    EditText e1, e2, e3;
    Admin db;
    String prevStarted = "prevStarted1";
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences1 = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences1.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences1.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            Intent i = new Intent(Main13Activity.this, MainActivity.class);
            startActivity(i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        db = new Admin(this);
        b1 = findViewById(R.id.confirmbtn);
        e1 = findViewById(R.id.editText12);
        e2 = findViewById(R.id.editText13);
        e3 = findViewById(R.id.editText15);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (s2.equals(s3)) {
                        Boolean ce = db.checkmail(s1);
                        if (ce == true) {
                            if (db.adminlimit())
                                Toast.makeText(getApplicationContext(), "ONLY ONE ADMIN ALLOWED", Toast.LENGTH_SHORT).show();

                            else {
                                Boolean insert = db.insert(s1, s2);
                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Main13Activity.this, MainActivity.class);
                                    startActivity(i);

                                } else
                                    Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else
                        Toast.makeText(getApplicationContext(), "Both Password doesnt match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
