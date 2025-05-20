// app/src/main/java/com/example/mobilalkfejl_nyari_tabor_foglalo/adapters/GalleryAdapter.java
package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gen._base._base_java__assetres.srcjar.R;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.GalleryItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private final List<GalleryItem> galleryItems;
    private Context mContext;


    public GalleryAdapter(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }



    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ime_secondary_split_test_activity, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryItem item = galleryItems.get(position);
        holder.imageView.setImageResource(item.getImageResourceId());
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        int lastPosition = -1;
        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.slide_in_row).getAnimation();
            lastPosition = holder.getAdapterPosition();
        }
        
        
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
            imageView = itemView.findViewById(R.id.action_image);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.tag_state_description);
        }
    }
}