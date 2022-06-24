package com.example.je_attendancesystem.models;

public class TimesheetModel {
    public String id;
    public String userid;
    public String projectid;
    public String timeIn;
    public String timeOut;
    public String datecreated;

    public TimesheetModel(){

    }
    public TimesheetModel(String id, String projectid, String userid, String timeIn, String timeOut, String datecreated){
        this.userid = userid;
        this.id = id;
        this.projectid = projectid;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.datecreated = datecreated;
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

    public String getId() {
        return id;
    }
}
