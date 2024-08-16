package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

public class UserModel {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserModel(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public static UserModel fromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        return new UserModel(id, firstName, lastName, email);
    }
}
