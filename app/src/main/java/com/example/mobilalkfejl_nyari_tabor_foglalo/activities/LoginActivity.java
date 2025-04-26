package com.example.mobilalkfejl_nyari_tabor_foglalo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mobilalkfejl_nyari_tabor_foglalo.R;


public class LoginActivity extends AppCompatActivity {
    private CardView cardParent, cardTeacher, cardStaff, cardAdmin;
    private Button btnLogin;
    private String selectedUserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        cardParent = findViewById(R.id.cardParent);
        cardTeacher = findViewById(R.id.cardTeacher);
        cardStaff = findViewById(R.id.cardStaff);
        cardAdmin = findViewById(R.id.cardAdmin);
        btnLogin = findViewById(R.id.btnLogin);

        // Set up click listeners
        setupCardClickListeners();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedUserType.isEmpty()) {
                    // Navigate to appropriate registration/login screen based on selected type
                    navigateToRegistration(selectedUserType);
                }
            }
        });
    }

    private void setupCardClickListeners() {
        // Create a card selection listener
        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset all cards to default state
                resetCardSelection();

                // Highlight the selected card
                v.setElevation(16);

                // Store the selected user type
                if (v.getId() == R.id.cardParent) {
                    selectedUserType = "parent";
                } else if (v.getId() == R.id.cardTeacher) {
                    selectedUserType = "teacher";
                } else if (v.getId() == R.id.cardStaff) {
                    selectedUserType = "staff";
                } else if (v.getId() == R.id.cardAdmin) {
                    selectedUserType = "admin";
                }

                // Enable the login button
                btnLogin.setEnabled(true);
                btnLogin.setAlpha(1.0f);
            }
        };

        // Set the listener to all cards
        cardParent.setOnClickListener(cardClickListener);
        cardTeacher.setOnClickListener(cardClickListener);
        cardStaff.setOnClickListener(cardClickListener);
        cardAdmin.setOnClickListener(cardClickListener);

        // Initially disable the login button
        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.5f);
    }

    private void resetCardSelection() {
        cardParent.setElevation(4);
        cardTeacher.setElevation(4);
        cardStaff.setElevation(4);
        cardAdmin.setElevation(4);
    }

    private void navigateToRegistration(String userType) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("USER_TYPE", userType);
        startActivity(intent);
    }
}
