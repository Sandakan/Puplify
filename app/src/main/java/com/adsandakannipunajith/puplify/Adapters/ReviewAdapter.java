package com.adsandakannipunajith.puplify.Adapters;

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

import com.adsandakannipunajith.puplify.Activities.ProductActivity;
import com.adsandakannipunajith.puplify.DAO.CartDAO;
import com.adsandakannipunajith.puplify.DAO.ReviewDAO;
import com.adsandakannipunajith.puplify.DAO.UserDAO;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.Models.ReviewModel;
import com.adsandakannipunajith.puplify.Models.UserModel;
import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.Utils.TimeUtils;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ReviewModel> reviews;
    private UserDAO userDAO;

    public ReviewAdapter(Context context, int product_id) {
        this.context = context;
        ReviewDAO reviewDAO = new ReviewDAO(product_id);
        this.reviews = reviewDAO.getReviews();
        userDAO = new UserDAO(this.context);
    }

    public ReviewAdapter(Context context, ArrayList<ReviewModel> reviews) {
        this.context = context;
        this.reviews = reviews;
        userDAO = new UserDAO(this.context);
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String elapsedTime = TimeUtils.getElapsedTime(reviews.get(position).getDate());
        UserModel user = userDAO.getUser(reviews.get(position).getUserId());
        double rating = reviews.get(position).getRating();

        holder.reviewContent.setText(reviews.get(position).getComment());
        holder.reviewCreationDate.setText(elapsedTime);
        holder.reviewUserName.setText(user.getFirstName() + " " + user.getLastName());
        holder.setRating(rating);
        holder.reviewUserAvatar.setImageResource(R.drawable.user);

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewUserAvatar;
        TextView reviewUserName;
        TextView reviewCreationDate;
        TextView reviewContent;

        ImageView reviewRatingStarOne;
        ImageView reviewRatingStarTwo;
        ImageView reviewRatingStarThree;
        ImageView reviewRatingStarFour;
        ImageView reviewRatingStarFive;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewUserAvatar = itemView.findViewById(R.id.review_user_avatar);
            reviewUserName = itemView.findViewById(R.id.review_username);
            reviewCreationDate = itemView.findViewById(R.id.review_creation_date);
            reviewContent = itemView.findViewById(R.id.review_content);

            reviewRatingStarOne = itemView.findViewById(R.id.review_rating_star_one);
            reviewRatingStarTwo = itemView.findViewById(R.id.review_rating_star_two);
            reviewRatingStarThree = itemView.findViewById(R.id.review_rating_star_three);
            reviewRatingStarFour = itemView.findViewById(R.id.review_rating_star_four);
            reviewRatingStarFive = itemView.findViewById(R.id.review_rating_star_five);

        }

        private void setRating(double rating) {
            if (rating >= 1.0) {
                reviewRatingStarOne.setImageResource(R.drawable.ic_star_24dp);
            } else if (rating > 0.5) {
                reviewRatingStarOne.setImageResource(R.drawable.ic_star_half_24dp);
            }

            if (rating >= 2) {
                reviewRatingStarTwo.setImageResource(R.drawable.ic_star_24dp);
            } else if (rating >= 1.5) {
                reviewRatingStarTwo.setImageResource(R.drawable.ic_star_half_24dp);
            }

            if (rating >= 3) {
                reviewRatingStarThree.setImageResource(R.drawable.ic_star_24dp);
            } else if (rating >= 2.5) {
                reviewRatingStarThree.setImageResource(R.drawable.ic_star_half_24dp);
            }

            if (rating >= 4.0) {
                reviewRatingStarFour.setImageResource(R.drawable.ic_star_24dp);
            } else if (rating >= 3.5) {
                reviewRatingStarFour.setImageResource(R.drawable.ic_star_half_24dp);
            }

            if (rating == 5.0) {
                reviewRatingStarFive.setImageResource(R.drawable.ic_star_24dp);
            } else if (rating >= 4.5) {
                reviewRatingStarFive.setImageResource(R.drawable.ic_star_half_24dp);
            }
        }
    }

}
