package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

import com.adsandakannipunajith.puplify.App;

import java.util.ArrayList;

public class CartModel {

    private int id;
    private int userId;
    private String status;

    public CartModel(int id, int userId, String status) {
        this.id = id;
        this.userId = userId;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public static CartModel fromCursor(Cursor cursor) {
        int userId = App.getSharedPreferences().getInt("user_id", 0);
        return new CartModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                userId,
                cursor.getString(cursor.getColumnIndexOrThrow("status"))
        );
    }
}
