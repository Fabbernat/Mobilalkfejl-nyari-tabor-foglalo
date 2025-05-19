package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities;

import static android.os.Build.VERSION_CODES.R;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignIn;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    EditText editUserName, passwordEdit;
    private CardView cardParent, cardTeacher, cardStaff, cardAdmin;
    private Button btnLogin;
    private String selectedUserType = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editUserName = findViewById(R.id.editUserName);
        passwordEdit = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = editUserName.getText().toString();
            String password = passwordEdit.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showLoginNotification();
                            startActivity(new Intent(this, BrowseCampsActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Hibás belépés: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView haveAccountText = findViewById(R.id.textRegister);
        haveAccountText.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        // Google Sign-In config
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // ezt tedd bele strings.xml-be
                .requestEmail()
                .build();


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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!selectedUserType.isEmpty()) {
                        // Navigate to appropriate registration/login screen based on selected type
                        navigateToRegistration(selectedUserType);
                    }
                }
            }
        });
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.setElevation(16);
                }

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    btnLogin.setAlpha(1.0f);
                }
            }
        };

        // Set the listener to all cards
        cardParent.setOnClickListener(cardClickListener);
        cardTeacher.setOnClickListener(cardClickListener);
        cardStaff.setOnClickListener(cardClickListener);
        cardAdmin.setOnClickListener(cardClickListener);

        // Initially disable the login button
        btnLogin.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            btnLogin.setAlpha(0.5f);
        }
    }

    private void resetCardSelection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardParent.setElevation(4);
            cardTeacher.setElevation(4);
            cardStaff.setElevation(4);
            cardAdmin.setElevation(4);
        }
    }

    private void navigateToRegistration(String userType) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("USER_TYPE", userType);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void showLoginNotification() {
        String channelId = "login_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Login Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Értesítések bejelentkezés után");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(this, BrowseCampsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_login) // FONTOS: legyen létező ikon!
                .setContentTitle("Sikeres bejelentkezés")
                .setContentText("Üdvözlünk az Empress Cinema alkalmazásban!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(1001, builder.build());
        }

    }

}
