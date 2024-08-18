package com.adsandakannipunajith.puplify.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.R;

public class UpdatePaymentMethodActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private EditText updateCardHolderNameInput;
    private TextView updateCardHolderNameInputErrorMessage;
    private EditText updateCardNumberInput;
    private TextView updateCardNumberInputErrorMessage;
    private EditText updateExpirationDateInput;
    private TextView updateExpirationDateInputErrorMessage;
    private EditText updateCVVInput;
    private TextView updateCVVInputErrorMessage;

    private Button updatePaymentMethodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_payment_method);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = App.getSharedPreferences();

        updateCardHolderNameInput = findViewById(R.id.update_card_holder_name_input);
        updateCardHolderNameInputErrorMessage = findViewById(R.id.update_card_holder_name_input_error_message);
        updateCardNumberInput = findViewById(R.id.update_card_number_input);
        updateCardNumberInputErrorMessage = findViewById(R.id.update_card_number_input_error_message);
        updateExpirationDateInput = findViewById(R.id.update_expiration_date_input);
        updateExpirationDateInputErrorMessage = findViewById(R.id.update_expiration_date_input_error_message);
        updateCVVInput = findViewById(R.id.update_cvv_input);
        updateCVVInputErrorMessage = findViewById(R.id.update_cvv_input_error_message);
        updatePaymentMethodButton = findViewById(R.id.update_payment_method_button);

        updateCardHolderNameInput.setText(sharedPreferences.getString("card_holder_name", ""));
        updateCardNumberInput.setText(sharedPreferences.getString("card_number", ""));
        updateExpirationDateInput.setText(sharedPreferences.getString("expiration_date", ""));
        updateCVVInput.setText(sharedPreferences.getString("cvv", ""));
    }
}