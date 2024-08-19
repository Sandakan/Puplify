package com.adsandakannipunajith.puplify.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.models.PaymentMethodModel;

public class PaymentMethodDAO {
    private final SQLiteDatabase database;

    public PaymentMethodDAO() {
        database = App.getDatabase();
    }

    public PaymentMethodModel getPaymentMethod() {
        Cursor cursor = database.query("payment_method", null, "user_id = ?", new String[]{String.valueOf(App.getSharedPreferences().getInt("user_id", 0))}, null, null, null);
        if (cursor.moveToFirst()) {
            return PaymentMethodModel.fromCursor(cursor);
        } else {
            return null;
        }
    }

    public long addPaymentMethod(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        ContentValues values = new ContentValues();
        values.put("card_number", cardNumber);
        values.put("card_holder_name", cardHolderName);
        values.put("expiration_date", expirationDate);
        values.put("cvv", cvv);
        values.put("user_id", App.getSharedPreferences().getInt("user_id", 0));

        return database.insert("payment_method", null, values);
    }

    public void updatePaymentMethod(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        Cursor cursor = database.query("payment_method", new String[]{"id"}, "user_id = ?", new String[]{String.valueOf(App.getSharedPreferences().getInt("user_id", 0))}, null, null, null);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("card_number", cardNumber);
            values.put("card_holder_name", cardHolderName);
            values.put("expiration_date", expirationDate);
            values.put("cvv", cvv);
            database.update("payment_method", values, "id = ?", new String[]{String.valueOf(cursor.getInt(0))});
        } else {
            addPaymentMethod(cardNumber, cardHolderName, expirationDate, cvv);
        }
        cursor.close();
    }
}
