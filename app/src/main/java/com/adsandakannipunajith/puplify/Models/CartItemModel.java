package com.adsandakannipunajith.puplify.Models;

import android.database.Cursor;

public class CartItemModel {

    private final int id;
    private final int productId;
    private final int cartId;
    private final int quantity;
    private final ProductModel product;

    public static String TABLE_NAME = "cart_item";


    public CartItemModel(int id, int productId, int cartId, int quantity, ProductModel product) {
        this.id = id;
        this.productId = productId;
        this.cartId = cartId;
        this.quantity = quantity;
        this.product = product;
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

    public ProductModel getProduct() {
        return product;
    }

    public static CartItemModel fromCursor(Cursor cursor) {
        return new CartItemModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("cart_id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                ProductModel.fromCursor(cursor)
        );
    }
}
