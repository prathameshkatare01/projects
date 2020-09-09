package com.mgn.mgnrega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main10Activity extends AppCompatActivity {
    Button b1;
    private  long exit1;
    ScrollView s1;
    Database2 db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        db=new Database2(this);
        s1=findViewById(R.id.scroll1);
        ArrayList<String> workerno=new ArrayList<String>();
        ArrayList<String> workername=new ArrayList<String>();
        ArrayList<String> workersalary=new ArrayList<String>();
        Cursor data=db.displayworkers();
        if(data.getCount()==0){
            Toast.makeText(getApplicationContext(),"No workers",Toast.LENGTH_SHORT).show();}
        else{
            LinearLayout l=new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);

            s1.addView(l);
            int i=0;
            while (data.moveToNext()){
                workerno.add(data.getString(0));
                workername.add(data.getString(1));
                workersalary.add(data.getString(2));
                TextView t=new TextView(this);
                t.setText("\n\nJob cardno:-  "+workerno.get(i)+"\n"+"Name:-  "+workername.get(i)+"\n"+"Salary(Rs):-  "+workersalary.get(i)+"\n\n"+"****************************************\n");
                l.addView(t);
                i++;
            }
            data.close();
            db.close();
        }

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
