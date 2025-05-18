package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.ParentDashboardActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.Camp;

import java.util.List;

public class UpcomingCampAdapter extends RecyclerView.Adapter<CampAdapter.ViewHolder> {
    public UpcomingCampAdapter(ParentDashboardActivity parentDashboardActivity, List<Camp> upcomingCamps) {
    }

    @NonNull
    @Override
    public CampAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CampAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
