package com.adsandakannipunajith.puplify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.Adapters.ReviewAdapter;
import com.adsandakannipunajith.puplify.DAO.CartDAO;
import com.adsandakannipunajith.puplify.DAO.ProductDAO;
import com.adsandakannipunajith.puplify.DAO.ReviewDAO;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.Models.ReviewModel;
import com.adsandakannipunajith.puplify.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private int product_id;
    private int selectedQuantity = 1;
    private double averageRating = 0;
    ProductModel product;
    ProductDAO productDAO;
    CartDAO cartDAO;
    ReviewDAO reviewDAO;

    TextView productName;
    TextView productDescription;
    TextView productPrice;
    ImageView productImage;
    TextView productQuantity;
    TextView productReviewCount;

    ImageButton quantityIncrementButton;
    ImageButton quantityDecrementButton;
    ImageView ratingOneStarImage;
    ImageView ratingTwoStarImage;
    ImageView ratingThreeStarImage;
    ImageView ratingFourStarImage;
    ImageView ratingFiveStarImage;

    Button addToCartButton;
    Button addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        productName = findViewById(R.id.product_info_name);
        productDescription = findViewById(R.id.product_info_description);
        productPrice = findViewById(R.id.product_info_price);
        productImage = findViewById(R.id.product_info_image);
        productQuantity = findViewById(R.id.product_info_quantity);
        productReviewCount = findViewById(R.id.product_info_review_count);
        quantityIncrementButton = findViewById(R.id.product_info_quantity_increment_button);
        quantityDecrementButton = findViewById(R.id.cart_item_quantity_decrement_button);
        ratingOneStarImage = findViewById(R.id.product_info_rating_star_one);
        ratingTwoStarImage = findViewById(R.id.product_info_rating_star_two);
        ratingThreeStarImage = findViewById(R.id.product_info_rating_star_three);
        ratingFourStarImage = findViewById(R.id.product_info_rating_star_four);
        ratingFiveStarImage = findViewById(R.id.product_info_rating_star_five);

        addToCartButton = findViewById(R.id.product_info_add_to_cart_button);
        addReviewButton = findViewById(R.id.product_info_add_review_button);

        product_id = getIntent().getIntExtra("product_id", 0);

        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        reviewDAO = new ReviewDAO(product_id);

        product = productDAO.getProduct(product_id);
        ArrayList<ReviewModel> reviews = reviewDAO.getReviews();


        updateAdapterData(reviews);
        averageRating = reviewDAO.getAverageRating(reviews);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.format("LKR %s", String.format("%.2f", product.getPrice())));
        Glide.with(this).load(product.getImageUrl()).into(productImage);
        productReviewCount.setText(String.format("%s reviews", String.valueOf(reviews.size())));

        setRating(averageRating);

        productQuantity.setText(String.valueOf(selectedQuantity));
        quantityIncrementButton.setOnClickListener(v -> changeQuantity(1, null));
        quantityDecrementButton.setOnClickListener(v -> changeQuantity(-1, null));

        addToCartButton.setOnClickListener(v -> {
            cartDAO.addCartItem(product.getId(), selectedQuantity);
            changeQuantity(0, 1);
            Toast.makeText(ProductActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
        });

        addReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, AddReviewActivity.class);
            intent.putExtra("product_id", product_id);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void updateAdapterData(ArrayList<ReviewModel> reviews) {
        ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviews);
        RecyclerView reviewRecyclerView = findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
    }

    private void changeQuantity(int incrementValue, Integer customValue) {
        if (customValue != null) selectedQuantity = customValue;
        else selectedQuantity += incrementValue;
        productQuantity.setText(String.valueOf(selectedQuantity));
    }

    private void setRating(double rating) {
        if (rating >= 1.0) {
            ratingOneStarImage.setImageResource(R.drawable.ic_star_24dp);
        } else if (rating > 0.5) {
            ratingOneStarImage.setImageResource(R.drawable.ic_star_half_24dp);
        }

        if (rating >= 2) {
            ratingTwoStarImage.setImageResource(R.drawable.ic_star_24dp);
        } else if (rating >= 1.5) {
            ratingTwoStarImage.setImageResource(R.drawable.ic_star_half_24dp);
        }

        if (rating >= 3) {
            ratingThreeStarImage.setImageResource(R.drawable.ic_star_24dp);
        } else if (rating >= 2.5) {
            ratingThreeStarImage.setImageResource(R.drawable.ic_star_half_24dp);
        }

        if (rating >= 4.0) {
            ratingFourStarImage.setImageResource(R.drawable.ic_star_24dp);
        } else if (rating >= 3.5) {
            ratingFourStarImage.setImageResource(R.drawable.ic_star_half_24dp);
        }

        if (rating == 5.0) {
            ratingFiveStarImage.setImageResource(R.drawable.ic_star_24dp);
        } else if (rating >= 4.5) {
            ratingFiveStarImage.setImageResource(R.drawable.ic_star_half_24dp);
        }
    }
}