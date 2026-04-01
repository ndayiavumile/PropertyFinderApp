package com.example.propertyfinder;

public class Property {
    private String title;
    private String price;
    private String description;
    private double latitude;
    private double longitude;
    private String type;

    public Property(String title, String price, String description, double latitude, double longitude, String type) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }


    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getType() { return type; }
}
