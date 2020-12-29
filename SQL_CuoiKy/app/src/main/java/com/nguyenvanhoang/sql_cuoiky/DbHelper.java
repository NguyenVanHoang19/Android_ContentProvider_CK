package com.nguyenvanhoang.sql_cuoiky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "db.sach", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TUASACH("+
                                "ma integer primary key,"+
                                "tuasach text"+
                                ")");
        sqLiteDatabase.execSQL("CREATE TABLE SACH("+
                                "ma integer primary key,"+
                                "maTuaSach integer,"+
                                "ten text,"+
                                "tacgia,"+
                                "FOREIGN KEY (maTuaSach) REFERENCES TUASACH(ma)"+
                                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TUASACH");
                onCreate(sqLiteDatabase);
    }
    public int themTuaSach(TuaSach tuaSach){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma",tuaSach.getMa());
        contentValues.put("tuasach",tuaSach.getTuaSach());
        int result = (int) sqLiteDatabase.insert("TUASACH",null,contentValues);
        return result;
    }
    public int themSach(Sach sach){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma",sach.getMaSach());
        contentValues.put("maTuaSach",sach.getMaTuaSach());
        contentValues.put("ten",sach.getTenSach());
        contentValues.put("tacgia",sach.getTenTacGia());
        int result  = (int)sqLiteDatabase.insert("SACH",null,contentValues);
        return result;
    }
    public int xoaSach(int ma){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result =  (int)sqLiteDatabase.delete("SACH","ma= " + "'" + ma + "'",null);
        return result;
    }
    public int capNhatSach(Sach sach){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma",sach.getMaSach());
        contentValues.put("ten",sach.getTenSach());
        contentValues.put("tacgia",sach.getTenTacGia());
        int result = (int) sqLiteDatabase.update("SACH",contentValues,"id= " + "'" + sach.getMaSach()+ "'",null);
        return result;
    }
    public List<TuaSach> getALLTuaSach(){
        List<TuaSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM TUASACH",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            TuaSach tuaSach = new TuaSach();
            tuaSach.setMa(cursor.getInt(0));
            tuaSach.setTuaSach(cursor.getString(1));
            list.add(tuaSach);
            cursor.moveToNext();

        }
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

    public List<Sach> getALLSach(){
        List<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM SACH",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Sach sach = new Sach();
            sach.setMaSach(cursor.getInt(0));
            sach.setTenSach(cursor.getString(1));
            sach.setTenTacGia(cursor.getString(2));
            list.add(sach);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }
    public Sach getSachByMaSach(int ma){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Sach sach = new Sach();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM SACH WHERE ma = " + "'" +ma+"'",null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            sach.setMaSach(cursor.getInt(0));
            sach.setTenSach(cursor.getString(1));
            sach.setTenTacGia(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return sach;
    }
}
