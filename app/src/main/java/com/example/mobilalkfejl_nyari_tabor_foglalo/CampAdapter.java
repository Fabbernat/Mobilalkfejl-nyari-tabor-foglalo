package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.Context;
import android.graphics.pdf.LoadParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilalkfejl_nyari_tabor_foglalo.models.CampModel;

import java.util.ArrayList;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.ViewHolder> implements Filterable {
    private ArrayList<CampModel> mCampsData;
    private ArrayList<CampModel> mCampsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    public CampAdapter(Context mContext, ArrayList<CampModel> mCampsData) {
        this.mContext = mContext;
        this.mCampsData = mCampsData;
        this.mCampsDataAll = mCampsData;
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {link #onBindViewHolder(CampViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_list_camps, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getBindingAdapterPosition()} which
     * will have the updated adapter position.
     * <p>
     * Override {link #onBindViewHolder(CampViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CampModel currentCamp = mCampsData.get(position);

        holder.bindTo(currentCamp);

        if (holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.slite_in_row).getAnimation();
            lastPosition = holder.getAdapterPosition();
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mCampsData.size();
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     *
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return campFilter;
    }

    private Filter campFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CampModel> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0){
                results.count = mCampsDataAll.size();
                results.values = mCampsDataAll;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CampModel item : mCampsDataAll) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults){
            mCampsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();

        }
    };

    public void filterList(ArrayList<CampModel> filteredList) {
        mCampsData = filteredList;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;

        public ViewHolder(@NonNull View campView) {
            super(campView);

            mTitleText = campView.findViewById(R.id.camp_title);
            mInfoText = campView.findViewById(R.id.camp_info);
            mPriceText = campView.findViewById(R.id.camp_price);
            mItemImage = campView.findViewById(R.id.camp_image);
            mRatingBar = campView.findViewById(R.id.camp_rating);

            campView.findViewById(R.id.add_to_starred).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Activity", "We noted for us that you are interested in this camp.");
                }
            });
        }

        public void bindTo(CampModel currentCamp) {
            mTitleText.setText(currentCamp.getName());
            mInfoText.setText(currentCamp.getDescription());
            mPriceText.setText(currentCamp.getPrice());
            int parsedCurrentCampInteger;
            try {
                parsedCurrentCampInteger = Integer.parseInt(currentCamp.getImageUrl());
            } catch (Exception e) {
                parsedCurrentCampInteger = 0;
            }
            mItemImage.setImageResource(parsedCurrentCampInteger);

            Glide.with(mContext).load(currentCamp.getImageUrl()).into(mItemImage);
        }
    }
}
