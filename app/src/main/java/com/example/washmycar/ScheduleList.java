package com.example.washmycar;

public class ScheduleList {

    private String id,stat_id,date,time,status;

    public ScheduleList(String id, String date, String time, String status, String stat_id) {
        super();
        this.id = id;
        this.date = date;
        this.time = time;
        this.stat_id = stat_id;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStat_id() {
        return stat_id;
    }

    public void setStat_id(String stat_id) {
        this.stat_id = stat_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }












}
