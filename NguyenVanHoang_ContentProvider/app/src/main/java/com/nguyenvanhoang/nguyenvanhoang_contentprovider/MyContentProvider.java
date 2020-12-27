package com.nguyenvanhoang.nguyenvanhoang_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    static final String AUTHORITY = "com.nguyenvanhoang.nguyenvanhoang_contentprovider";
  //  static final String CONTENT_PROVIDER = "contentprovider";
//    static final String URL ="content://" + AUTHORITY + "/" + CONTENT_PROVIDER;
  static final String URL ="content://" + AUTHORITY + "/" + "PRODUCT";
    static final Uri CONTENT_URI  = Uri.parse(URL);

    static final String PRODUCT_TABLE = "PRODUCT";
    private SQLiteDatabase db ;
    static final int ONE = 1;
    static final int ALL = 2;
    static final UriMatcher uri_matcher;
    static {
        uri_matcher = new UriMatcher(UriMatcher.NO_MATCH);
        uri_matcher.addURI(AUTHORITY,PRODUCT_TABLE ,ALL);
        uri_matcher.addURI(AUTHORITY,PRODUCT_TABLE + "/#",ONE);
    }
    public static HashMap<String, String> PROJECTION_MAP;
    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db =databaseHelper.getWritableDatabase();
        if(db == null)
            return false;
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(PRODUCT_TABLE);
        switch (uri_matcher.match(uri)){
            case ALL:
                sqLiteQueryBuilder.setProjectionMap(PROJECTION_MAP);
                break;
            case ONE:
                sqLiteQueryBuilder.appendWhere("id =" + uri.getPathSegments().get(1));
                break;
        }
        if(s1 == null || s1 == ""){
            s1 = "name";
        }
        Cursor cursor =sqLiteQueryBuilder.query(db,strings,s,strings1,null,null,s1);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long number_row = db.insert(PRODUCT_TABLE,"",contentValues);
        if(number_row > 0){
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI,number_row);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }
        throw new SQLException("Fail to add record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int number_row =  db.delete(PRODUCT_TABLE,"id =" + "'" + Integer.parseInt(s) + "'",null);
            getContext().getContentResolver().notifyChange(uri,null);
        return number_row;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int number_row = db.update(PRODUCT_TABLE,contentValues,"id =" + "'" + contentValues.get("id") + "'",null);
        getContext().getContentResolver().notifyChange(uri,null);
        return number_row;
    }
}
