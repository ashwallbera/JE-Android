package com.example.je_attendancesystem.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProjectModel {

    public String id;
    public String name;
    public String location;
    public String isFinished;

    public ProjectModel(){

    }

    public ProjectModel(String id, String name, String location, String isFinished){
        this.id = id;
        this.name = name;
        this.location = location;
        this.isFinished = isFinished;
    }
}
