package com.adsandakannipunajith.puplify.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.DAO.CartDAO;
import com.adsandakannipunajith.puplify.Models.EducationalItemModel;
import com.adsandakannipunajith.puplify.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EducationalItemAdapter extends RecyclerView.Adapter<EducationalItemAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<EducationalItemModel> educationalItems;

    public EducationalItemAdapter(Context context, ArrayList<EducationalItemModel> educationalItems) {
        this.context = context;
        this.educationalItems = educationalItems;
    }

    @NonNull
    @Override
    public EducationalItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_educational_item, parent, false);
        return new EducationalItemAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EducationalItemAdapter.ViewHolder holder, int position) {
        EducationalItemModel educationalItem = educationalItems.get(position);
        holder.educationalItemTitle.setText(educationalItem.getTitle());
        holder.educationalItemDescription.setText(educationalItem.getDescription());
        holder.educationalItemType.setText(educationalItem.getType());
        Glide.
                with(context)
                .load(educationalItem.getImageUrl())
                .centerCrop()
                .into(holder.educationalItemImage);

        holder.educationalItemMoreInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(educationalItem.getUrl()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return educationalItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView educationalItemImage;
        TextView educationalItemTitle;
        TextView educationalItemDescription;
        TextView educationalItemType;
        Button educationalItemMoreInfoButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            educationalItemImage = itemView.findViewById(R.id.educational_item_image);
            educationalItemTitle = itemView.findViewById(R.id.educational_item_title);
            educationalItemDescription = itemView.findViewById(R.id.educational_item_description);
            educationalItemType = itemView.findViewById(R.id.educational_item_type);
            educationalItemMoreInfoButton = itemView.findViewById(R.id.educational_item_more_info_button);

        }
    }
}
