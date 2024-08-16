package com.adsandakannipunajith.puplify.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.Models.CartItemModel;
import com.adsandakannipunajith.puplify.Models.CartModel;

import java.util.ArrayList;

public class CartDAO {
    private final SQLiteDatabase database;

    public CartDAO() {
        database = App.getDatabase();
    }

    public CartModel getCart() {
        int userId = App.getSharedPreferences().getInt("user_id", 0);
        Cursor cursor = database.rawQuery("SELECT * FROM cart WHERE user_id = ? AND status = ? LIMIT 1", new String[]{String.valueOf(userId), "ACTIVE"});
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        return CartModel.fromCursor(cursor);
    }

    public ArrayList<CartItemModel> getCartItems() {
        ArrayList<CartItemModel> products = new ArrayList<>();
        CartModel cart = getCart();

        if (cart == null) {
            return products;
        }

        Cursor cursor = database.rawQuery("SELECT * FROM cart_items WHERE cart_id = ?", new String[]{String.valueOf(cart.getId())});
        if (cursor == null || cursor.getCount() == 0) {
            return products;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            products.add(CartItemModel.fromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public CartModel createCart() {
        CartModel prevCart = getCart();
        if (prevCart != null) {
            return prevCart;
        }

        int userId = App.getSharedPreferences().getInt("user_id", 0);
        database.execSQL("INSERT INTO cart (user_id, status) VALUES (?, ?)", new String[]{String.valueOf(userId), "ACTIVE"});

        return getCart();
    }

    public void addCartItem(int productId, int quantity) {
        CartModel cart = createCart();

        database.execSQL(
                "INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (?, ?, ?)",
                new String[]{
                        String.valueOf(cart.getId()),
                        String.valueOf(productId),
                        String.valueOf(quantity)
                }
        );

    }

}
