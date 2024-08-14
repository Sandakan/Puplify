package com.adsandakannipunajith.puplify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "puplify.db";
    private static final int DATABASE_VERSION = 1;
    private static final String createUserSql = "CREATE TABLE IF NOT EXISTS \"user\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"first_name\" TEXT NOT NULL,\n" +
            "\t\"last_name\" TEXT NOT NULL,\n" +
            "\t\"email\" TEXT NOT NULL UNIQUE,\n" +
            "\t\"password\" TEXT NOT NULL,\n" +
            "\t\"address\" TEXT,\n" +
            "\t\"created_at\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";
    private static final String createProductSql = "CREATE TABLE IF NOT EXISTS \"product\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"name\" TEXT NOT NULL,\n" +
            "\t\"brand\" TEXT NOT NULL,\n" +
            "\t\"type\" TEXT NOT NULL,\n" +
            "\t\"age_group\" TEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";
    private static final String createPaymentMethodSql = "CREATE TABLE IF NOT EXISTS \"payment_method\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"card_number\" TEXT NOT NULL,\n" +
            "\t\"card_holder_name\" TEXT NOT NULL,\n" +
            "\t\"expiration_date\" TEXT NOT NULL,\n" +
            "\t\"cvv\" TEXT NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createCartSql = "CREATE TABLE IF NOT EXISTS \"cart\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"status\" TEXT NOT NULL DEFAULT 'PENDING',\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createCartItemSql = "CREATE TABLE IF NOT EXISTS \"cart_item\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"cart_id\" INTEGER NOT NULL,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"quantity\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"cart_id\") REFERENCES \"cart\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";

    private static final String createOrderSql = "CREATE TABLE IF NOT EXISTS \"order\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"total_price\" REAL NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createOrderItemSql = "CREATE TABLE IF NOT EXISTS \"order_item\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"quantity\" INTEGER NOT NULL,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"order_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"order_id\") REFERENCES \"order\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createReviewSql = "CREATE TABLE IF NOT EXISTS \"review\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\t\"rating\" INTEGER NOT NULL CHECK(rating BETWEEN 1 AND 5),\n" +
            "\t\"comment\" TEXT NOT NULL,\n" +
            "\t\"created_at\" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"product_id\") REFERENCES \"product\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createEducationalContentSql = "CREATE TABLE IF NOT EXISTS \"educational_content\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"title\" TEXT NOT NULL,\n" +
            "\t\"description\" TEXT NOT NULL,\n" +
            "\t\"url\" TEXT NOT NULL,\n" +
            "\t\"type\" TEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserSql);
        db.execSQL(createProductSql);
        db.execSQL(createCartSql);
        db.execSQL(createCartItemSql);
        db.execSQL(createOrderSql);
        db.execSQL(createOrderItemSql);
        db.execSQL(createReviewSql);
        db.execSQL(createEducationalContentSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

}
