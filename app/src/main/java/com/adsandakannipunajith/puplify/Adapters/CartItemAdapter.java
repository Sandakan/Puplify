package com.adsandakannipunajith.puplify.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.Activities.ProductActivity;
import com.adsandakannipunajith.puplify.DAO.CartDAO;
import com.adsandakannipunajith.puplify.DAO.ProductDAO;
import com.adsandakannipunajith.puplify.Models.CartItemModel;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<CartItemModel> cartItems;
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private Callable<Void> onItemDataChanged;

    public CartItemAdapter(Context context, Callable<Void> onItemDataChanged) {
        this.context = context;
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
        this.cartItems = cartDAO.getCartItems();
        this.onItemDataChanged = onItemDataChanged;
    }

    public CartItemAdapter(Context context, ArrayList<CartItemModel> cartItems, Callable<Void> onItemDataChanged) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
        this.onItemDataChanged = onItemDataChanged;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_cart_item, parent, false);
        return new CartItemAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        CartItemModel cartItem = cartItems.get(position);
        ProductModel product = productDAO.getProduct(cartItem.getProductId());
        final int[] quantity = {cartItem.getQuantity()};

        holder.cartItemName.setText(product.getName());
        holder.cartItemPrice.setText(String.format("LKR %s", String.format("%.2f", product.getPrice())));
        Glide.with(context).load(product.getImageUrl()).into(holder.cartItemImage);
        holder.cartItemQuantity.setText(String.format("%d", quantity[0]));
        holder.cartItemDeleteButton.setOnClickListener(view -> onRemoveCartItem(product, cartItem));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("product_id", product.getId());
            view.getContext().startActivity(intent);
        });

        holder.cartItemQuantityIncrementButton.setOnClickListener(view -> {
            int newQuantity = quantity[0] + 1;
            cartDAO.updateCartItemQuantity(cartItem.getId(), newQuantity);
            holder.cartItemQuantity.setText(String.format("%d", newQuantity));
            quantity[0] = (newQuantity);
            notifyItemChanged();

        });

        holder.cartItemQuantityDecrementButton.setOnClickListener(view -> {
            int newQuantity = quantity[0] - 1;
            if (newQuantity > 0) {
                cartDAO.updateCartItemQuantity(cartItem.getId(), newQuantity);
                holder.cartItemQuantity.setText(String.format("%d", newQuantity));
                quantity[0] = newQuantity;
                notifyItemChanged();
            } else {
                CartItemAdapter.this.onRemoveCartItem(product, cartItem);
            }
        });
    }

    public void onRemoveCartItem(ProductModel product, CartItemModel cartItem) {
        new AlertDialog.Builder(context)
                .setTitle(String.format("Remove '%s' from cart?", product.getName()))
                .setMessage("Do you really want to remove this item?")
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    if (cartDAO == null) {
                        cartDAO = new CartDAO();
                    }
                    cartDAO.removeCartItem(cartItem.getId());
                    cartItems.remove(cartItem);
                    notifyItemRemoved(cartItems.indexOf(cartItem));
                    notifyItemRangeChanged(cartItems.indexOf(cartItem), cartItems.size());
                    Toast.makeText(context, "Item removed from cart successfully", Toast.LENGTH_SHORT).show();
                    notifyItemChanged();
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void notifyItemChanged() {
        try {
            onItemDataChanged.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemPrice;
        ImageButton cartItemQuantityDecrementButton;
        TextView cartItemQuantity;
        ImageButton cartItemQuantityIncrementButton;
        ImageButton cartItemDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_item_image);
            cartItemName = itemView.findViewById(R.id.cart_item_name);
            cartItemPrice = itemView.findViewById(R.id.cart_item_price);
            cartItemQuantityDecrementButton = itemView.findViewById(R.id.cart_item_quantity_decrement_button);
            cartItemQuantity = itemView.findViewById(R.id.cart_item_quantity);
            cartItemQuantityIncrementButton = itemView.findViewById(R.id.cart_item_quantity_increment_button);
            cartItemDeleteButton = itemView.findViewById(R.id.cart_item_delete_button);
        }
    }
}
