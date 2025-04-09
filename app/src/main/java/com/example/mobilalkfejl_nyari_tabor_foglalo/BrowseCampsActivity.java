package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilalkfejl_nyari_tabor_foglalo.models.CampModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BrowseCampsActivity extends AppCompatActivity {
    public static final String LOG_TAG = BrowseCampsActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth auth;

    private RecyclerView mRecyclerView;
    private ArrayList<CampModel> mCampList;
    private CampAdapter mAdapter;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse_camps);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Sikeresen bejelentkezett ezzel a felhasználónévvel: " + user.getEmail());
        } else {
            Log.d(LOG_TAG, "Nincs bejelentkezett felhasználó!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mCampList = new ArrayList<>();

        mAdapter = new CampAdapter(this, mCampList);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
        // auto-generated
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void initializeData() {
        String[] campsTypesList = getResources().getStringArray(R.array.camp_types);
        String[] campsFormatsList = getResources().getStringArray(R.array.camp_format);
        String[] campsPricesList = getResources().getStringArray(R.array.camp_prices);
        String[] campUserTypesList = getResources().getStringArray(R.array.user_type);
        String[] campOrganizerTypesList = getResources().getStringArray(R.array.organizer_type);

        TypedArray campRatingsList = getResources().obtainTypedArray(R.array.camp_ratings);
        TypedArray campsImageResourcesList = getResources().obtainTypedArray(R.array.camp_images);

        mCampList.clear();

        for (int i = 0; i < campsTypesList.length; i++) {

            // TODO Fix
            mCampList.add(new CampModel(
                    campsTypesList[i],
                    campsFormatsList[i],
                    campsPricesList[i],
                    campRatingsList.getFloat(i, 0),
                    campsImageResourcesList.getResourceId(i, 0),
                    campUserTypesList[i],
                    campOrganizerTypesList[i]
            ));
        }


        // Fontos! Felszabadítás
        campRatingsList.recycle();
        campsImageResourcesList.recycle();


        mAdapter.notifyDataSetChanged();
    }


}
