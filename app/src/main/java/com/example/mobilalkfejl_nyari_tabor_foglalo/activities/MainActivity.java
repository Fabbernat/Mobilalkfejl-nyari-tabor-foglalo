package com.example.mobilalkfejl_nyari_tabor_foglalo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.mobilalkfejl_nyari_tabor_foglalo.CampAsyncLoader;
import com.example.mobilalkfejl_nyari_tabor_foglalo.GoogleSignIn;
import com.example.mobilalkfejl_nyari_tabor_foglalo.GoogleSignInClient;
import com.example.mobilalkfejl_nyari_tabor_foglalo.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;
    private static final int RC_SIGN_IN = 123;

    EditText userNameET;
    EditText passwordET;

    private SharedPreferences preferences;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        });



        userNameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        Button button = findViewById(R.id.loginAsGuestButton);
//        new CampAsyncTask(button).execute();

        getSupportLoaderManager().restartLoader(0, null, this);
        Log.i(LOG_TAG, "onCreate");

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
    }

    private void startBrowsingCamps(/* Regsiztralt user adatai*/){
        Intent intent = new Intent(this, BrowseCampsActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void login(View view) {
        EditText userName = findViewById(R.id.editTextUserName);
        EditText password = findViewById(R.id.editTextPassword);

        String userNameStr = userName.getText().toString();
        String passwordStr = password.getText().toString();

        mAuth.signInWithEmailAndPassword(userNameStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * @param task Google(Firebase) bejelentkezéshez
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "signInWithEmail:success");
                    startBrowsingCamps();
                } else {
                    Log.w(LOG_TAG, "signInWithEmail:failure", task.getException());
                }
            }
        }
        );

        Log.i(LOG_TAG, "Bejelentkezett: " + userNameStr);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(LOG_TAG, "Google sign in success: " + account.getId());
                startBrowsingCamps();
            } catch (ApiException e){
                Log.w(LOG_TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * @param task Google(Firebase) bejelentkezéshez
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "GoogleLogin:success");
                    startBrowsingCamps();
                } else {
                    Log.w(LOG_TAG, "GoogleLogin:failure", task.getException());
                }
            }
        });
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivity(signInIntent);

        AuthCredential credential = null;
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    /**
                     * @param task Google(Firebase) bejelentkezéshez
                     */
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, "GoogleLogin:success");
                            startBrowsingCamps();
                        } else {
                            Log.w(LOG_TAG, "GoogleLogin:failure", task.getException());
                        }
                    }
                }
        );
    }

    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * @param task
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "AnonymousLogin:success");
                    startBrowsingCamps();
                } else {
                    Log.w(LOG_TAG, "AnonymousLogin:failure", task.getException());
                }
            }
        });
    }

    public void loginWithFacebook(View view) {

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new CampAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Button button = findViewById(R.id.loginAsGuestButton);
        button.setText(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
