package com.adsandakannipunajith.puplify.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.R;
import com.adsandakannipunajith.puplify.dao.UserDAO;
import com.adsandakannipunajith.puplify.models.UserModel;

public class UpdateProfileInformationActivity extends AppCompatActivity {
    private UserDAO userDAO;
    private UserModel user;
    private SharedPreferences sharedPreferences;

    private EditText updateFirstNameInput;
    private TextView updateFirstNameInputErrorMessage;
    private EditText updateLastNameInput;
    private TextView updateLastNameInputErrorMessage;
    private EditText updateEmailInput;
    private TextView updateEmailInputErrorMessage;
    private EditText updatePasswordInput;
    private TextView updatePasswordInputErrorMessage;
    private EditText updateConfirmPasswordInput;
    private TextView updateConfirmPasswordInputErrorMessage;
    private EditText updateAddressInput;
    private TextView updateAddressInputErrorMessage;

    private Button updateProfileInformationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDAO = new UserDAO(this);
        sharedPreferences = App.getSharedPreferences();
        user = userDAO.getUser(sharedPreferences.getString("user_email", ""));

        updateFirstNameInput = findViewById(R.id.update_first_name_input);
        updateLastNameInput = findViewById(R.id.update_last_name_input);
        updateEmailInput = findViewById(R.id.update_email_input);
        updatePasswordInput = findViewById(R.id.update_password_input);
        updateConfirmPasswordInput = findViewById(R.id.update_confirm_password_input);
        updateAddressInput = findViewById(R.id.update_address_input);

        updateFirstNameInputErrorMessage = findViewById(R.id.update_first_name_input_error_message);
        updateLastNameInputErrorMessage = findViewById(R.id.update_last_name_input_error_message);
        updateEmailInputErrorMessage = findViewById(R.id.update_email_input_error_message);
        updatePasswordInputErrorMessage = findViewById(R.id.update_password_input_error_message);
        updateConfirmPasswordInputErrorMessage = findViewById(R.id.update_confirm_password_input_error_message);
        updateAddressInputErrorMessage = findViewById(R.id.update_address_input_error_message);

        updateProfileInformationButton = findViewById(R.id.update_profile_information_button);
        updateProfileInformationButton.setOnClickListener(v -> onUpdateProfileInformationButtonClicked());

        updateFirstNameInput.setText(user.getFirstName());
        updateLastNameInput.setText(user.getLastName());
        updateEmailInput.setText(user.getEmail());
        updateAddressInput.setText(user.getAddress());

    }

    public void onUpdateProfileInformationButtonClicked() {
        String firstName = updateFirstNameInput.getText().toString();
        String lastName = updateLastNameInput.getText().toString();
        String email = updateEmailInput.getText().toString();
        String password = updatePasswordInput.getText().toString();
        String confirmPassword = updateConfirmPasswordInput.getText().toString();
        String address = updateAddressInput.getText().toString();

        boolean isValid = true;

        if (firstName.isEmpty()) {
            updateFirstNameInputErrorMessage.setVisibility(View.VISIBLE);
            updateFirstNameInputErrorMessage.setText(R.string.first_name_cannot_be_empty);
            isValid = false;
        } else {
            updateFirstNameInputErrorMessage.setVisibility(View.GONE);
        }

        if (lastName.isEmpty()) {
            updateLastNameInputErrorMessage.setVisibility(View.VISIBLE);
            updateLastNameInputErrorMessage.setText(R.string.last_name_cannot_be_empty);
            isValid = false;
        } else {
            updateLastNameInputErrorMessage.setVisibility(View.GONE);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            updateEmailInputErrorMessage.setVisibility(View.VISIBLE);
            updateEmailInputErrorMessage.setText(R.string.invalid_email);
            isValid = false;
        } else {
            updateEmailInputErrorMessage.setVisibility(View.GONE);
        }

        if (!password.isEmpty() || !confirmPassword.isEmpty()) {
            if (password.isEmpty()) {
                updatePasswordInputErrorMessage.setVisibility(View.VISIBLE);
                updatePasswordInputErrorMessage.setText(R.string.password_cannot_be_empty);
                isValid = false;
            } else if (!password.equals(confirmPassword)) {
                updateConfirmPasswordInputErrorMessage.setVisibility(View.VISIBLE);
                updateConfirmPasswordInputErrorMessage.setText(R.string.passwords_do_not_match);
                isValid = false;
            } else {
                updatePasswordInputErrorMessage.setVisibility(View.GONE);
                updateConfirmPasswordInputErrorMessage.setVisibility(View.GONE);
            }
        }

        if (address.isEmpty()) {
            updateAddressInputErrorMessage.setVisibility(View.VISIBLE);
            updateAddressInputErrorMessage.setText(R.string.address_cannot_be_empty);
            isValid = false;
        } else {
            updateAddressInputErrorMessage.setVisibility(View.GONE);
        }


        if (isValid) {
            userDAO.updateProfileInformation(user.getId(), firstName, lastName, email, password, address);

            UserModel user = userDAO.getUser(sharedPreferences.getInt("user_id", 0));
            sharedPreferences.edit()
                    .putString("user_email", user.getEmail())
                    .putString("user_first_name", user.getFirstName())
                    .putString("user_last_name", user.getLastName())
                    .apply();
            Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}