package com.adsandakannipunajith.puplify.activities.ui.cart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.adapters.CartItemAdapter;
import com.adsandakannipunajith.puplify.dao.CartDAO;
import com.adsandakannipunajith.puplify.databinding.FragmentCartBinding;
import com.adsandakannipunajith.puplify.models.CartItemModel;
import com.adsandakannipunajith.puplify.models.CartModel;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private TextView noCartItemsText;
    private TextView subTotalText;
    private TextView cartItemCountText;
    private Button confirmOrderButton;
    private ArrayList<CartItemModel> cartItems;
    private CartDAO cartDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        noCartItemsText = binding.noCartItemsText;
        subTotalText = binding.cartSubTotal;
        cartItemCountText = binding.cartItemCount;
        confirmOrderButton = binding.cartConfirmOrderButton;
        RecyclerView productRecyclerView = binding.cartItemRecyclerView;
        cartDAO = new CartDAO();

        // cart items will be assigned here
        handleItemDataChanged();

        productRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        confirmOrderButton.setOnClickListener(view -> new AlertDialog.Builder(getContext()).setTitle("Confirm Order").setMessage("Do you really want to confirm this order?")
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    cartDAO.confirmCart();
                    handleItemDataChanged();
                }).setNegativeButton(android.R.string.no, null).show());
        return binding.getRoot();
    }

    public Void handleItemDataChanged() {

        cartItems = cartDAO.getCartItems();
        CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), cartItems, this::handleItemDataChanged);
        double subTotal = CartModel.getTotalPrice(cartItems);

        subTotalText.setText(String.format("LKR %.2f", subTotal));
        cartItemCountText.setText(String.format("%d items", cartItems.size()));
        binding.cartItemRecyclerView.setAdapter(cartItemAdapter);
        if (cartItems.isEmpty()) {
            noCartItemsText.setVisibility(View.VISIBLE);
        } else {
            noCartItemsText.setVisibility(View.GONE);
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}