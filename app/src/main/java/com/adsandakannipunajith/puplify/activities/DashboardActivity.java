package com.adsandakannipunajith.puplify.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.activities.ui.browse.BrowseFragment;
import com.adsandakannipunajith.puplify.activities.ui.cart.CartFragment;
import com.adsandakannipunajith.puplify.activities.ui.home.HomeFragment;
import com.adsandakannipunajith.puplify.activities.ui.info.InfoFragment;
import com.adsandakannipunajith.puplify.activities.ui.profile.ProfileFragment;
import com.adsandakannipunajith.puplify.databinding.ActivityDashboardBinding;

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

            if (menuItemId == R.id.navigation_info) {
                replaceFragment(new InfoFragment());
                return true;
            }

            if (menuItemId == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment());
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