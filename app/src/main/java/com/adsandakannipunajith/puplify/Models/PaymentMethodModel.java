package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

public class PaymentMethodModel {
    private int id;
    private final String cardHolderName;
    private final String cardNumber;
    private final String expirationDate;
    private final String cvv;

    public PaymentMethodModel(int id, String cardHolderName, String cardNumber, String expirationDate, String cvv) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public static PaymentMethodModel fromCursor(Cursor cursor) {
        return new PaymentMethodModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("card_holder_name")),
                cursor.getString(cursor.getColumnIndexOrThrow("card_number")),
                cursor.getString(cursor.getColumnIndexOrThrow("expiration_date")),
                cursor.getString(cursor.getColumnIndexOrThrow("cvv"))
        );
    }

    public int getId() {
        return id;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }
}
