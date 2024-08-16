package com.adsandakannipunajith.puplify.Activities.ui.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.databinding.FragmentCartBinding;
import com.adsandakannipunajith.puplify.databinding.FragmentHomeBinding;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}