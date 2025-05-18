package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities;

import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.R;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters.CampAdapter;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters.UpcomingCampAdapter;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.Camp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParentDashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CardView cardFindCamps, cardMyApplications, cardMyChildren, cardRegistrations, cardProfile;
    private RecyclerView recyclerPopularCamps, recyclerUpcomingCamps;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        initializeViews();

        // Set up event listeners
        setupListeners();

        // Set up recycler views
        setupRecyclerViews();

        // Set up bottom navigation
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    private void initializeViews() {
        cardFindCamps = findViewById(R.id.cardFindCamps);
        cardMyApplications = findViewById(R.id.cardMyApplications);
        cardMyChildren = findViewById(R.id.cardMyChildren);
        cardRegistrations = findViewById(R.id.cardRegistrations);
        cardProfile = findViewById(R.id.cardProfile);
        recyclerPopularCamps = findViewById(R.id.recyclerPopularCamps);
        recyclerUpcomingCamps = findViewById(R.id.recyclerUpcomingCamps);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupListeners() {
        cardFindCamps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to find camps activity
                // Intent intent = new Intent(ParentDashboardActivity.this, FindCampsActivity.class);
                // startActivity(intent);
            }
        });


        cardMyApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to my children activity
                Intent intent = new Intent(ParentDashboardActivity.this, CampApplicationActivity.class);
                startActivity(intent);
            }
        });

        cardMyChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to my children activity
                Intent intent = new Intent(ParentDashboardActivity.this, MyChildrenActivity.class);
                startActivity(intent);
            }
        });

        cardRegistrations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to registrations activity
                Intent intent = new Intent(ParentDashboardActivity.this, ParentDashboardActivity.class); // TODO: CampRegistrationsActivity implementálása, a 2. mérföldkő után
                startActivity(intent);
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to profile activity
                Intent intent = new Intent(ParentDashboardActivity.this, ParentDashboardActivity.class); // TODO: ProfileActivity implementálása, a 2. mérföldkő után
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerViews() {
        // Set up horizontal layout for popular camps
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPopularCamps.setLayoutManager(horizontalLayoutManager);

        // Set up vertical layout for upcoming camps
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
        recyclerUpcomingCamps.setLayoutManager(verticalLayoutManager);

        // Load sample data for demo
        loadSampleCamps();
    }

    private void loadSampleCamps() {
        // In a real app, this would come from a database or API
        List<Camp> popularCamps = getSamplePopularCamps();
        List<Camp> upcomingCamps = getSampleUpcomingCamps();

        // Set adapters with sample data
        CampAdapter popularAdapter = new CampAdapter(this, (ArrayList<Camp>) popularCamps);
        recyclerPopularCamps.setAdapter(popularAdapter);

        UpcomingCampAdapter upcomingAdapter = new UpcomingCampAdapter(this, upcomingCamps);
        recyclerUpcomingCamps.setAdapter(upcomingAdapter);
    }

    private List<Camp> getSamplePopularCamps() {
        List<Camp> camps = new ArrayList<>();

        // Sample data
        Camp camp1 = new Camp();
        camp1.setId(1);
        camp1.setName("Cserkész Kalandtábor");
        camp1.setType("cserkésztábor");
        camp1.setStartDate(new Date(2025, 6, 10)); // Month is 0-based
        camp1.setEndDate(new Date(2025, 6, 20));
        camp1.setLocation("Balaton, Tihany");
        camps.add(camp1);

        Camp camp2 = new Camp();
        camp2.setId(2);
        camp2.setName("Vitorlás Élménytábor");
        camp2.setType("vitorlástábor");
        camp2.setStartDate(new Date(2025, 6, 15));
        camp2.setEndDate(new Date(2025, 6, 25));
        camp2.setLocation("Balaton, Balatonfüred");
        camps.add(camp2);

        Camp camp3 = new Camp();
        camp3.setId(3);
        camp3.setName("Robotika Tábor");
        camp3.setType("robotika tábor");
        camp3.setStartDate(new Date(2025, 7, 5));
        camp3.setEndDate(new Date(2025, 7, 12));
        camp3.setLocation("Budapest");
        camps.add(camp3);

        Camp camp4 = new Camp();
        camp4.setId(4);
        camp4.setName("Erdei Vándortábor");
        camp4.setType("erdei vándortábor");
        camp4.setStartDate(new Date(2025, 7, 10));
        camp4.setEndDate(new Date(2025, 7, 17));
        camp4.setLocation("Mátra");
        camps.add(camp4);

        return camps;
    }

    private List<Camp> getSampleUpcomingCamps() {
        // For the demo, we'll use the same camps as popular camps
        return getSamplePopularCamps();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            // Already on home
            return true;
        } else if (itemId == R.id.nav_camps) {
            // Navigate to camps
            // Intent intent = new Intent(ParentDashboardActivity.this, CampsActivity.class);
            // startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_children) {
            // Navigate to children
            // Intent intent = new Intent(ParentDashboardActivity.this, MyChildrenActivity.class);
            // startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_profile) {
            // Navigate to profile
            // Intent intent = new Intent(ParentDashboardActivity.this, ProfileActivity.class);
            // startActivity(intent);
            return true;
        }

        return false;
    }
}
