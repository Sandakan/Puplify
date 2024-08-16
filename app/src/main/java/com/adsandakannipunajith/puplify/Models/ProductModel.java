package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

import com.adsandakannipunajith.puplify.R;

public class ProductModel {

    private final int id;
    private final String name;
    private final String description;
    private final double price;
    private final String brand;
    private final String type;
    private final String ageGroup;
    private final int image;

    public static String TABLE_NAME = "product";


    public ProductModel(int id, String name, String description, double price, String brand, String type, String ageGroup, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.type = type;
        this.ageGroup = ageGroup;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public int getImage() {
        return image;
    }

    public static ProductModel fromCursor(Cursor cursor) {
        return new ProductModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                cursor.getString(cursor.getColumnIndexOrThrow("brand")),
                cursor.getString(cursor.getColumnIndexOrThrow("type")),
                cursor.getString(cursor.getColumnIndexOrThrow("age_group"))
                , cursor.getInt(cursor.getColumnIndexOrThrow("image"))
        );

    }
}
