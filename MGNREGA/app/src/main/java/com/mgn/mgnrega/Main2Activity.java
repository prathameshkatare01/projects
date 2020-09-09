package com.mgn.mgnrega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Button b1;
    private  long exit1;
    EditText e1,e2,e3,e4;
    DataBase db;
    Database1 db1;
    Database2 db2;
    int i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b1=findViewById(R.id.bt11);
        e1=findViewById(R.id.editText10);
        e2=findViewById(R.id.editText2);
        e3=findViewById(R.id.editText3);
        e4=findViewById(R.id.editText14);
        db=new DataBase(this);
        db1=new Database1(this);
        db2=new Database2(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String s4 = e4.getText().toString();
                String[] ts = s4.split("-");
                if (ts.length == 5) {
                    String[] ts1 = ts[4].split("/");
                    if (ts1.length == 2) {
                        i1 = Integer.parseInt(String.format("%s%s%s", ts[3], ts1[0], ts1[1]));
                        if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty())

                            Toast.makeText(getApplicationContext(), "Some Fields are empty", Toast.LENGTH_SHORT).show();
                        else {
                            if (Integer.parseInt(s3) < 18) {
                                Toast.makeText(getApplicationContext(), "Age should be 18 and above", Toast.LENGTH_SHORT).show();
                            } else {
                                if (db.insert(s1, s2, Integer.parseInt(s3), s4) && db1.insert(s4, i1) && db2.insert(s1, s4)) {
                                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                                    openActivity14();

                                } else
                                    Toast.makeText(getApplicationContext(), "Registeration unsuccesful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Job cardno format invalid", Toast.LENGTH_SHORT).show();
                }
                else
             Toast.makeText(getApplicationContext(), "Job cardno format invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openActivity14(){
        Intent intent=new Intent(this,Main14Activity.class);
        intent.putExtra("jbint",i1);
        startActivity(intent);
    }
    public void openActivity1(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
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

}
