package com.adsandakannipunajith.puplify.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.models.CartItemModel;
import com.adsandakannipunajith.puplify.models.CartModel;

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
        CartModel model = CartModel.fromCursor(cursor);
        cursor.close();
        return model;
    }

    public ArrayList<CartItemModel> getCartItems() {
        ArrayList<CartItemModel> products = new ArrayList<>();
        CartModel cart = getCart();

        if (cart == null) {
            return products;
        }

        Cursor cursor = database.rawQuery("SELECT * FROM cart_item WHERE cart_id = ?", new String[]{String.valueOf(cart.getId())});
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

        String query = "SELECT id FROM cart_item WHERE cart_id = ? AND product_id = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(cart.getId()), String.valueOf(productId)});

        if (cursor != null && cursor.getCount() > 0) {
            // cart item with the selected product_id exists in the cart_item table
            cursor.moveToFirst();
            int cartItemId = cursor.getInt(0);
            database.execSQL(
                    "UPDATE cart_item SET quantity = quantity + ? WHERE id = ?",
                    new String[]{String.valueOf(quantity), String.valueOf(cartItemId)}
            );
        } else {
            // cart item with the selected product_id does not exist in the cart_item table
            database.execSQL(
                    "INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (?, ?, ?)",
                    new String[]{
                            String.valueOf(cart.getId()),
                            String.valueOf(productId),
                            String.valueOf(quantity)
                    }
            );
        }

        assert cursor != null;
        cursor.close();
    }

    public void updateCartItemQuantity(int cartItemId, int quantity) {
        database.execSQL("UPDATE cart_item SET quantity = ? WHERE id = ?", new String[]{String.valueOf(quantity), String.valueOf(cartItemId)});
    }

    public void removeCartItem(int cartItemId) {
        database.execSQL("DELETE FROM cart_item WHERE id = ?", new String[]{String.valueOf(cartItemId)});
    }

    public void confirmCart() {
        CartModel cart = getCart();
        ArrayList<CartItemModel> cartItems = getCartItems();

        database.execSQL("UPDATE cart SET status = ? WHERE id = ?", new String[]{"COMPLETED", String.valueOf(getCart().getId())});

        ContentValues values = new ContentValues();
        values.put("total_price", CartModel.getTotalPrice(cartItems));
        values.put("user_id", cart.getUserId());
        long order_Id = database.insert("order", null, values);

        for (CartItemModel item : cartItems) {
            ContentValues values2 = new ContentValues();
            values2.put("order_id", order_Id);
            values2.put("product_id", item.getProductId());
            values2.put("quantity", item.getQuantity());
            database.insert("order_item", null, values2);
        }
    }

}
