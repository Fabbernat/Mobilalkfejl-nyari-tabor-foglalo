package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class Parent extends User {

    private List<Child> children;

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    // Additional parent-specific methods
    public void registerChildForCamp(Child child, Camp camp) {
        // Implementation
    }

    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public Parent() {
    }
}
