package com.mgn.mgnrega;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Admin extends SQLiteOpenHelper {
    public Admin(Context context){
        super(context,"admin.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table adminlogin(email text primary key,pass text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        db.execSQL("drop table if exists adminlogin");
    }
    public Boolean insert(String email,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("pass",pass);
        long ins=db.insert("adminlogin",null,contentValues);
        db.close();
        if(ins==-1)
            return false;
        else
            return true;

    }
    public Boolean checkmail(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from adminlogin where email=?",new String[]{email});
        if(cursor.getCount()>0){
            cursor.close();db.close();return false;
        }
        else{
            cursor.close();db.close();return true;
        }
    }
    public Boolean adminlimit()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select email from adminlogin",null);
        if(cursor.getCount()>=1) {
            cursor.close();db.close();return true;
        }
        else{
            cursor.close();db.close();return false;
        }
    }
    public Boolean ep(String email,String pass){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from adminlogin where email=? and pass=?",new String[]{email,pass});
        if(cursor.getCount()>0){
            cursor.close();db.close();return true;
        }
        else{
            cursor.close();
            db.close();
            return false;

        }
   }
   public String getmail(){
        String e;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select email from adminlogin",null);
        cursor.moveToFirst();
        e=cursor.getString(0);
        cursor.close();
        db.close();
        return e;
   }
}
