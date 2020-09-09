package com.mgn.mgnrega;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.time.*;
import java.util.*;
import com.ajts.androidmads.library.SQLiteToExcel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4;
    private long exit1;
    private ActionBar actionBar;
    statedata db;
    Database1 db1;
    Database2 db2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadlocale();
        setContentView(R.layout.activity_main);
        db1=new Database1(this);
        db=new statedata(this);
        db2=new Database2(this);
        b1=findViewById(R.id.adminbt);
        b2=findViewById(R.id.workerbtn);
        b3=findViewById(R.id.languagebtn);
        b4=findViewById(R.id.exit);
        String dd=db.getd();
        System.out.println(db.getweek());
        String []ss=dd.split("-");
        LocalDate dat=LocalDate.of(Integer.parseInt(ss[2]),Integer.parseInt(ss[1]),Integer.parseInt(ss[0]));
        LocalDate n=LocalDate.now();
        Period dif=Period.between(dat,n);
        int difDays=dif.getDays();
        if(difDays==1) {
            int x=db.getweek();
            System.out.println(String.valueOf(x)+"########");
            if(x==7)
            {
                x=0;
            }
            else {
                x++;
            }
            db.setweek(x);
            String dt=n.toString();
            String[] dt1=dt.split("-");
            db.setd(dt1[2]+"-"+dt1[1]+"-"+dt1[0]);

        }
        String d1=db.getstartdate();
        String d = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String []s1=d1.split("-");
        String []s2=d.split("-");
        LocalDate date=LocalDate.of(Integer.parseInt(s1[2]),Integer.parseInt(s1[1]),Integer.parseInt(s1[0]));
        LocalDate now=LocalDate.now();
        Period diff=Period.between(date,now);
        int di=diff.getDays();
        if(di==7)
        {
            store(d1);
            db.setstartdate(d);
           // db2.reset();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity7();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity6();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlangdialog();
            }
        });

     b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity();
            }
        });

    }



    public void openActivity7(){
        Intent intent=new Intent(this,Main7Activity.class);
        startActivity(intent);
    }
    public void openActivity6(){
        Intent intent=new Intent(this,Main6Activity.class);
        startActivity(intent);
    }
    public void openActivity1(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openlangdialog(){
        final String[] listitems={"ENGLISH","हिंदी","मराठी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listitems, -1, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    setLocale("en");
                    recreate();

                }
                else if(i==1){
                    setLocale("hi");
                    recreate();

                }
                else if(i==2){
                    setLocale("mr");
                    recreate();

                }
                 dialog.dismiss();

            }
        });
        AlertDialog mDialog= mBuilder.create();
        mDialog.show();
    }
    private void setLocale(String Lang)
    {
        Locale locale=new Locale(Lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My Lang",Lang);
        editor.apply();
    }
    public void loadlocale(){

        SharedPreferences prefs= getSharedPreferences("Settings",MODE_PRIVATE);
        String language=prefs.getString("My Lang","");
        setLocale(language);
    }
    public void onBackPressed(){

        if(exit1 + 2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            finishAffinity();

        }
        else{
            Toast.makeText(getBaseContext(),"PRESS BACK AGAIN",Toast.LENGTH_SHORT).show();
        }
        exit1=System.currentTimeMillis();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.home){
            openActivity1();
        }
        if(id==R.id.exit){
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
public void store(String d){
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/muster_roll/";
    File file = new File(directory_path);
    if (!file.exists()) {
        file.mkdirs();
    }
    SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "worker3.db", directory_path);
    sqliteToExcel.exportAllTables(d+".xls", new SQLiteToExcel.ExportListener() {
        @Override
        public void onStart() {
            Toast.makeText(getApplicationContext(),"Saving last weeks data",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompleted(String filePath) {

            Toast.makeText(getApplicationContext(),"Starts new week data is reset",Toast.LENGTH_LONG).show();
            System.out.println("completed");
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }
    });

}
}
