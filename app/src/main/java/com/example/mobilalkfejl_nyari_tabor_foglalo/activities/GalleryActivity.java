package com.example.mobilalkfejl_nyari_tabor_foglalo.activities;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilalkfejl_nyari_tabor_foglalo.R;
import com.example.mobilalkfejl_nyari_tabor_foglalo.adapters.GalleryAdapter;
import com.example.mobilalkfejl_nyari_tabor_foglalo.models.GalleryItem;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.gallery_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Add animation
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animation);

        // Create and set adapter
        GalleryAdapter adapter = new GalleryAdapter(getGalleryItems());
        recyclerView.setAdapter(adapter);
    }

    private List<GalleryItem> getGalleryItems() {
        List<GalleryItem> items = new ArrayList<>();

        // Add all images from your drawable folder
        items.add(new GalleryItem("cserkesztabor",
                getString(R.string.gallery_item1_title),
                getString(R.string.gallery_item1_desc),
                R.drawable.cserkesztabor));

        items.add(new GalleryItem("drcode",
                getString(R.string.gallery_item3_title),
                getString(R.string.gallery_item3_desc),
                R.drawable.drcode));

        items.add(new GalleryItem("forest-summer-camp",
                getString(R.string.gallery_item4_title),
                getString(R.string.gallery_item4_desc),
                R.drawable.forest_summer_camp));

        items.add(new GalleryItem("group_of_children_lying_in_the_grass_in_a_circle",
                getString(R.string.gallery_item5_title),
                getString(R.string.gallery_item5_desc),
                R.drawable.group_of_children_lying_in_the_grass_in_a_circle));

        items.add(new GalleryItem("island_camp",
                getString(R.string.gallery_item6_title),
                getString(R.string.gallery_item6_desc),
                R.drawable.island_camp));

        items.add(new GalleryItem("nyari_tabor_2022",
                getString(R.string.gallery_item7_title),
                getString(R.string.gallery_item7_desc),
                R.drawable.nyari_tabor_jatek));

        items.add(new GalleryItem("szent_margit",
                getString(R.string.gallery_item8_title),
                getString(R.string.gallery_item8_desc),
                R.drawable.szent_margit));

        items.add(new GalleryItem("szinjatszotabor",
                getString(R.string.gallery_item9_title),
                getString(R.string.gallery_item9_desc),
                R.drawable.szinjatszotabor));

        items.add(new GalleryItem("Zankai_Elmenytabor_2019",
                getString(R.string.gallery_item10_title),
                getString(R.string.gallery_item10_desc),
                R.drawable.zankai_elmenytabor));

        return items;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}