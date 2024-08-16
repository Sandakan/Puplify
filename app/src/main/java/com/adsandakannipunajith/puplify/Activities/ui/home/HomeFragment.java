package com.adsandakannipunajith.puplify.Activities.ui.home;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.Adapters.ProductAdapter;
import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.DAO.ProductDAO;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String firstName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
//
//        SharedPreferences preferences = App.getSharedPreferences();
//        firstName = preferences.getString("firstName", "");
////        binding.greetingUserFirstName.setText(firstName);
//
        RecyclerView productRecyclerView = binding.productRecyclerView;


        ProductDAO productDAO = new ProductDAO();
        ArrayList<ProductModel> products = productDAO.getProducts();
        ProductAdapter adapter = new ProductAdapter(getContext(), products);

        productRecyclerView.setAdapter(adapter);
        productRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}