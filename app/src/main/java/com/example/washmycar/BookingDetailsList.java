package com.example.washmycar;

public class BookingDetailsList {

    String image,name,date,time,service,employee,station,price,status,id,seek_id;

    public BookingDetailsList(String image,String name,
                              String date, String time, String service, String employee, String station, String price,String status,String id,String seek_id){
        super();
        this.image = image;
        this.name = name;
        this.date = date;
        this.time = time;
        this.service = service;
        this.employee = employee;
        this.station= station;
        this.price = price;
        this.status = status;
        this.id = id;
        this.seek_id = seek_id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    //price

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeek_id() {
        return seek_id;
    }

    public void setSeek_id(String seek_id) {
        this.seek_id = seek_id;
    }
}
