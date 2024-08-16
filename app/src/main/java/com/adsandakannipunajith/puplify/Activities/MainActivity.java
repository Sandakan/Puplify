package com.adsandakannipunajith.puplify.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        checkUserSession();
    }

    public void checkUserSession() {
        SharedPreferences preferences = App.getSharedPreferences();

        if (preferences.contains("user_email") && preferences.contains("user_id")) {
            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
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