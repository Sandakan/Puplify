package com.adsandakannipunajith.puplify.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.models.ProductModel;

import java.util.ArrayList;

public class ProductDAO {
    private final SQLiteDatabase database;

    public ProductDAO() {
        database = App.getDatabase();
    }

    public ArrayList<ProductModel> getProducts() {
        ArrayList<ProductModel> products = new ArrayList<>();
        Cursor cursor = database.query(ProductModel.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ProductModel product = ProductModel.fromCursor(cursor);
            products.add(product);
        }

        cursor.close();
        return products;

    }

    public ProductModel getProduct(int id) {
        Cursor cursor = database.query(ProductModel.TABLE_NAME, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        ProductModel product = ProductModel.fromCursor(cursor);
        cursor.close();
        return product;
    }

    public ArrayList<ProductModel> searchProducts(String query, String brand, String type, String ageGroup) {
        ArrayList<ProductModel> products = new ArrayList<>();
        Cursor cursor = database.query(ProductModel.TABLE_NAME, null, "name LIKE ? AND brand LIKE ? AND type LIKE ? AND age_group LIKE ?",
                new String[]{"%" + query + "%", "%" + brand + "%", "%" + type + "%", "%" + ageGroup + "%"}, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ProductModel product = ProductModel.fromCursor(cursor);
            products.add(product);
        }
        cursor.close();
        return products;
    }

    public ArrayList<String> getProductBrands() {
        ArrayList<String> brands = new ArrayList<>();
        Cursor cursor = database.query(ProductModel.TABLE_NAME, new String[]{"DISTINCT brand"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            brands.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return brands;
    }

    public ArrayList<String> getProductTypes() {
        ArrayList<String> types = new ArrayList<>();
        Cursor cursor = database.query(ProductModel.TABLE_NAME, new String[]{"DISTINCT type"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            types.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return types;
    }

    public ArrayList<String> getProductAgeGroups() {
        ArrayList<String> ageGroups = new ArrayList<>();
        Cursor cursor = database.query(ProductModel.TABLE_NAME, new String[]{"DISTINCT age_group"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ageGroups.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return ageGroups;
    }
}
