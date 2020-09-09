package com.mgn.mgnrega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.File;

public class Main16Activity extends AppCompatActivity {
Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main16);
        b1=(Button)findViewById(R.id.salarychange);
        b2=(Button)findViewById(R.id.seedata1);
        b3=(Button)findViewById(R.id.maildata);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openActivity11();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity17("s");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity17("g");
            }
        });

    }

    private void openActivity17(String p) {
        Intent intent = new Intent(this, Main17Activity.class);
        intent.putExtra("status",p);
        startActivity(intent);
    }

    public void openActivity11() {
        Intent intent = new Intent(this, Main11Activity.class);
        startActivity(intent);
    }
}
