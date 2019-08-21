package com.shemuchemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shemuchemi.R;
import com.shemuchemi.helpers.InputValidation;
import com.shemuchemi.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;


    private AppCompatSpinner appCompatSpinner;
    private ArrayAdapter<CharSequence> adapter;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView appCompatTextViewRegisterLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();
        initViews();
        initObjects();
        initListeners();

        adapter = ArrayAdapter.createFromResource( this,R.array.user_type,R.layout.support_simple_spinner_dropdown_item );
        appCompatSpinner.setAdapter( adapter );

    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);

        appCompatSpinner = findViewById(R.id.appCompactSpinner);

        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);

        appCompatTextViewRegisterLink = findViewById(R.id.appCompatTextViewRegisterLink);

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        appCompatTextViewRegisterLink.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                emptyInputEditText();
                break;
            case R.id.appCompatTextViewRegisterLink:
                // Navigate to registger link
                Intent  intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        //Check the values in our database and then control the layout
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            String item = appCompatSpinner.getSelectedItem().toString();
            if (item.equals("Customer")){
                Intent customerIntent = new Intent(activity, CustomerActivity.class);
                startActivity(customerIntent);
            }
            if (databaseHelper.checkUser( textInputEditTextEmail.getText().toString().trim()
                    , textInputEditTextPassword.getText().toString().trim())){
                String item1 = appCompatSpinner.getSelectedItem().toString();
                if (item1.equals("Distributor")){
                    Intent distributorIntent = new Intent(activity, OrdersListActivity.class);
                    startActivity(distributorIntent);
                }
            }
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
