package com.example.je_attendancesystem.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeModel {
    private String date;
    private String day;
    private String projectid;
    private LocalDate localDate;
    public DateTimeModel(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DateTimeModel(String date, String projectid){
//        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
//        String formatted = format.format(utc.getTime());

        //USE ANOTHER FORMATTER TO PARSE TO LOCALDATE THE UTC DATE
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy");
        LocalDate localDate = LocalDate.parse(""+date,dtf);
        //this.date = ""+localDate.getMonth()+" "+localDate.getDayOfMonth()+", "+localDate.getYear();
        this.date = date;
        this.day = localDate.getDayOfWeek().toString();
        this.projectid = projectid;
        this.localDate = LocalDate.parse(date,dtf);
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getProjectid() {
        return projectid;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
