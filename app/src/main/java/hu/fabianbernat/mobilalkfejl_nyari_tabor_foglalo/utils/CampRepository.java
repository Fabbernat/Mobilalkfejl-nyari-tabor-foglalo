package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CampRepository {
    private FirebaseFirestore db;
    private CollectionReference campsRef;

    public CampRepository() {
        db = FirebaseFirestore.getInstance();
        campsRef = db.collection("camps");
    }

    // 1. Query with multiple where conditions, ordering and limiting
    // Requires composite index: collection="camps", fields=minAge,maxAge,price ASC
    public Task<QuerySnapshot> getCampsForAgeGroupWithPriceLimit(int age, double maxPrice) {
        return campsRef.whereGreaterThanOrEqualTo("maxAge", age)
                .whereLessThanOrEqualTo("minAge", age)
                .whereLessThanOrEqualTo("price", maxPrice)
                .orderBy("price", Query.Direction.ASCENDING)
                .limit(10)
                .get();
    }

    // 2. Query with where condition, ordering, pagination (offset)
    // Requires composite index: collection="camps", fields=featured,rating DESC
    public Task<QuerySnapshot> getFeaturedCampsPaginated(int limit, int offset) {
        return campsRef.whereEqualTo("featured", true)
                .orderBy("rating", Query.Direction.DESCENDING)
                .limit(limit)
                .startAt(offset)
                .get();
    }

    // 3. Query with array contains, where condition and ordering
    // Requires index on "activities" array
    public Task<QuerySnapshot> getCampsWithActivity(String activity, Date afterDate) {
        return campsRef.whereArrayContains("activities", activity)
                .whereGreaterThanOrEqualTo("startDate", afterDate)
                .orderBy("startDate", Query.Direction.ASCENDING)
                .get();
    }

    public Task<QuerySnapshot> getAffordableSwimmingCampsForAge(int age, double maxPrice) {
        return db.collection("camps")
                .whereArrayContains("activities", "úszás")  // Must have swimming
                .whereLessThanOrEqualTo("minAge", age)      // Minimum age requirement
                .whereGreaterThanOrEqualTo("maxAge", age)   // Maximum age limit
                .whereLessThanOrEqualTo("price", maxPrice)  // Within budget
                .orderBy("price", Query.Direction.ASCENDING) // Cheapest first
                .limit(10)                                  // Only show top 10
                .get();
    }

    public Task<QuerySnapshot> getPremiumCampsPaginated(String lastCampId, int limit) {
        Query baseQuery = db.collection("camps")
                .whereEqualTo("premium", true)            // Only premium camps
                .whereGreaterThanOrEqualTo("rating", 4.5)  // Highly rated
                .orderBy("rating", Query.Direction.DESCENDING)
                .orderBy("name")                          // Secondary sort
                .limit(limit);

        if (lastCampId != null) {
            // For pagination - get document snapshot first
            return db.collection("camps").document(lastCampId).get()
                    .continueWithTask(task -> {
                        DocumentSnapshot lastVisible = task.getResult();
                        return baseQuery.startAfter(lastVisible).get();
                    });
        }
        return baseQuery.get();
    }

    public Task<QuerySnapshot> getAvailableSummerCamps(List<String> activities, int groupSize) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2025, Calendar.JULY, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2025, Calendar.AUGUST, 31);

        return db.collection("camps")
                .whereGreaterThanOrEqualTo("startDate", startDate.getTime())
                .whereLessThanOrEqualTo("endDate", endDate.getTime())
                .whereArrayContainsAny("activities", activities) // Any of specified activities
                .whereGreaterThanOrEqualTo("availableSpots", groupSize) // Enough capacity
                .orderBy("availableSpots", Query.Direction.ASCENDING) // Show filling-up camps first
                .get();
    }
}
