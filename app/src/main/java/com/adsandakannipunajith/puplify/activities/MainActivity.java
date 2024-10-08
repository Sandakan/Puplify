package com.adsandakannipunajith.puplify.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        checkUserSession();
    }

    public void checkUserSession() {
        SharedPreferences preferences = App.getSharedPreferences();

        if (preferences.contains("user_email") && preferences.contains("user_id")) {
            Toast.makeText(this, "Welcome back, " + preferences.getString("user_first_name", ""), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }

    public void onLoginButtonClick(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onRegisterButtonClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


}