package com.example.mobilalkfejl_nyari_tabor_foglalo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mobilalkfejl_nyari_tabor_foglalo.MainActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.R;
import com.example.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignIn;
import com.example.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.*;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";

    private CardView cardParent, cardTeacher, cardStaff, cardAdmin;
    private Button btnLogin;
    private String selectedUserType = "";

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Google Sign-In config
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // ezt tedd bele strings.xml-be
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

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

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign-in failed", e);
                Toast.makeText(this, "Sikertelen bejelentkezés", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        navigateToRegistration(selectedUserType);
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Firebase hitelesítés sikertelen.", Toast.LENGTH_SHORT).show();
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
