package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities;

import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements android.widget.AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    // FireStore adatbázis a CRUD műveletekhez
    private FirebaseFirestore db;
    private List<Map<String, Object>> userDataCache;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    EditText phoneEditText;
    android.widget.Spinner spinner;
    EditText addressEditText;
    RadioGroup userTypeRadioGroup;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_modes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        // CRUD műveletekhez inicializáljuk a FireStore adatbázist
        db = FirebaseFirestore.getInstance();
        userDataCache = new ArrayList<>();

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
         startBrowsingCamps();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * @param task Google(Firebase) bejelentkezéshez
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "createUserWithEmail:success");
                    startBrowsingCamps();
                } else {
                    Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startBrowsingCamps(/* Regsiztralt user adatai*/) {
        Intent intent = new Intent(this, BrowseCampsActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }


    // Életciklus hookok: újraindításkor toast és /vagy Loggolás

    // Firebase Firestore - CREATE művelet
    private void saveUserDataToFirestore(String userName, String email, String phoneNumber, String phoneType, String address, String userType) {
        // Felhasználói adatok elkészítése
        Map<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("phoneType", phoneType);
        userData.put("address", address);
        userData.put("userType", userType);
        userData.put("registrationDate", System.currentTimeMillis());

        // Adatok mentése a Firestore adatbázisba
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("users")
                .document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(LOG_TAG, "Felhasználói adatok sikeresen mentve!");
                    Toast.makeText(RegisterActivity.this, "Regisztráció sikeres!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(LOG_TAG, "Hiba a felhasználói adatok mentése közben", e);
                    Toast.makeText(RegisterActivity.this, "Adatmentési hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Lifecycle Hook start - READ művelet
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Foglalási oldal elindult", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onStart");

        // READ művelet - Összes regisztrált felhasználó lekérdezése
        fetchAllUsers();
    }

    // READ művelet implementálása
    private void fetchAllUsers() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userDataCache.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userDataCache.add(document.getData());
                            Log.d(LOG_TAG, "Felhasználó: " + document.getId() + " => " + document.getData());
                        }
                        Log.d(LOG_TAG, "Összes felhasználó sikeresen lekérdezve, darabszám: " + userDataCache.size());
                    } else {
                        Log.w(LOG_TAG, "Hiba a felhasználók lekérdezése közben", task.getException());
                    }
                });
    }

    // Lifecycle Hook stop - UPDATE művelet
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Foglalási oldal leáll", Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "onStop");

        // UPDATE művelet - Felhasználói bejelentkezési státusz frissítése
        updateUserStatus(false);
    }

    // UPDATE művelet implementálása
    private void updateUserStatus(boolean isActive) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            Map<String, Object> updates = new HashMap<>();
            updates.put("isActive", isActive);
            updates.put("lastSeen", System.currentTimeMillis());

            db.collection("users")
                    .document(userId)
                    .update(updates)
                    .addOnSuccessListener(aVoid -> Log.d(LOG_TAG, "Felhasználói státusz sikeresen frissítve!"))
                    .addOnFailureListener(e -> Log.w(LOG_TAG, "Hiba a felhasználói státusz frissítése közben", e));
        }
    }

    // Lifecycle Hook destroy - DELETE művelet
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");

        // Töröljük az ideiglenes adatokat vagy akár a felhasználót is ha szükséges
        clearTemporaryData();
    }

    // DELETE művelet implementálása
    private void clearTemporaryData() {
        // Töröljük az ideiglenes adatokat
        userDataCache.clear();

        // Példa a felhasználó törlésére (csak megfelelő jogosultság esetén)
        if (mAuth.getCurrentUser() != null && isAdmin()) {
            String userIdToDelete = "specificUserId"; // Ez csak példa

            db.collection("users")
                    .document(userIdToDelete)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d(LOG_TAG, "Felhasználó sikeresen törölve!"))
                    .addOnFailureListener(e -> Log.w(LOG_TAG, "Hiba a felhasználó törlése közben", e));
        }
    }

    // Csak adminisztrátorok törölhetnek felhasználókat
    private boolean isAdmin() {
        // Itt ellenőrizhető, hogy a felhasználó admin-e
        // Egyszerűsített példa:
        return false; // Alapértelmezetten nem admin
    }

    // Lifecycle Hook pause - CREATE művelet (aktivitási napló)
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");

        // CREATE művelet - Aktivitási napló készítése
        logActivityEvent("RegisterActivity paused");
    }

    // Aktivitási napló készítése
    private void logActivityEvent(String eventDescription) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();

            Map<String, Object> logEntry = new HashMap<>();
            logEntry.put("userId", userId);
            logEntry.put("event", eventDescription);
            logEntry.put("timestamp", System.currentTimeMillis());

            db.collection("activity_logs")
                    .add(logEntry)
                    .addOnSuccessListener(documentReference ->
                            Log.d(LOG_TAG, "Aktivitási napló sikeresen mentve: " + documentReference.getId()))
                    .addOnFailureListener(e ->
                            Log.w(LOG_TAG, "Hiba az aktivitási napló mentése közben", e));
        }
    }

    // Lifecycle Hook resume - READ művelet (felhasználói adatok)
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");

        // READ művelet - Felhasználói adatok frissítése
        updateUserStatus(true);
        fetchCurrentUserData();
    }

    // Aktuális felhasználó adatainak lekérdezése
    private void fetchCurrentUserData() {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();

            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> userData = documentSnapshot.getData();
                            Log.d(LOG_TAG, "Aktuális felhasználó adatai: " + userData);

                            // Itt akár frissíthetjük a UI-t a lekérdezett adatokkal
                            assert userData != null;
                            updateUIWithUserData(userData);
                        } else {
                            Log.d(LOG_TAG, "A felhasználói dokumentum nem létezik");
                        }
                    })
                    .addOnFailureListener(e -> Log.w(LOG_TAG, "Hiba a felhasználói adatok lekérdezése közben", e));
        }
    }

    // UI frissítése a felhasználói adatokkal
    private void updateUIWithUserData(Map<String, Object> userData) {
        // Itt frissíthetjük a UI-t a felhasználói adatokkal
        if (userData.containsKey("userName")) {
            String userName = (String) userData.get("userName");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                assert userName != null;
                if (!userName.isEmpty() && userNameEditText != null) {
                    userNameEditText.setText(userName);
                }
            }
        }

        if (userData.containsKey("email")) {
            String email = (String) userData.get("email");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                assert email != null;
                if (!email.isEmpty() && userEmailEditText != null) {
                    userEmailEditText.setText(email);
                }
            }
        }

        // További mezők frissítése...
    }

    // Lifecycle Hook restart - UPDATE művelet (felhasználói preferenciák)
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");

        // UPDATE művelet - Felhasználói preferenciák frissítése
        updateUserPreferences();
    }

    // Felhasználói preferenciák frissítése
    private void updateUserPreferences() {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();

            // Az aktuális preferenciák lekérdezése a SharedPreferences-ből
            SharedPreferences prefs = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
            boolean notificationsEnabled = prefs.getBoolean("notifications_enabled", true);
            boolean darkModeEnabled = prefs.getBoolean("dark_mode_enabled", false);

            // Frissítjük az adatbázist a legújabb preferenciákkal
            Map<String, Object> preferences = new HashMap<>();
            preferences.put("notificationsEnabled", notificationsEnabled);
            preferences.put("darkModeEnabled", darkModeEnabled);

            db.collection("users")
                    .document(userId)
                    .collection("preferences")
                    .document("settings")
                    .set(preferences)
                    .addOnSuccessListener(aVoid -> Log.d(LOG_TAG, "Felhasználói preferenciák sikeresen frissítve!"))
                    .addOnFailureListener(e -> Log.w(LOG_TAG, "Hiba a felhasználói preferenciák frissítése közben", e));
        }
    }

    // Lifecycle Hook onItemSelected - READ művelet
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, "Selected item: " + selectedItem);

        // READ művelet - Lekérdezzük az adott telefonszám típussal rendelkező felhasználókat
        fetchUsersByPhoneType(selectedItem);
    }

    // Telefonszám típus alapján felhasználók lekérdezése
    private void fetchUsersByPhoneType(String phoneType) {
        db.collection("users")
                .whereEqualTo("phoneType", phoneType)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> users = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            users.add(document.getData());
                        }
                        Log.d(LOG_TAG, phoneType + " típusú telefonnal rendelkező felhasználók száma: " + users.size());
                    } else {
                        Log.w(LOG_TAG, "Hiba a telefonszám típus szerinti lekérdezés közben", task.getException());
                    }
                });
    }

    // Lifecycle Hook onNothingSelected - CREATE művelet (log)
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(LOG_TAG, "onNothingSelected meghívva");
        Toast.makeText(this, "Nincs kiválasztott elem", Toast.LENGTH_SHORT).show();

        // CREATE művelet - Log bejegyzés készítése a nem-kiválasztásról
        logNoSelectionEvent("Nem történt telefonszám típus kiválasztása");
    }

    // Nem-kiválasztási esemény naplózása
    private void logNoSelectionEvent(String message) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("event", "no_selection");
        logEntry.put("message", message);
        logEntry.put("timestamp", System.currentTimeMillis());

        db.collection("app_logs")
                .add(logEntry)
                .addOnSuccessListener(documentReference ->
                        Log.d(LOG_TAG, "Nem-kiválasztási esemény sikeresen naplózva: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.w(LOG_TAG, "Hiba a nem-kiválasztási esemény naplózása közben", e));
    }
}