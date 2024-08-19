package com.adsandakannipunajith.puplify.activities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.adapters.ProductAdapter;
import com.adsandakannipunajith.puplify.dao.ProductDAO;
import com.adsandakannipunajith.puplify.databinding.FragmentHomeBinding;
import com.adsandakannipunajith.puplify.models.ProductModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        RecyclerView productRecyclerView = binding.productRecyclerView;

        ProductDAO productDAO = new ProductDAO();
        ArrayList<ProductModel> products = productDAO.getProducts();
        ProductAdapter productAdapter = new ProductAdapter(getContext(), products);

        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}