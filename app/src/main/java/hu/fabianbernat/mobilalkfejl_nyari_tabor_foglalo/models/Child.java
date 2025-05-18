package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;

public class Child extends User {
    private String id;
    private String name;
    private Date birthDate;
    private String healthInfo;
    private boolean hasMedicalConditions;
    private String medicalNotes;

    public Child(String id, String name, Date birthDate, String healthInfo, boolean hasMedicalConditions, String medicalNotes) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.healthInfo = healthInfo;
        this.hasMedicalConditions = hasMedicalConditions;
    }

    public Child(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Child() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    public boolean isHasMedicalConditions() {
        return hasMedicalConditions;
    }

    public void setHasMedicalConditions(boolean hasMedicalConditions) {
        this.hasMedicalConditions = hasMedicalConditions;
    }

    public String getMedicalNotes() {
        return medicalNotes;
    }

    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }
}