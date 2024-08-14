package com.adsandakannipunajith.puplify;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adsandakannipunajith.puplify.DAO.UserDAO;

public class App extends Application {

    private static SQLiteDatabase database;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);

        // Initialize SQLite database
        database = new DatabaseHelper(this).getWritableDatabase();

    }

    // Method to get SQLiteDatabase instance
    public static SQLiteDatabase getDatabase() {
        return database;
    }

    // Method to get SharedPreferences instance
    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    // SQLiteOpenHelper class for managing database creation and version management

}
