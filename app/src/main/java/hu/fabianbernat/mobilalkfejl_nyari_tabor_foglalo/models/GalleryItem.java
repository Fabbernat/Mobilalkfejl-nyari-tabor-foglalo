// app/src/main/java/com/example/mobilalkfejl_nyari_tabor_foglalo/models/GalleryItem.java
package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

public class GalleryItem {
    private String imageName;
    private String title;
    private String description;
    private int imageResourceId; // For local images

    public GalleryItem(String imageName, String title, String description, int imageResourceId) {
        this.imageName = imageName;
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Getters
    public String getImageName() {
        return imageName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}