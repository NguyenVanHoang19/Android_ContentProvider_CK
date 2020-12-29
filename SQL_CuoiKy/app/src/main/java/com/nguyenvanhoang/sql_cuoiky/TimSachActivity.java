package com.nguyenvanhoang.sql_cuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TimSachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_sach);
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
                startActivity(new Intent(TimSachActivity.this,ThemSachActivity.class));
                break;
            case R.id.mnTimSach:
                startActivity(new Intent(TimSachActivity.this,TimSachActivity.class));
                break;
            case R.id.mnThoat:
                AlertDialog.Builder builder = new AlertDialog.Builder(TimSachActivity.this);
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