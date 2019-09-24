package com.example.washmycar;

public class CompanyList {

    private String image,id, name, station_wallet;
    private float rate;


    public CompanyList(String image, String id, String name, float rate, String station_wallet) {
        super();
        this.image = image;
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.station_wallet = station_wallet;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStation_wallet() { return station_wallet; }

    public void  setStation_wallet(String desc) {  this.station_wallet = station_wallet; }
}
