package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class CampProgram {
    private String id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String location;
    private List<String> equipmentNeeded;

    public CampProgram() {
    }

    public CampProgram(String title, Date startTime, Date endTime, String location) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEquipmentNeeded(List<String> equipmentNeeded) {
        this.equipmentNeeded = equipmentNeeded;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getEquipmentNeeded() {
        return equipmentNeeded;
    }
}