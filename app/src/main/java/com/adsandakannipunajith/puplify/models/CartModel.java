package com.adsandakannipunajith.puplify.models;

import android.database.Cursor;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.dao.ProductDAO;

import java.util.ArrayList;

public class CartModel {

    private final int id;
    private final int userId;
    private final String status;

    public CartModel(int id, int userId, String status) {
        this.id = id;
        this.userId = userId;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public static CartModel fromCursor(Cursor cursor) {
        int userId = App.getSharedPreferences().getInt("user_id", 0);
        return new CartModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                userId,
                cursor.getString(cursor.getColumnIndexOrThrow("status"))
        );
    }

    public static double getTotalPrice(ArrayList<CartItemModel> cartItems) {
        ProductDAO productDAO = new ProductDAO();
        double totalPrice = 0;

        for (CartItemModel cartItem : cartItems) {
            ProductModel product = productDAO.getProduct(cartItem.getProductId());
            totalPrice += product.getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
