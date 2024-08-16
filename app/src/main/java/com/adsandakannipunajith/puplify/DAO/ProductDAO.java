package com.adsandakannipunajith.puplify.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.Models.ProductModel;

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
}
