package com.adsandakannipunajith.puplify.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adsandakannipunajith.puplify.DAO.UserDAO;
import com.adsandakannipunajith.puplify.R;

public class RegisterActivity extends AppCompatActivity {
    public EditText firstNameInput;
    public EditText lastNameInput;
    public EditText emailInput;
    public EditText passwordInput;
    public EditText confirmPasswordInput;

    public TextView firstNameInputErrorMessage;
    public TextView lastNameInputErrorMessage;
    public TextView emailInputErrorMessage;
    public TextView passwordInputErrorMessage;
    public TextView confirmPasswordInputErrorMessage;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstNameInput = findViewById(R.id.register_first_name_input);
        lastNameInput = findViewById(R.id.register_last_name_input);
        emailInput = findViewById(R.id.register_email_input);
        passwordInput = findViewById(R.id.register_password_input);
        confirmPasswordInput = findViewById(R.id.register_confirm_password_input);

        firstNameInputErrorMessage = findViewById(R.id.register_first_name_input_error_message);
        lastNameInputErrorMessage = findViewById(R.id.register_last_name_input_error_message);
        emailInputErrorMessage = findViewById(R.id.register_email_input_error_message);
        passwordInputErrorMessage = findViewById(R.id.register_password_input_error_message);
        confirmPasswordInputErrorMessage = findViewById(R.id.register_confirm_password_input_error_message);

        userDAO = new UserDAO(this);
    }

    public void onRegister(View v) {
        boolean isError = false;

        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        // Clear error messages
        firstNameInputErrorMessage.setText("");
        lastNameInputErrorMessage.setText("");
        emailInputErrorMessage.setText("");
        passwordInputErrorMessage.setText("");
        confirmPasswordInputErrorMessage.setText("");

        if (firstName.isEmpty()) {
            firstNameInputErrorMessage.setVisibility(View.VISIBLE);
            firstNameInputErrorMessage.setText(R.string.first_name_cannot_be_empty);
            isError = true;
        }
        if (lastName.isEmpty()) {
            lastNameInputErrorMessage.setVisibility(View.VISIBLE);
            lastNameInputErrorMessage.setText(R.string.last_name_cannot_be_empty);
            isError = true;
        }
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
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputErrorMessage.setVisibility(View.VISIBLE);
            confirmPasswordInputErrorMessage.setText(R.string.confirm_password_cannot_be_empty);
            isError = true;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordInputErrorMessage.setVisibility(View.VISIBLE);
            confirmPasswordInputErrorMessage.setText(R.string.password_and_confirm_password_must_match);
            isError = true;
        }

        if (!isError) {
            long isAuthenticated = userDAO.addUser(firstName, lastName, email, password);
            if (isAuthenticated > 0) Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
            else {
                emailInputErrorMessage.setVisibility(View.VISIBLE);
                emailInputErrorMessage.setText(R.string.email_is_already_registered);
            }
        }
    }
}