package com.adsandakannipunajith.puplify.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.DatabaseHelper;
import com.adsandakannipunajith.puplify.Models.UserModel;

public class UserDAO {
    private final SQLiteDatabase db;

    public UserDAO(Context context) {
        db = App.getDatabase();
    }

    // Method to add a new user
    public long addUser(String firstName, String lastName, String email, String password) {
        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("email", email);
        values.put("password", password);

        return db.insert("user", null, values);
    }

    // Method to authenticate a user
    public boolean authenticateUser(String email, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email=? AND password=?", new String[]{email, password});
        boolean result = (cursor != null && cursor.moveToFirst());
        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    // Method to fetch user details
    public UserModel getUser(String email) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email=? LIMIT 1", new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve user data from the cursor
            UserModel model = UserModel.fromCursor(cursor);

            cursor.close();
            return model;
        }

        return null;
    }

    public UserModel getUser(int userId) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE id=? LIMIT 1", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve user data from the cursor
            UserModel model = UserModel.fromCursor(cursor);
            cursor.close();
            return model;
        }

        return null;
    }

    public void updateProfileInformation(int userId, String firstName, String lastName, String email, String password, String address) {
        ContentValues values = new ContentValues();
        if (firstName != null && !firstName.isEmpty()) {
            values.put("first_name", firstName);
        }

        if (lastName != null && !lastName.isEmpty()) {
            values.put("last_name", lastName);
        }

        if (email != null && !email.isEmpty()) {
            values.put("email", email);
        }

        if (address != null && !address.isEmpty()) {
            values.put("address", address);
        }

        if (password != null && !password.isEmpty()) {
            values.put("password", password);
        }
        db.update("user", values, "id=?", new String[]{String.valueOf(userId)});
    }

}
