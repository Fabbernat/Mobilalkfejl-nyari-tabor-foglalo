package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.ArrayList;

public class NavigationListItem {
    public ArrayList<Object> items;
    public String image;
    public String title;

    public NavigationListItem(ArrayList<Object> items) {
        this.items = items;
    }

    public NavigationListItem(String image, String title) {
        this.image = image;
        this.title = title;

    }
}
