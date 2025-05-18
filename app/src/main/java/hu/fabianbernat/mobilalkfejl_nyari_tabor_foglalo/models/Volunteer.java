package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.List;

public class Volunteer {
    private String role; // Parancsnok, altáborvezető, őrsvezető, gyerekvigyázó, tábori animátor, program-lebonyolító, konyhai kisegítő, logisztikai segítő, stb.
    private List<String> skills;
    private String availability;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
