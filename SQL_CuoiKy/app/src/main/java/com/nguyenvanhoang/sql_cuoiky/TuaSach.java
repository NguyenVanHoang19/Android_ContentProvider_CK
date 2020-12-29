package com.nguyenvanhoang.sql_cuoiky;

public class TuaSach {
    private int ma;
    private String tuaSach;

    public TuaSach(int ma, String tuaSach) {
        this.ma = ma;
        this.tuaSach = tuaSach;
    }

    public TuaSach() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTuaSach() {
        return tuaSach;
    }

    public void setTuaSach(String tuaSach) {
        this.tuaSach = tuaSach;
    }

    @Override
    public String toString() {
        return this.tuaSach;
    }
}
