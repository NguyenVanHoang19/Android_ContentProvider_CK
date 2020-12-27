package com.nguyenvanhoang.nguyenvanhoang_contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String uri ="content://com.nguyenvanhoang.nguyenvanhoang_contentprovider/PRODUCT";
    private EditText edtId,edtName,edtPrice,edtMadein;
    private Button btnThem,btnXoa,btnCapNhat,btnTimKiem;
    private ListView lvDanhSachSanPham;
    private List<Product> productList = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
        setListProduct();
        final ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(MainActivity.this,android.R.layout.simple_list_item_1,productList);
        lvDanhSachSanPham.setAdapter(adapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                if(checkRong()){
                    if(!edtPrice.getText().toString().matches("^[0-9].*")){
                        Toast.makeText(MainActivity.this,"Giá sản phẩm phải là số! ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        contentValues.put("id",Integer.parseInt(edtId.getText().toString()));
                        contentValues.put("name",edtName.getText().toString());
                        contentValues.put("price",Double.parseDouble(edtPrice.getText().toString().trim()));
                        contentValues.put("madein", edtMadein.getText().toString().trim());
                        Uri uriProduct = Uri.parse(uri);
                        Uri insert_uri = getContentResolver().insert(uriProduct,contentValues);
                        setListProduct();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Thêm thành công 1 product! ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtId.getText().toString().trim().equals("")){
                    Uri uriProduct =  Uri.parse(uri);
                    int delete_uri = getContentResolver().delete(uriProduct,edtId.getText().toString().trim(),null);
                    setListProduct();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Xóa thành công sản phẩm " + edtId.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                if(checkRong()){
                    if(!edtPrice.getText().toString().matches("^[0-9].*")){
                        Toast.makeText(MainActivity.this,"Giá sản phẩm phải là số! ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        contentValues.put("id",Integer.parseInt(edtId.getText().toString()));
                        contentValues.put("name",edtName.getText().toString());
                        contentValues.put("price",Double.parseDouble(edtPrice.getText().toString().trim()));
                        contentValues.put("madein", edtMadein.getText().toString().trim());
                        Uri uriProduct = Uri.parse(uri);
                        if(getContentResolver().update(uriProduct,contentValues,null,null) > 0 ){
                            setListProduct();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this,"Cập nhật thành công sản phẩm! ",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtId.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this,"Nhập vào id sản phẩm để tìm kiếm!",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    productList.clear();
                    Uri uriProduct = Uri.parse("content://com.nguyenvanhoang.nguyenvanhoang_contentprovider/PRODUCT/" + Integer.parseInt(edtId.getText().toString()) );
                    Cursor cursor = getContentResolver().query(uriProduct,null,null,null,"name");
                    if(cursor != null){
                        cursor.moveToFirst();
                    }
                    while (!cursor.isAfterLast()){
                        productList.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getString(3)));
                        cursor.moveToNext();
                    }

                    adapter.notifyDataSetChanged();
                    cursor.close();
                }
            }
        });
    }
    public boolean checkRong(){
        if(edtId.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity.this,"Mã sản phẩm không được rỗng!" , Toast.LENGTH_LONG).show();
            return false;
        }
        else if(edtName.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity.this,"Tên sản phẩm không được rỗng! " ,Toast.LENGTH_LONG).show();
            return false;
        }
        else if(edtPrice.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity.this,"Giá sản phẩm không được rỗng! ",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(edtMadein.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity.this,"Madein không được bỏ trống!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void connectView(){
        edtId =  (EditText) findViewById(R.id.edtId);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtMadein = (EditText) findViewById(R.id.edtMadein);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        lvDanhSachSanPham = (ListView) findViewById(R.id.lvDanhSachSanPham);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.themSanPham : break;
            case R.id.danhSachSanPham :break;
            case R.id.thoat:break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setListProduct(){
        productList.clear();
        Uri uri_product =  Uri.parse(uri);
        Cursor cursor = getContentResolver().query(uri_product,null,null,null,"name");
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            productList.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
    }
}