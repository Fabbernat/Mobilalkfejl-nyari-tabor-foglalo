package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import gen._base._base_java__assetres.srcjar.R;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.BrowseCampsActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.CampCardActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.CustomMenuActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.FallbackActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.GalleryActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.ListCampsActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.LoginActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.ParentDashboardActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.RegisterActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.SimpleListCampActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.UpcomingCampActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters.CampAdapter;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.Camp;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils.CampAsyncTask;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils.CampRepository;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignIn;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils.GoogleSignInClient;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();
    private static final int SECRET_KEY = 99;
    private static final int RC_SIGN_IN = 123;

    EditText userNameET;
    EditText passwordET;

    private SharedPreferences preferences;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView featuredCampsRecycler, allCampsRecycler;
    private CampAdapter featuredAdapter, allCampsAdapter;
    private CampRepository campRepository;
    private ProgressBar progressBar;
    private TextView promoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // CONFIG Alternatively could be `setContentView(R.layout.activity_main);`, but this is safer

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Táborok böngészése", Snackbar.LENGTH_LONG)
                        .setAction("BrowseCamps", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        promoText = findViewById(R.id.promo_text);
        promoText.setText(Html.fromHtml("Nyári táborok szervezője: <b>Fábián Bernát</b><br>Honlap: <b>fabbernat.github.io</b>"));

        // Initialize UI
        progressBar = findViewById(R.id.progress_bar);
        featuredCampsRecycler = findViewById(R.id.featured_camps_recycler);
        allCampsRecycler = findViewById(R.id.all_camps_recycler);

        // Set up RecyclerViews
        setupRecyclerViews();

        // Initialize repository
        campRepository = new CampRepository();

        // Load data
        loadFeaturedCamps();
        loadAllCamps();

        // Galéri megjelenítése
        Button galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        });

        // Oldalsó csúszka megjelenítése
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(LOG_TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(LOG_TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(LOG_TAG, "signInWithCredential:success");
                startShopping();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(LOG_TAG, "signInWithCredential:failure", task.getException());
            }
        });
    }

    public void login(View view) {
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();

        // Log.i(LOG_TAG, "Bejelentkezett: " + userName + ", jelszó: " + password);

        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "User loged in successfully");
                startShopping();
            } else {
                Log.d(LOG_TAG, "User log in fail");
                Toast.makeText(MainActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startShopping() {
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "Anonym user loged in successfully");
                startShopping();
            } else {
                Log.d(LOG_TAG, "Anonym user log in fail");
                Toast.makeText(MainActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
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


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new RandomLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Button anonym = findViewById(R.id.guestLoginButton);
        anonym.setText(data);
    }

    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private void setupRecyclerViews() {
        // Featured camps - horizontal scrolling
        featuredAdapter = new CampAdapter(true);
        featuredCampsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredCampsRecycler.setAdapter(featuredAdapter);
        featuredCampsRecycler.addItemDecoration(new ItemOffsetDecoration(16));

        // All camps - vertical scrolling
        allCampsAdapter = new CampAdapter(false);
        allCampsRecycler.setLayoutManager(new LinearLayoutManager(this));
        allCampsRecycler.setAdapter(allCampsAdapter);
        allCampsRecycler.addItemDecoration(new ItemOffsetDecoration(16));
    }

    private void loadFeaturedCamps() {
        progressBar.setVisibility(View.VISIBLE);

        // Using our complex query with pagination
        campRepository.getFeaturedCampsPaginated(5, 0)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        List<Camp> camps = task.getResult().toObjects(Camp.class);
                        featuredAdapter.setCamps(camps);
                    } else {
                        Toast.makeText(this, "Hiba a kiemelt táborok betöltésekor", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadAllCamps() {
        // Using another complex query
        campRepository.getCampsWithActivity("úszás", new Date())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Camp> camps = task.getResult().toObjects(Camp.class);
                        allCampsAdapter.setCamps(camps);
                    } else {
                        Toast.makeText(this, "Hiba a táborok betöltésekor", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openFabianWebsite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://fabbernat.github.io"));
        startActivity(browserIntent);
    }
}

