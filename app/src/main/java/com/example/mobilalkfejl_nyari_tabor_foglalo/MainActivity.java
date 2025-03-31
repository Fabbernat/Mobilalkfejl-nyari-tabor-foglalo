package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText userNameET;
    EditText passwordET;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        /* Ez a kódrészlet a WindowInsets kezelésére szolgál, amely biztosítja, hogy a UI elemek megfelelően igazodjanak a rendszer sávjaihoz (például státuszsáv, navigációs sáv) Android rendszeren.
         * a jelenleg nincs vizuális probléma az elrendezésben, érdemes ezt a kódrészletet ideiglenesen kikommentezni és tesztelni, hogy minden UI elem jól jelenik-e meg. Ha nem tapasztalsz hibát, akkor valószínűleg nincs rá szükség.
         * Ha később fullscreen vagy gesture-based navigációs módot szeretnél implementálni, ez a kódrészlet még jól jöhet.
         *
         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userNameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        Log.i(LOG_TAG, "onCreate");
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

        String usernameStr = userNameET.getText().toString();
        String passwordStr = passwordET.getText().toString();

        android.util.Log.i(LOG_TAG, "Bejelentkezett: " + usernameStr + ", jelszó: " + passwordStr);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);

        startActivity(intent);

        EditText userName = findViewById(R.id.editTextUserName);
        EditText password = findViewById(R.id.editTextPassword);
        if (userName.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            userName.setError(null);
            password.setError(null);
        }
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userNameET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.apply();

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
}
