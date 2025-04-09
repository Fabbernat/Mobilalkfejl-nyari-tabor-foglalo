package com.example.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.Random;

public class CampModel {
    private static int idCounter = 0;
    private final int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private String imageUrl;
    private String location;
    private String contactInfo;
    private int price;

    /** Empty constructor required for Firestore deserialization */
    public CampModel() {
        this.id = generateId();;
    }

    /** Constructor with auto-incrementing ID */
    public CampModel(String name, String contactInfo) {
        this.id = generateId();
        this.name = name;
        this.contactInfo = contactInfo;
    }

    /** Constructor with manual ID (used when retrieving from a database) */
    public CampModel(int id, String name, Date startDate, Date endDate, String description, String imageUrl, String location, String contactInfo, int price) {
        this.id = generateId();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.contactInfo = contactInfo;
        this.price = price;
    }

    public CampModel(String s, String s1, String s2, float aFloat, int resourceId, String s3, String s4) {
        this.id = getId();
    }


    /** Synchronized method to safely increment ID */
    private static synchronized int generateId() {
        return ++idCounter;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
