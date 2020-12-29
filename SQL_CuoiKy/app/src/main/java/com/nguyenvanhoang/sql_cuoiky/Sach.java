package com.nguyenvanhoang.sql_cuoiky;

public class Sach {
    private int maSach;
    private int maTuaSach;
    private String tenSach;
    private String tenTacGia;

    public Sach(int maSach, int maTuaSach, String tenSach, String tenTacGia) {
        this.maSach = maSach;
        this.maTuaSach = maTuaSach;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
    }

    public int getMaTuaSach() {
        return maTuaSach;
    }

    public void setMaTuaSach(int maTuaSach) {
        this.maTuaSach = maTuaSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public Sach() {
    }
}
