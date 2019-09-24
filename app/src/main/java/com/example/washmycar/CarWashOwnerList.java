package com.example.washmycar;

public class CarWashOwnerList {

    private String image,id, name, address, tel, desc, station_wallet;

    public CarWashOwnerList(String image, String id, String name, String address, String tel, String desc, String station_wallet) {
        super();
        this.image = image;
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.desc = desc;
        this.station_wallet = station_wallet;
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

    public String getAddress() { return address; }

    public void  setAddress(String address) {  this.address = address; }

    public String getTel() { return tel; }

    public void  setTel(String tel) {  this.tel = tel; }

    public String getDesc() { return desc; }

    public void  setDesc(String desc) {  this.tel = desc; }

    public String getStation_wallet() { return station_wallet; }

    public void  setStation_wallet(String desc) {  this.station_wallet = station_wallet; }



}
