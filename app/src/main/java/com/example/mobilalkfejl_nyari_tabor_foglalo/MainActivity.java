package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        EditText userName = findViewById(R.id.editTextUserName);
        EditText password = findViewById(R.id.editTextPassword);
        if (userName.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            userName.setError(null);
            password.setError(null);
        } else {
            userName.setError("Invalid username or password");
            password.setError("Invalid username or password");
        }

        String usernameStr = userName.getText().toString();
        String passwordStr = password.getText().toString();

        android.util.Log.i(LOG_TAG, "Bejelentkezett: " + usernameStr + ", jelszó: " + passwordStr);
    }
}
