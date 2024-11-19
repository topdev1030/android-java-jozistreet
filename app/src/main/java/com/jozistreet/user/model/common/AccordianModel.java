package com.jozistreet.user.model.common;

public class AccordianModel {
    private String title, date, a_date, a_time, a_venue, a_stall_fee, a_phone;
    private int image;

    // Constructor, getters, and setters
    public AccordianModel(String title, String date, String a_date, String a_time, String a_venue, String a_stall_fee, String a_phone, int image) {
        this.title = title;
        this.date = date;
        this.image = image;
        this.a_date = a_date;
        this.a_time = a_time;
        this.a_venue = a_venue;
        this.a_stall_fee = a_stall_fee;
        this.a_phone = a_phone;
    }

    // Getter methods...
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public String getA_date() {
        return a_date;
    }

    public String getA_time() {
        return a_time;
    }

    public String getA_venue() {
        return a_venue;
    }

    public String getA_stall_fee() {
        return a_stall_fee;
    }

    public String getA_phone() {
        return a_phone;
    }
}