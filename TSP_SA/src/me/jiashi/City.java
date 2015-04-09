package me.jiashi;

/**
 * Created by anderson on 2/15/15.
 */
public class City {

    public City(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude*Math.PI/180.0;
        this.longitude = longitude*Math.PI/180.0;
    }

    public String getName() {
        return name;
    }

    private String name;

    public double getLatitude() {
        return latitude;
    }

    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    private double longitude;

}
