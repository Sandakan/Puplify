package com.adsandakannipunajith.puplify.activities.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.activities.MainActivity;
import com.adsandakannipunajith.puplify.activities.UpdatePaymentMethodActivity;
import com.adsandakannipunajith.puplify.activities.UpdateProfileInformationActivity;
import com.adsandakannipunajith.puplify.dao.UserDAO;
import com.adsandakannipunajith.puplify.databinding.FragmentProfileBinding;
import com.adsandakannipunajith.puplify.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ImageView profileUserImage;
    private TextView profileUserName;
    private TextView profileUserEmail;
    private TextView profileUserCreationDate;
    private SharedPreferences sharedPreferences;
    private UserModel user;
    private UserDAO userDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        profileUserImage = binding.profileUserImage;
        profileUserName = binding.profileUserName;
        profileUserEmail = binding.profileUserEmail;
        profileUserCreationDate = binding.profileUserCreationDate;

        binding.profileUpdateProfileInformationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), UpdateProfileInformationActivity.class);
            startActivity(intent);
        });

        binding.profileUpdatePaymentMethodButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), UpdatePaymentMethodActivity.class);
            startActivity(intent);
        });

        binding.profileLogoutButton.setOnClickListener(view -> new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    App.getSharedPreferences().edit().clear().apply();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show());


        return binding.getRoot();
    }

    public void updateProfileData() {
        sharedPreferences = App.getSharedPreferences();
        userDAO = new UserDAO(this.getContext());
        user = userDAO.getUser(sharedPreferences.getString("user_email", ""));

        profileUserImage.setImageResource(R.drawable.user);
        profileUserName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        profileUserEmail.setText(user.getEmail());

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(user.getAccountCreationDate());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMMM d, yyyy");
            assert date != null;
            profileUserCreationDate.setText(String.format("User since %s", simpleDateFormat2.format(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateProfileData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}