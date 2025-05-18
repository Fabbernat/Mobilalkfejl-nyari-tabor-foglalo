package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models.Application;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private FirebaseFirestore db;

    public FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Create a new application
    public void createApplication(Application application, final OnApplicationCreateListener listener) {
        db.collection("applications")
                .add(application)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Application created with ID: " + documentReference.getId());
                        application.setId(documentReference.getId());
                        listener.onApplicationCreated(application);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error creating application", e);
                        listener.onApplicationCreateFailed(e.getMessage());
                    }
                });
    }

    // Read all applications
    public void getAllApplications(final OnApplicationsLoadedListener listener) {
        db.collection("applications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Application> applications = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Application application = document.toObject(Application.class);
                                application.setId(document.getId());
                                applications.add(application);
                            }
                            listener.onApplicationsLoaded(applications);
                        } else {
                            Log.w(TAG, "Error getting applications", task.getException());
                            listener.onApplicationsLoadFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    // Update an application
    public void updateApplication(Application application, final OnApplicationUpdateListener listener) {
        if (application.getId() == null) {
            listener.onApplicationUpdateFailed("Application ID is null");
            return;
        }

        db.collection("applications").document(application.getId())
                .set(application)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Application updated successfully");
                        listener.onApplicationUpdated(application);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating application", e);
                        listener.onApplicationUpdateFailed(e.getMessage());
                    }
                });
    }

    // Delete an application
    public void deleteApplication(String applicationId, final OnApplicationDeleteListener listener) {
        db.collection("applications").document(applicationId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Application deleted successfully");
                        listener.onApplicationDeleted(applicationId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting application", e);
                        listener.onApplicationDeleteFailed(e.getMessage());
                    }
                });
    }

    // Interfaces for callback handlers
    public interface OnApplicationCreateListener {
        void onApplicationCreated(Application application);

        void onApplicationCreateFailed(String errorMessage);
    }

    public interface OnApplicationsLoadedListener {
        void onApplicationsLoaded(List<Application> applications);

        void onApplicationsLoadFailed(String errorMessage);
    }

    public interface OnApplicationUpdateListener {
        void onApplicationUpdated(Application application);

        void onApplicationUpdateFailed(String errorMessage);
    }

    public interface OnApplicationDeleteListener {
        void onApplicationDeleted(String applicationId);

        void onApplicationDeleteFailed(String errorMessage);
    }
}
