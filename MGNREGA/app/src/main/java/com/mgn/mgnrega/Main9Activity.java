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

public class Main9Activity extends AppCompatActivity {
    Button b1,b2;
    private long exit1;
    EditText e1;
    DataBase db;
    Database1 db1;
    Database2 db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        b1=findViewById(R.id.remove);
        b2=findViewById(R.id.donebtn);
        e1=findViewById(R.id.editText9);
        db=new DataBase(this);
        db1=new Database1(this);
        db2=new Database2(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                if(s1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Feild empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.checkworker1(s1) && db1.checkworker1(s1) && db2.checkworker1(s1)) {
                        if (db.removeworker(s1) && db1.removeworker(s1) && db2.removeworker(s1))
                            displayToast();
                        else
                            Toast.makeText(getApplicationContext(), "Error removing worker", Toast.LENGTH_SHORT).show();
                    }else   Toast.makeText(getApplicationContext(), "Worker not present", Toast.LENGTH_SHORT).show();
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
    public  void displayToast(){
        Toast.makeText(Main9Activity.this,"Worker successfully removed",Toast.LENGTH_LONG).show();
    }
    public void openActivity8()
    {
        Intent intent=new Intent(this,Main8Activity.class);
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
