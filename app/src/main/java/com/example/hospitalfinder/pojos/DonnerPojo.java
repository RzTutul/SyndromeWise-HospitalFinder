package com.example.hospitalfinder.pojos;

public class DonnerPojo {
    String donnerID;
    String donnerName;
    String donnerGender;
    String donnerPhone;
    String donnerDistric;

    public DonnerPojo() {
    }

    public DonnerPojo(String donnerID, String donnerName, String donnerGender, String donnerPhone, String donnerDistric) {
        this.donnerID = donnerID;
        this.donnerName = donnerName;
        this.donnerGender = donnerGender;
        this.donnerPhone = donnerPhone;
        this.donnerDistric = donnerDistric;
    }

    public String getDonnerID() {
        return donnerID;
    }

    public void setDonnerID(String donnerID) {
        this.donnerID = donnerID;
    }

    public String getDonnerName() {
        return donnerName;
    }

    public void setDonnerName(String donnerName) {
        this.donnerName = donnerName;
    }

    public String getDonnerGender() {
        return donnerGender;
    }

    public void setDonnerGender(String donnerGender) {
        this.donnerGender = donnerGender;
    }

    public String getDonnerPhone() {
        return donnerPhone;
    }

    public void setDonnerPhone(String donnerPhone) {
        this.donnerPhone = donnerPhone;
    }

    public String getDonnerDistric() {
        return donnerDistric;
    }

    public void setDonnerDistric(String donnerDistric) {
        this.donnerDistric = donnerDistric;
    }
}
