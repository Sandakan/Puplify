package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

import com.adsandakannipunajith.puplify.App;

public class EducationalItemModel {

    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String url;
    private String type;


    public EducationalItemModel(int id, String title, String description, String imageUrl, String url, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }


    public static EducationalItemModel fromCursor(Cursor cursor) {
        return new EducationalItemModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getString(cursor.getColumnIndexOrThrow("image_url")),
                cursor.getString(cursor.getColumnIndexOrThrow("url")),
                cursor.getString(cursor.getColumnIndexOrThrow("type"))
        );
    }
}
