package com.example.je_attendancesystem.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ProjectModel {

    public String uid;
    private String id;
    private String name;
    private String location;
    private String isFinished;

    public ProjectModel(){

    }

    public ProjectModel(String uid,String id, String name, String location, String isFinished){
        this.uid = uid;
        this.id = id;
        this.name = name;
        this.location = location;
        this.isFinished = isFinished;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", );
        result.put("id", id);
        result.put("isFinished", isFinished);
        result.put("location", location);
        result.put("name", name);
        return result;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public String getLocation() {
        return location;
    }

    public String getUid() {
        return uid;
    }
}
