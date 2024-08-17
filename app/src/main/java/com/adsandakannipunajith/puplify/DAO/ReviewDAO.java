package com.adsandakannipunajith.puplify.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.Models.ReviewModel;

import java.util.ArrayList;

public class ReviewDAO {
    private final SQLiteDatabase database;
    private final int product_id;

    public ReviewDAO(int product_id) {
        database = App.getDatabase();
        this.product_id = product_id;
    }


    public long addReview(String review, double rating) {
        int userId = App.getSharedPreferences().getInt("user_id", 0);
        ContentValues values = new ContentValues();
        values.put("product_id", product_id);
        values.put("comment", review);
        values.put("rating", rating);
        values.put("user_id", userId);

        return database.insert("review", null, values);
    }


    public ArrayList<ReviewModel> getReviews() {
        ArrayList<ReviewModel> reviews = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM review WHERE product_id = ?", new String[]{String.valueOf(product_id)});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                reviews.add(ReviewModel.fromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }


        return reviews;
    }


    public double getAverageRating(ArrayList<ReviewModel> reviews) {
        double averageRating = 0;
        for (ReviewModel review : reviews) {
            averageRating += review.getRating();
        }
        averageRating = averageRating / reviews.size();
        return averageRating;
    }


}
