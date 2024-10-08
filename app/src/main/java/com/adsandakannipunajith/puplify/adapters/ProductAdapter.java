package com.adsandakannipunajith.puplify.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.activities.ProductActivity;
import com.adsandakannipunajith.puplify.dao.CartDAO;
import com.adsandakannipunajith.puplify.models.ProductModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ProductModel> products;
    private CartDAO cartDAO;

    public ProductAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.products = new ArrayList<>();
        this.cartDAO = new CartDAO();

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ProductModel product = ProductModel.fromCursor(cursor);
            products.add(product);
        }
        cursor.close();
    }

    public ProductAdapter(Context context, ArrayList<ProductModel> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_product_item, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productDescription.setText(products.get(position).getDescription());
        holder.productPrice.setText(String.format("LKR %s", String.format("%.2f", products.get(position).getPrice())));
        Glide.with(context).load(products.get(position).getImageUrl()).centerCrop().into(holder.productImage);
        holder.productAddToCartButton.setOnClickListener(view -> {
            // Add product to cart
            if (cartDAO == null) {
                cartDAO = new CartDAO();
            }
            cartDAO.addCartItem(products.get(position).getId(), 1);
            Toast.makeText(view.getContext(), "Item added to cart successfully", Toast.LENGTH_SHORT).show();

        });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("product_id", products.get(position).getId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(ArrayList<ProductModel> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productDescription;
        Button productAddToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productDescription = itemView.findViewById(R.id.product_description);
            productAddToCartButton = itemView.findViewById(R.id.product_add_to_cart_button);

        }
    }
}
