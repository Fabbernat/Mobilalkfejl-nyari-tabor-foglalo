// app/src/main/java/com/example/mobilalkfejl_nyari_tabor_foglalo/adapters/GalleryAdapter.java
package com.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.R;
import com.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.GalleryItem;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private final List<GalleryItem> galleryItems;

    public GalleryAdapter(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryItem item = galleryItems.get(position);
        holder.imageView.setImageResource(item.getImageResourceId());
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gallery_image);
            titleTextView = itemView.findViewById(R.id.gallery_title);
            descriptionTextView = itemView.findViewById(R.id.gallery_description);
        }
    }
}