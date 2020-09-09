package com.mgn.mgnrega;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class statedata extends SQLiteOpenHelper{
    public statedata(Context context){
        super(context,"state.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table state(sname text primary key,scode text unique,ssalary text,startdate text,week integer,d text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        db.execSQL("drop table if exists state");
    }
    public Boolean insert(String sname,String scode,String ssalary,String startdate,String d){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("sname",sname);
        contentValues.put("scode",scode);
        contentValues.put("ssalary",ssalary);
        contentValues.put("startdate",startdate);
        contentValues.put("week",0);
        contentValues.put("d",d);
        long ins=db.insert("state",null,contentValues);
        db.close();
        if(ins==-1)
            return false;
        else
            return true;

    }

public String getsalary(){
        String ss;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select ssalary from state",null);
        cursor.moveToFirst();
        ss=cursor.getString(0);
        cursor.close();
        db.close();
        return ss;

}
    public void setsalary(String ss){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ssalary",ss);
        db.update("state",cv,null,null);
        db.close();

    }
    public String getstartdate(){
        String ss;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select startdate from state",null);
        cursor.moveToFirst();
        ss=cursor.getString(0);
        cursor.close();
        db.close();
        return ss;

    }
public void setstartdate(String s){
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put("startdate",s);
    db.update("state",cv,null,null);
    db.close();
}
    public void setweek(int s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("week",s);
        db.update("state",cv,null,null);
        db.close();
    }
    public int getweek(){
        int ss;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select week from state",null);
        cursor.moveToFirst();
        ss=cursor.getInt(0);
        cursor.close();
        db.close();
        return ss;

    }
    public String getd(){
        String ss;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select d from state",null);
        cursor.moveToFirst();
        ss=cursor.getString(0);
        cursor.close();
        db.close();
        return ss;

    }
    public void setd(String s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("d",s);
        db.update("state",cv,null,null);
        db.close();
    }
}
