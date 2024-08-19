package com.adsandakannipunajith.puplify.models;

import android.database.Cursor;

public class CartItemModel {

    private final int id;
    private final int productId;
    private final int cartId;
    private final int quantity;

    public static String TABLE_NAME = "cart_item";


    public CartItemModel(int id, int productId, int cartId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static CartItemModel fromCursor(Cursor cursor) {
        return new CartItemModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("cart_id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
        );
    }
}
