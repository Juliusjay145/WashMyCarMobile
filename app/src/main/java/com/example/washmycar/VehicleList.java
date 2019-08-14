package com.example.washmycar;

public class VehicleList {
    private String image,id,model,plateno,brand,color;

    public VehicleList(String image, String id, String model, String plateno, String brand, String color) {
        super();
        this.image = image;
        this.id = id;
        this.model = model;
        this.plateno = plateno;
        this.brand = brand;
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
