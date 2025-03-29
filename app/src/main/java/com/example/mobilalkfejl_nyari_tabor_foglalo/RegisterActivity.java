package com.example.mobilalkfejl_nyari_tabor_foglalo;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    EditText userNameET;
    EditText userEmailET;
    EditText passwordET;
    EditText confirmPasswordET;


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

        userNameET = findViewById(R.id.userNameEditText);
        userEmailET = findViewById(R.id.userEmailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        confirmPasswordET = findViewById(R.id.confirmPasswordEditText);
    }

    public void register(View view) {
        String userName = userNameET.getText().toString();
        String email = userEmailET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();

        if (!password.equals(confirmPassword)) {
            Log.e(LOG_TAG, "A megadott jelszavak nem egyeznek meg!");
            return;
        }

        Log.i(LOG_TAG, "Felhasználónév: " + userName + ", E-mail: " + email + ", Jelszó: " + password);
        // TODO: Implement real registration logic here
    }

    public void cancel(View view) {
        finish();
    }

    // TODO Override onStart onStop ... metodusok
}