package com.nguyenvanhoang.sql_cuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThemSachActivity extends AppCompatActivity {
    private EditText edtMaSach,edtTenSach;
    private Button btnThoat,btnLuu;
    private Spinner spinnerTacGia,spinnerTuaSach;
    private DbHelper dbHelper ;
    private List<Sach> sachList = new ArrayList<>();
    private List<TuaSach> tuaSachList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        connectView();
        dbHelper = new DbHelper(this);
        //themTuaSach();
        List<TuaSach> tuaSaches = dbHelper.getALLTuaSach();
        System.out.println(tuaSaches.size());
        if(tuaSaches.size() > 0){
            tuaSachList=  tuaSaches;
        }
        ArrayAdapter<TuaSach> tuaSachArrayAdapter = new ArrayAdapter<>(ThemSachActivity.this
                ,android.R.layout.simple_list_item_1,tuaSachList);
        spinnerTuaSach.setAdapter(tuaSachArrayAdapter);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRong()){
                    int maSach = Integer.parseInt(edtMaSach.getText().toString().trim());
                    int idTuaSach = spinnerTuaSach.getSelectedItemPosition() + 1 ;
                    String tenSach = edtTenSach.getText().toString().trim();
                    String tenTacGia = spinnerTacGia.getSelectedItem().toString();
                    Sach sach = new Sach();
                    sach.setMaSach(maSach);
                    sach.setMaTuaSach(idTuaSach);
                    sach.setTenSach(tenSach);
                    sach.setTenTacGia(tenTacGia);
                    if(dbHelper.themSach(sach) > 0)
                        Toast.makeText(ThemSachActivity.this,"Đã thêm thành công sách",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ThemSachActivity.this,"Lỗi khi thêm sách",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(ThemSachActivity.this);
                builder.setTitle("Thông báo!!!");
                builder.setMessage("Bạn có chắc chắn muốn thoát???");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
    public boolean checkRong(){
        if(edtMaSach.getText().toString().trim().equals("")){
            Toast.makeText(ThemSachActivity.this,"Mã sách không được rỗng!!! ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(edtTenSach.getText().toString().trim().equals("")){
            Toast.makeText(ThemSachActivity.this,"Tên sách không được rỗng!!! ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(spinnerTuaSach.getSelectedItem().toString().trim().equals("")){
            Toast.makeText(ThemSachActivity.this,"Vui lòng chọn tựa sách!!! ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(spinnerTacGia.getSelectedItem().toString().trim().equals("")){
            Toast.makeText(ThemSachActivity.this,"Vui lòng chọn tác giả!!! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void connectView(){
        edtMaSach = (EditText) findViewById(R.id.edtMaSach);
        edtTenSach = (EditText) findViewById(R.id.edtTenSach);
        btnThoat = (Button) findViewById(R.id.btnThoat);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        spinnerTacGia = (Spinner) findViewById(R.id.spnTacGia);
        spinnerTuaSach = (Spinner) findViewById(R.id.spnTuaSach);
    }
    public void themTuaSach(){
        dbHelper.themTuaSach(new TuaSach(1,"Sách anh văn"));
        dbHelper.themTuaSach(new TuaSach(2,"Sách âm nhạc"));
        dbHelper.themTuaSach(new TuaSach(3,"Sách mỹ thuật"));
        dbHelper.themTuaSach(new TuaSach(4,"Sách kinh tế"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  =item.getItemId();
        switch (id){
            case R.id.mnThemSach:
                startActivity(new Intent(ThemSachActivity.this,ThemSachActivity.class));
                break;
            case R.id.mnTimSach:
                startActivity(new Intent(ThemSachActivity.this,TimSachActivity.class));
                break;
            case R.id.mnThoat:
                AlertDialog.Builder builder = new AlertDialog.Builder(ThemSachActivity.this);
                builder.setTitle("Xác nhận thoát???");
                builder.setMessage("Bạn có chăc chắn muốn thoát?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}