package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramSchedule {
    private String id; // program nap azonosító
    private String date; // pl.: "2025-07-15"
    private List<String> activities; // pl.: ["Reggeli", "Kézműves foglalkozás", "Ebéd", "Túra", "Tábortűz"]

    public ProgramSchedule() {
    }

    public ProgramSchedule(String id) {
        this.id = id;
        this.date = "2025-07-15";
        this.activities = new ArrayList<>(Arrays.asList(
                "Reggeli", "Kézműves foglalkozás", "Ebéd", "Túra", "Tábortűz"
        ));
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
}
