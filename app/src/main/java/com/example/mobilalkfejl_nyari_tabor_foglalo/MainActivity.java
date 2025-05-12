package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilalkfejl_nyari_tabor_foglalo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Alternatively could be `setContentView(R.layout.activity_main);`, but this is safer

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
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