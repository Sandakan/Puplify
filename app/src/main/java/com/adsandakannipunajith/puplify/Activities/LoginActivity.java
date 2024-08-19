package com.adsandakannipunajith.puplify.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.adsandakannipunajith.puplify.DAO.UserDAO;
import com.adsandakannipunajith.puplify.Models.UserModel;
import com.adsandakannipunajith.puplify.R;

public class LoginActivity extends AppCompatActivity {
    public EditText emailInput;
    public EditText passwordInput;
    public Button loginButton;
    public TextView emailInputErrorMessage;
    public TextView passwordInputErrorMessage;
    private UserDAO userDAO;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.register_button);

        emailInputErrorMessage = findViewById(R.id.login_email_input_error_message);
        passwordInputErrorMessage = findViewById(R.id.password_input_error_message);

        userDAO = new UserDAO(this);

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
    }

    public void onSubmit(View v) {
        boolean isError = false;
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Clear error messages
        emailInputErrorMessage.setText("");
        passwordInputErrorMessage.setText("");

        if (email.isEmpty()) {
            emailInputErrorMessage.setVisibility(View.VISIBLE);
            emailInputErrorMessage.setText(R.string.email_cannot_be_empty);
            isError = true;
        }
        if (password.isEmpty()) {
            passwordInputErrorMessage.setVisibility(View.VISIBLE);
            passwordInputErrorMessage.setText(R.string.password_cannot_be_empty);
            isError = true;
        }

        if (!isError) {
            boolean isAuthenticated = userDAO.authenticateUser(email, password);
            if (isAuthenticated) {
                UserModel user = userDAO.getUser(email);
                sharedPreferences.edit()
                        .putString("user_email", user.getEmail())
                        .putInt("user_id", user.getId())
                        .putString("user_first_name", user.getFirstName())
                        .putString("user_last_name", user.getLastName())
                        .apply();

                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            } else {
                emailInputErrorMessage.setVisibility(View.VISIBLE);
                emailInputErrorMessage.setText(R.string.email_or_password_is_incorrect);
            }
        }
    }
}