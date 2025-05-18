package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class CampLocation {
    private String id;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private Coordinates coordinates;
    private int capacity;
    private List<String> facilities;
    private List<String> imageUrls;
    private String createdBy; // Admin ID
    private Date createdAt;

    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public CampLocation() {
    }

    // Paraméteres konstruktor
    public CampLocation(String id, String name, String address, String city, String zipCode,
                        Coordinates coordinates, int capacity, List<String> facilities,
                        List<String> imageUrls, String createdBy, Date createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.coordinates = coordinates;
        this.capacity = capacity;
        this.facilities = facilities;
        this.imageUrls = imageUrls;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // Getterek és setterek

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Belső osztály a koordinátákhoz
    public static class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates() {
        }

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
