package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

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
}
