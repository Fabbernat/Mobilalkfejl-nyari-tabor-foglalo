package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilalkfejl_nyari_tabor_foglalo.models.Camp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BrowseCampsActivity extends AppCompatActivity {
    public static final String LOG_TAG = BrowseCampsActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth auth;

    private RecyclerView mRecyclerView;
    private ArrayList<Camp> mCampList;
    private CampAdapter mAdapter;

    private FrameLayout redCircle;
    private TextView contentTextView;

    private int gridNumber = 1;
    private int starredCampsCount = 0;
    private boolean viewRow = false;

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
            mCampList.add(new Camp(
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

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.camp_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_TAG, "query: " + newText);
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out_button) {
            Log.d(LOG_TAG, "Log out button pressed!");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else if (id == R.id.setting_button) {
            Log.d(LOG_TAG, "Setting button pressed!");
            return true;
        } else if (id == R.id.starred_camps) {
            Log.d(LOG_TAG, "Starred camps button pressed!");
            return true;
        } else if (id == R.id.view_selector) {
            Log.d(LOG_TAG, "View selector button pressed!");
            if (viewRow) {
                changeSpanCount(item, R.drawable.baseline_view_grid_24, 1);
            } else {
                changeSpanCount(item, R.drawable.ic_launcher_background, 2);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        assert layoutManager != null;
        layoutManager.setSpanCount(spanCount);
    }

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     *
     * <p>The default implementation updates the system menu items based on the
     * activity's state.  Deriving classes should always call through to the
     * base class implementation.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.camp_image);
        assert alertMenuItem != null;
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        assert rootView != null;
        redCircle = rootView.findViewById(R.id.view_alert_red_circle);
        contentTextView = rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(view -> onOptionsMenuClosed((Menu) alertMenuItem));

        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon(){
        starredCampsCount++;
        if (starredCampsCount > 0) {
            contentTextView.setText(String.valueOf(starredCampsCount));
        } else {
            contentTextView.setText("");
        }
    }
}
