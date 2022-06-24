package com.example.je_attendancesystem.models;

public class TimesheetModel {
    public String userid;
    public String projectid;
    public String timeIn;
    public String timeOut;
    public String datecreated;

    public TimesheetModel(){

    }

    public String getProjectid() {
        return projectid;
    }

    public String getUserid() {
        return userid;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getDatecreated() {
        return datecreated;
    }
}
