package com.mgn.mgnrega;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.File;

public class Main17Activity extends AppCompatActivity {
    EditText e1;
    Button b1,b2;
    String s,s1;
    Admin a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        Intent intent=getIntent();
        a=new Admin(this);
        s1=intent.getStringExtra("status");
        e1=(EditText)findViewById(R.id.datedata);
        b1=(Button)findViewById(R.id.donedata);
        b2=(Button)findViewById(R.id.back);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s1.equals("s")) {
                    s = e1.getText().toString();
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/muster_roll/" + s + ".xls");
                    if (file.exists()) {
                        Uri path = Uri.fromFile(file);
                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                        pdfIntent.setDataAndType(path, "application/vnd.ms-excel");
                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            startActivity(pdfIntent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "No                                                                      Application available to viewExcel",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                } else if (s1.equals("g")) {

                        sendTestEmail();

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openActivity8();
            }
        });

    }

    private void sendTestEmail() {
String b=a.getmail();
System.out.println(b);
        BackgroundMail.newBuilder(this)
                .withUsername("prathamesh2891999@gmail.com")
                .withPassword("patil@289")
                .withMailto("prathamesh2891999@gmail.com")
                .withSubject("Attendance of workers")
                .withBody("This excel sheet contains attendance data of workers of week starting from "+s)
                .withAttachments(Environment.getExternalStorageDirectory().getPath() + "/muster_roll/"+s+".xls")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                        System.out.println("Email sent");
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                        System.out.println("Email not sent");
                    }
                })
                .send();
    }
    public void openActivity8() {
        Intent intent = new Intent(this, Main8Activity.class);
        startActivity(intent);
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
