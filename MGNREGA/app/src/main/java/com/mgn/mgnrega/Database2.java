package com.mgn.mgnrega;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;

public class Database2 extends SQLiteOpenHelper {
    public Database2(Context context) {
        super(context, "worker3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table worker3(jobcardno text primary key,username text not null ,day1 integer default null,day2 integer default null,day3 integer default null,day4 integer default null,day5 integer default null,day6 integer default null,day7 integer default null,dayspresent integer default null,salary decimal(8,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("drop table if exists worker3");
    }

    public Boolean insert(String username, String jobcardno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jobcardno", jobcardno);
        cv.put("username", username);
        cv.put("day1",0);
        cv.put("day2",0);
        cv.put("day3",0);
        cv.put("day4",0);
        cv.put("day5",0);
        cv.put("day6",0);
        cv.put("day7",0);
        cv.put("dayspresent",0);
        cv.put("salary",0.0);
        long ins = db.insert("worker3", null, cv);
        db.close();
        if (ins == -1)
            return false;
        else
            return true;
    }


    public Boolean checkworker1(String jobcardno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from worker3 where jobcardno=?", new String[]{jobcardno});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public Boolean removeworker(String jobcardno) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("worker3", "jobcardno=?", new String[]{jobcardno});
        Cursor cursor = db.rawQuery("Select * from worker3 where jobcardno=?", new String[]{jobcardno});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    public Cursor displayworkers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("Select jobcardno,username,salary from worker3", null);

        return data;
    }

    public double displaysalary(String jobcardno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select salary from worker3 where jobcardno=?", new String[]{jobcardno});
        if (cursor != null && cursor.moveToFirst()) {
            double b = cursor.getDouble(0);
            cursor.close();
            db.close();
            return b;
        } else {
            cursor.close();
            db.close();
            return 0.0;
        }
    }

    public int displayownstatus(String jobcardno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select dayspresent from worker3 where jobcardno=?", new String[]{jobcardno});
        if (cursor != null && cursor.moveToFirst()) {
            int a = cursor.getInt(0);
            cursor.close();
            db.close();
            return a;
        } else {
            cursor.close();
            db.close();
            return 0;
        }
    }

    public void setday1(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day1", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();


    }
    public void setday2(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day2", d);
        db.update("worker3", cv,"jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setday3(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day3", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setday4(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day4", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setday5(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day5", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setday6(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day6", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setday7(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day7", d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setdaypresent(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dayspresent",d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public void setsalary(double d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("salary",d);
        db.update("worker3", cv, "jobcardno=?", new String[]{jb});
        db.close();
    }
    public int getdayspresent(String jb) {
        int t;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select dayspresent from worker3 where jobcardno=?", new String[]{jb});
        cursor.moveToFirst();
        t=cursor.getInt(0);
        cursor.close();
        db.close();
        return t;

    }
    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("day1",0);
        cv.put("day2",0);
        cv.put("day3",0);
        cv.put("day4",0);
        cv.put("day5",0);
        cv.put("day6",0);
        cv.put("day7",0);
        cv.put("dayspresent",0);
        cv.put("salary",0.0);
        db.update("worker3",cv,null,null);
        db.close();
    }

}




