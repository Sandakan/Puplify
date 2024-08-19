package com.adsandakannipunajith.puplify.models;

import android.database.Cursor;

public class ReviewModel {

    private final int id;
    private final String date;
    private final String comment;
    private final int rating;
    private final int userId;
    public static String TABLE_NAME = "review";

    public ReviewModel(int id, String date, String comment, int rating, int userId) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.rating = rating;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }


    public int getUserId() {
        return userId;
    }

    public static ReviewModel fromCursor(Cursor cursor) {
        return new ReviewModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                cursor.getString(cursor.getColumnIndexOrThrow("comment")),
                cursor.getInt(cursor.getColumnIndexOrThrow("rating")),
                cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
        );
    }
}
