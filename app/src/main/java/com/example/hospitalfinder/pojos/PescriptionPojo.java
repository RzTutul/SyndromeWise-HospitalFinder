package com.example.hospitalfinder.pojos;

public class PescriptionPojo {
    String p_id;
    String hospitalName;
    String doctorName;
    String doctorPhone;
    String apoinmentDate;
    String pesriptionImage;

    public PescriptionPojo() {
    }

    public PescriptionPojo(String p_id, String hospitalName, String doctorName, String doctorPhone, String apoinmentDate, String pesriptionImage) {
        this.p_id = p_id;
        this.hospitalName = hospitalName;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.apoinmentDate = apoinmentDate;
        this.pesriptionImage = pesriptionImage;
    }



    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getApoinmentDate() {
        return apoinmentDate;
    }

    public void setApoinmentDate(String apoinmentDate) {
        this.apoinmentDate = apoinmentDate;
    }

    public String getPesriptionImage() {
        return pesriptionImage;
    }

    public void setPesriptionImage(String pesriptionImage) {
        this.pesriptionImage = pesriptionImage;
    }
}
