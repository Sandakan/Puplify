package com.adsandakannipunajith.puplify.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.adsandakannipunajith.puplify.App;

public class PaymentMethodDAO {
    private final SQLiteDatabase database;

    public PaymentMethodDAO() {
        database = App.getDatabase();
    }

    public long addPaymentMethod(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        ContentValues values = new ContentValues();
        values.put("card_number", cardNumber);
        values.put("card_holder_name", cardHolderName);
        values.put("expiration_date", expirationDate);
        values.put("cvv", cvv);
        values.put("user_id", App.getSharedPreferences().getInt("user_id", 0));

        long id = database.insert("payment_method", null, values);
        if (id == -1) {
            Toast.makeText(App.getContext(), "Failed to add payment method", Toast.LENGTH_SHORT).show();
        }

        return id;
    }
}
