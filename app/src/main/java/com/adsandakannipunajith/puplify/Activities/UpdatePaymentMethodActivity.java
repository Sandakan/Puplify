package com.adsandakannipunajith.puplify.Activities;

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
import com.adsandakannipunajith.puplify.DAO.PaymentMethodDAO;
import com.adsandakannipunajith.puplify.Models.PaymentMethodModel;
import com.adsandakannipunajith.puplify.R;

public class UpdatePaymentMethodActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private PaymentMethodDAO paymentMethodDAO;

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
        paymentMethodDAO = new PaymentMethodDAO();

        updateCardHolderNameInput = findViewById(R.id.update_card_holder_name_input);
        updateCardHolderNameInputErrorMessage = findViewById(R.id.update_card_holder_name_input_error_message);
        updateCardNumberInput = findViewById(R.id.update_card_number_input);
        updateCardNumberInputErrorMessage = findViewById(R.id.update_card_number_input_error_message);
        updateExpirationDateInput = findViewById(R.id.update_expiration_date_input);
        updateExpirationDateInputErrorMessage = findViewById(R.id.update_expiration_date_input_error_message);
        updateCVVInput = findViewById(R.id.update_cvv_input);
        updateCVVInputErrorMessage = findViewById(R.id.update_cvv_input_error_message);
        updatePaymentMethodButton = findViewById(R.id.update_payment_method_button);

        PaymentMethodModel paymentMethod = paymentMethodDAO.getPaymentMethod();


        if (paymentMethod != null) {
            updateCardHolderNameInput.setText(paymentMethod.getCardHolderName());
            updateCardNumberInput.setText(paymentMethod.getCardNumber());
            updateExpirationDateInput.setText(paymentMethod.getExpirationDate());
            updateCVVInput.setText(paymentMethod.getCvv());
        }

        updatePaymentMethodButton.setOnClickListener(v -> {
            String cardHolderName = updateCardHolderNameInput.getText().toString();
            String cardNumber = updateCardNumberInput.getText().toString();
            String expirationDate = updateExpirationDateInput.getText().toString();
            String cvv = updateCVVInput.getText().toString();

            boolean isValid = true;
            if (cardHolderName.isEmpty()) {
                updateCardHolderNameInputErrorMessage.setVisibility(View.VISIBLE);
                updateCardHolderNameInputErrorMessage.setText(R.string.card_holder_name_cannot_be_empty);
                isValid = false;
            } else {
                updateCardHolderNameInputErrorMessage.setVisibility(View.GONE);
            }
            if (cardNumber.isEmpty()) {
                updateCardNumberInputErrorMessage.setVisibility(View.VISIBLE);
                updateCardNumberInputErrorMessage.setText(R.string.card_number_cannot_be_empty);
                isValid = false;
            } else {
                updateCardNumberInputErrorMessage.setVisibility(View.GONE);
            }
            if (expirationDate.isEmpty()) {
                updateExpirationDateInputErrorMessage.setVisibility(View.VISIBLE);
                updateExpirationDateInputErrorMessage.setText(R.string.expiration_date_cannot_be_empty);
                isValid = false;
            } else {
                updateExpirationDateInputErrorMessage.setVisibility(View.GONE);
            }
            if (cvv.isEmpty()) {
                updateCVVInputErrorMessage.setVisibility(View.VISIBLE);
                updateCVVInputErrorMessage.setText(R.string.cvv_cannot_be_empty);
                isValid = false;
            }

            if (isValid) {
                paymentMethodDAO.updatePaymentMethod(cardNumber, cardHolderName, expirationDate, cvv);
                Toast.makeText(this, "Payment method updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}