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

public  class Main4Activity extends AppCompatActivity {
    Button b1;
    private long exit1;
    EditText e1;
    Database2 db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        b1=findViewById(R.id.bt41);
        e1=findViewById(R.id.editText5);
        db=new Database2(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                if(s1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Empty field",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.checkworker1(s1)) {
                        int a = db.displayownstatus(s1);
                        double s = db.displaysalary(s1);
                        if (a >= 0)
                            openActivity5(a, s);
                        else
                            Toast.makeText(getApplicationContext(), "Error in display status", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Worker not present", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void openActivity5(int a,double s){
        Intent intent=new Intent(this,Main5Activity.class);
        Bundle bundle=new Bundle();
        bundle.putString("Dayspresent",String.valueOf(a));
        bundle.putString("Salary",String.valueOf(s));
        intent.putExtras(bundle);
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
