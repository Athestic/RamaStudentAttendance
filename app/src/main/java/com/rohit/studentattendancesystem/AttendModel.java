package com.rohit.studentattendancesystem;

public class AttendModel {
    private String rollno;
    private String fname;
    private String lname;
    private boolean present;

    public AttendModel(String rollno, String fname, String lname) {
        this.rollno = rollno;
        this.fname = fname;
        this.lname = lname;
    }

    public String getRollno() {
        return rollno;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
