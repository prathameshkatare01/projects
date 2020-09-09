package com.mgn.mgnrega;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;

public class Database1 extends SQLiteOpenHelper {
    public Database1(Context context) {
        super(context, "worker1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table worker1(jobcardno text primary key,jbint integer,checkin text,day integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("drop table if exists worker1");
    }

    public Boolean insert(String jobcardno,int jbint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("jobcardno", jobcardno);
        contentValues.put("jbint",jbint);
        contentValues.put("day", 0);
        long ins = db.insert("worker1", null, contentValues);
        db.close();
        if (ins == -1)
            return false;
        else
            return true;
    }


    public Boolean checkworker1(String jobcardno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from worker1 where jobcardno=?", new String[]{jobcardno});
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

        db.delete("worker1", "jobcardno=?", new String[]{jobcardno});
        Cursor cursor = db.rawQuery("Select * from worker1 where jobcardno=?", new String[]{jobcardno});
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

    public int getday(String jb) {
        int d;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select day from worker1 where jobcardno=?", new String[]{jb});
        cursor.moveToFirst();
        d = cursor.getInt(0);
        cursor.close();
        db.close();
        return d;

    }

    public void setday(int d,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("day", d);
        db.update("worker1", cv, "jobcardno=?", new String[]{jb});
        db.close();

    }
    public void setcheckin(String ci,String jb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("checkin", ci);
        db.update("worker1",cv,"jobcardno=?", new String[]{jb});
        db.close();

    }
    public String getcheckin(String jb) {
        String t;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select checkin from worker1 where jobcardno=?", new String[]{jb});
        cursor.moveToFirst();
        t=cursor.getString(0);
        cursor.close();
        db.close();
        return t;

    }
    public String jb(int jbint) {
        String d;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select jobcardno from worker1 where jbint=?", new String[]{String.valueOf(jbint)});
        cursor.moveToFirst();
        d=cursor.getString(0);
        cursor.close();
        db.close();
        return d;

    }

}






