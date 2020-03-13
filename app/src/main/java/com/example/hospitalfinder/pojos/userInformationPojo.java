package com.example.hospitalfinder.pojos;

public class userInformationPojo {
    private String uesrID;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userGender;
    private String userBloodgrp;
    private String userDateofBith;
    private String userPassword;

    public userInformationPojo() {
    }

    public userInformationPojo(String uesrID, String userName, String userEmail, String userPhone, String userGender, String userBloodgrp, String userDateofBith, String userPassword) {
        this.uesrID = uesrID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userGender = userGender;
        this.userBloodgrp = userBloodgrp;
        this.userDateofBith = userDateofBith;
        this.userPassword = userPassword;
    }

    public String getUesrID() {
        return uesrID;
    }

    public void setUesrID(String uesrID) {
        this.uesrID = uesrID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserBloodgrp() {
        return userBloodgrp;
    }

    public void setUserBloodgrp(String userBloodgrp) {
        this.userBloodgrp = userBloodgrp;
    }

    public String getUserDateofBith() {
        return userDateofBith;
    }

    public void setUserDateofBith(String userDateofBith) {
        this.userDateofBith = userDateofBith;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
