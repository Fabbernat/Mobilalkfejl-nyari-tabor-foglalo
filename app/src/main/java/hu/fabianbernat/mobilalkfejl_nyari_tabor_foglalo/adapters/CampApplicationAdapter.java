package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.CampApplicationActivity;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.Application;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import gen._base._base_java__assetres.srcjar.R;

public class CampApplicationAdapter extends RecyclerView.Adapter<CampApplicationAdapter.ApplicationViewHolder> {

    private final Context context;
    private List<Application> applications;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

    public CampApplicationAdapter(Context context, List<Application> applications) {
        this.context = context;
        this.applications = applications;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_camp_application, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = applications.get(position);

        holder.textViewCampName.setText(application.getCampName());
        holder.textViewChildName.setText(application.getChildName());

        // Set status with appropriate color
        holder.textViewStatus.setText(getStatusText(application.getStatus()));
        holder.textViewStatus.setBackgroundColor(getStatusColor(application.getStatus()));

        // Format dates if they exist
        if (application.getStartDate() != null && application.getEndDate() != null) {
            String dateRange = dateFormat.format(application.getStartDate()) + " - " +
                    dateFormat.format(application.getEndDate());
            holder.textViewDates.setText(dateRange);
        } else {
            holder.textViewDates.setText("Dátumok nincsenek megadva");
        }

        // Set medical info and payment indicators
        holder.textViewMedicalInfo.setText("Eü. adatok " +
                (application.isMedicalInfoProvided() ? "✓" : "✗"));
        holder.textViewPayment.setText("Fizetés " +
                (application.isPaymentCompleted() ? "✓" : "✗"));

        // Set notes if they exist
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (application.getNote() != null && !application.getNote().isEmpty()) {
                holder.textViewNotes.setText(application.getNote());
                holder.textViewNotes.setVisibility(View.VISIBLE);
            } else {
                holder.textViewNotes.setVisibility(View.GONE);
            }
        }

        // Set up edit button
        holder.buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, CampApplicationActivity.class);
            intent.putExtra("APPLICATION_ID", application.getApplicationId());
            context.startActivity(intent);
        });
    }

    private String getStatusText(String status) {
        switch (status) {
            case "PENDING":
                return "FOLYAMATBAN";
            case "APPROVED":
                return "ELFOGADVA";
            case "REJECTED":
                return "ELUTASÍTVA";
            default:
                return status;
        }
    }

    private int getStatusColor(String status) {
        switch (status) {
            case "PENDING":
                return context.getResources().getColor(android.R.color.holo_blue_dark);
            case "APPROVED":
                return context.getResources().getColor(android.R.color.holo_green_dark);
            case "REJECTED":
                return context.getResources().getColor(android.R.color.holo_red_dark);
            default:
                return context.getResources().getColor(android.R.color.darker_gray);
        }
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public void updateApplications(List<Application> newApplications) {
        this.applications = newApplications;
        notifyDataSetChanged();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCampName, textViewChildName, textViewStatus, textViewDates;
        TextView textViewMedicalInfo, textViewPayment, textViewNotes;
        Button buttonEdit;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCampName = itemView.findViewById(R.id.textViewCampName);
            textViewChildName = itemView.findViewById(R.id.textViewChildName);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewDates = itemView.findViewById(R.id.textViewDates);
            textViewMedicalInfo = itemView.findViewById(R.id.textViewMedicalInfo);
            textViewPayment = itemView.findViewById(R.id.textViewPayment);
            textViewNotes = itemView.findViewById(R.id.textViewNotes);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}