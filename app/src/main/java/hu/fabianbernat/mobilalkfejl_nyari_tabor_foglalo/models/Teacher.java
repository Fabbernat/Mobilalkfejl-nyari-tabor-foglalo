package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import android.util.Log;

public class Teacher extends User {
    private static final int SECRET = 42;
    private String schoolName;
    private String position;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void registerSchoolGroup(Camp camp, int childrenCount, int supervisorCount) {
        if (camp == null) {
            throw new IllegalArgumentException("Camp cannot be null.");
        }
        if (childrenCount <= 0) {
            throw new IllegalArgumentException("Children count must be greater than zero.");
        }
        if (supervisorCount <= 0) {
            throw new IllegalArgumentException("Supervisor count must be greater than zero.");
        }

        int totalParticipants = childrenCount + supervisorCount;

        camp.addParticipants(totalParticipants);

        Log.i(String.valueOf(SECRET), "School group registered: " + schoolName + " (" + position +
                "), Children: " + childrenCount + ", Supervisors: " + supervisorCount);
    }
}
