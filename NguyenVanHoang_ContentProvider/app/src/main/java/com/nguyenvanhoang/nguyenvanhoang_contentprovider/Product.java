package com.nguyenvanhoang.nguyenvanhoang_contentprovider;

public class Product {
    private int id;
    private String name;
    private double price;
    private String madein;

    public Product(int id, String name, double price, String madein) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.madein = madein;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMadein() {
        return madein;
    }

    public void setMadein(String madein) {
        this.madein = madein;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Mã sản phẩm:  "+this.id + " - Tên sản phẩm: " + this.name;
    }
}
