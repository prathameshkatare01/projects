package com.mgn.mgnrega;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;

public class DataBase extends SQLiteOpenHelper{
    public DataBase(Context context){
        super(context,"worker.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table worker(username text not null ,adharno text not null,age integer not null,jobcardno text primary key)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        db.execSQL("drop table if exists worker");
    }
    public Boolean insert(String username,String adharno,int age,String jobcardno){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("adharno",adharno);
        contentValues.put("age",age);
        contentValues.put("jobcardno",jobcardno);
        long ins=db.insert("worker",null,contentValues);
        db.close();
        if(ins==-1)
            return false;
        else
            return true;
    }
    public Boolean checkworker1(String jobcardno){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from worker where jobcardno=?",new String[]{jobcardno});
        if(cursor.getCount()>0){cursor.close();db.close();return true;}
        else {cursor.close();db.close();return false;}
    }
    public Boolean removeworker(String jobcardno){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete("worker","jobcardno=?",new String[]{jobcardno});
        Cursor cursor=db.rawQuery("Select * from worker where jobcardno=?",new String[]{jobcardno});
        if(cursor.getCount()>0) {
         cursor.close();
         db.close();
         return false;
        }
        else {
            cursor.close();
            db.close();
            return true;
        }
    }
    public String getname(String jb){
        String u;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select username from worker where jobcardno=?",new String[]{jb});
        u=cursor.getString(0);
        cursor.close();
        db.close();
        return u;
    }


}






















/*import java.time.LocalDateTime; // Import the LocalDateTime class
        import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class MyClass {
    public static void main(String[] args) {
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("After formatting: " + formattedDate);
    }
}*/