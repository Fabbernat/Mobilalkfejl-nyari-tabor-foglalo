package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.BrowseCampsActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.CampCardActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.CustomMenuActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.FallbackActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.GalleryActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.ListCampsActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.LoginActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.ParentDashboardActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.RegisterActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.SimpleListCampActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.activities.UpcomingCampActivity;
import com.example.mobilalkfejl_nyari_tabor_foglalo.utils.CampAsyncTask;
import com.example.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilalkfejl_nyari_tabor_foglalo.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;
    private static final int RC_SIGN_IN = 123;

    EditText userNameET;
    EditText passwordET;

    private SharedPreferences preferences;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Alternatively could be `setContentView(R.layout.activity_main);`, but this is safer

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Táborok böngészése", Snackbar.LENGTH_LONG)
                        .setAction("BrowseCamps", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        Button button = findViewById(R.id.loginAsGuestButton);
        new CampAsyncTask(button).execute();

        getSupportLoaderManager().restartLoader(0, null, this);
        Log.i(LOG_TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void openBrowseCamps(View view) {
        startActivity(new Intent(this, BrowseCampsActivity.class));
    }

    public void openListCamps(View view) {
        startActivity(new Intent(this, ListCampsActivity.class));
    }

    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openParentDashboard(View view) {
        startActivity(new Intent(this, ParentDashboardActivity.class));
    }

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void openCustomMenu(View view) {
        startActivity(new Intent(this, CustomMenuActivity.class));
    }

    public void openCampCard(View view) {
        startActivity(new Intent(this, CampCardActivity.class));
    }

    public void openGallery(View view) {
        startActivity(new Intent(this, GalleryActivity.class));
    }

    public void openUpcomingCamp(View view) {
        startActivity(new Intent(this, UpcomingCampActivity.class));
    }

    public void openSimpleListCamp(View view) {
        startActivity(new Intent(this, SimpleListCampActivity.class));
    }

    public void openFallback(View view) {
        startActivity(new Intent(this, FallbackActivity.class));
    }
}