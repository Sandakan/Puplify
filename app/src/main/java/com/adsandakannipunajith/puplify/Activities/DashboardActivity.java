package com.adsandakannipunajith.puplify.Activities;

import android.os.Bundle;


import com.adsandakannipunajith.puplify.Activities.ui.browse.BrowseFragment;
import com.adsandakannipunajith.puplify.Activities.ui.cart.CartFragment;
import com.adsandakannipunajith.puplify.Activities.ui.home.HomeFragment;
import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.databinding.ActivityDashboardBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.navView.setOnItemSelectedListener(item -> {
            int menuItemId = item.getItemId();

            if (menuItemId == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
                return true;
            }

            if (menuItemId == R.id.navigation_browse) {
                replaceFragment(new BrowseFragment());
                return true;
            }

            if (menuItemId == R.id.navigation_cart) {
                replaceFragment(new CartFragment());
                return true;
            }

            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();


    }
}