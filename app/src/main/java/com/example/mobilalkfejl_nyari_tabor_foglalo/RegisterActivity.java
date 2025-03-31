package com.example.mobilalkfejl_nyari_tabor_foglalo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity implements android.widget.AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();


    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    EditText phoneEditText;
    android.widget.Spinner spinner;
    EditText addressEditText;
    RadioGroup userTypeRadioGroup;

    private SharedPreferences preferences;

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

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        bundle.getInt("SECRET_KEY");
        int SECRET_KEY = bundle.getInt("SECRET_KEY", 0);

        if (SECRET_KEY != 99) {
            finish();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        spinner = findViewById(R.id.phoneSpinner);
        spinner.setSelection(0);
        addressEditText = findViewById(R.id.addressEditText);
        userTypeRadioGroup = findViewById(R.id.userTypeRadioGroup);
        userTypeRadioGroup.check(R.id.parentRadioButton);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(userName);
        passwordEditText.setText(password);
        confirmPasswordEditText.setText(password);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.phone_modes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            Log.e(LOG_TAG, "A megadott jelszavak nem egyeznek meg!");
            return;
        }

        String phoneNumber = phoneEditText.getText().toString();
        String phoneType = spinner.getSelectedItem().toString();
        String address = addressEditText.getText().toString();

        int checkId = userTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = userTypeRadioGroup.findViewById(checkId);
        String userType = radioButton.getText().toString();

        Log.i(LOG_TAG, "Felhasználónév: " + userName + ", E-mail: " + email + ", Jelszó: " + password + ", Telefonszám: " + phoneNumber + ", Telefonszám típusa: " + phoneType + ", Cím: " + address);
        // TODO: Implement real registration logic here
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, "Selected item: " + selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }
}