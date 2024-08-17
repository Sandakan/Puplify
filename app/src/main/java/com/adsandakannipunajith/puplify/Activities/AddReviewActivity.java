package com.adsandakannipunajith.puplify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adsandakannipunajith.puplify.DAO.ProductDAO;
import com.adsandakannipunajith.puplify.DAO.ReviewDAO;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.Models.ReviewModel;
import com.adsandakannipunajith.puplify.R;

public class AddReviewActivity extends AppCompatActivity {
    private int product_id;
    private ReviewDAO reviewDAO;
    private ProductDAO productDAO;
    private ProductModel product;

    private TextView addReviewTitle;
    private RatingBar addReviewRatingBar;
    private TextView addReviewInput;
    private Button addReviewSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addReviewTitle = findViewById(R.id.add_review_title);
        addReviewRatingBar = findViewById(R.id.add_review_rating_bar);
        addReviewInput = findViewById(R.id.add_review_input);
        addReviewSubmitButton = findViewById(R.id.add_review_submit_button);

        product_id = getIntent().getIntExtra("product_id", -1);

        productDAO = new ProductDAO();
        product = productDAO.getProduct(product_id);
        reviewDAO = new ReviewDAO(product_id);
        addReviewTitle.setText(String.format("Adding a new review to '%s'", product.getName()));


        addReviewSubmitButton.setOnClickListener(v -> {
            String review = addReviewInput.getText().toString();
            float rating = addReviewRatingBar.getRating();

            reviewDAO.addReview(review, rating);
            Toast.makeText(this, "Review added successfully.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}