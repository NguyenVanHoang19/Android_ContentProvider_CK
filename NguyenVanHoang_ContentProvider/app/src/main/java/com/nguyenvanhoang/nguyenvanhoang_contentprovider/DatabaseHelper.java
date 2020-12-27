package com.nguyenvanhoang.nguyenvanhoang_contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "PRODUCT.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PRODUCT(" +
                                "id int primary key,"+
                                "name text,"+
                                "price double,"+
                                "madein text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUCT");
        onCreate(sqLiteDatabase);
    }
    // them san pham
    public int themSanPham(Product product){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",product.getId());
        contentValues.put("name",product.getName());
        contentValues.put("price",product.getPrice());
        contentValues.put("madein",product.getMadein());
        int result = (int)sqLiteDatabase.insert("PRODUCT",null,contentValues);
        return result;
    }
    // liet ke danh sach san pham
    public List<Product> getAllProduct(){
        List<Product> list = new ArrayList<Product>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCT",null);
        if(cursor !=  null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Product product = new Product();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setPrice(cursor.getDouble(2));
            product.setMadein(cursor.getString(3));
            list.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    // xoa san pham theo id
    public int deleteProductById(String idSanPham){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result  = (int)sqLiteDatabase.delete("PRODUCT","id =" + "'" + idSanPham + "'",null);
        sqLiteDatabase.close();
        return result;
    }
    // cap nhat san pham
    public int updateSanPham(Product product){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",product.getId());
        contentValues.put("name",product.getName());
        contentValues.put("price",product.getPrice());
        contentValues.put("madein",product.getMadein());
        int result = (int) sqLiteDatabase.update("PRODUCT",contentValues,"id =" + "'" + product.getId() + "'",null );
        sqLiteDatabase.close();
        return result;
    }
    // tim kiem san pham theo ten san pham
    public List<Product> getSanPhamByTen(String name){
        List<Product> list = new ArrayList<Product>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM PRODUCT WHERE name = " + "'" + name + "'",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Product product = new Product();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setPrice(cursor.getDouble(2));
            product.setMadein(cursor.getString(3));
            list.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
}
