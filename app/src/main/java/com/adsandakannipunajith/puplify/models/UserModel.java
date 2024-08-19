package com.adsandakannipunajith.puplify.models;

import android.database.Cursor;

public class UserModel {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String accountCreationDate;
    private final String address;

    public UserModel(int id, String firstName, String lastName, String email, String address, String accountCreationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.accountCreationDate = accountCreationDate;
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

    public String getAddress() {
        return address;
    }

    public String getAccountCreationDate() {
        return accountCreationDate;
    }


    public static UserModel fromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        String accountCreationDate = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
        return new UserModel(id, firstName, lastName, email, address, accountCreationDate);
    }
}
