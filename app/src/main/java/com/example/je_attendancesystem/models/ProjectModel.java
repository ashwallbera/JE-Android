package com.example.je_attendancesystem.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ProjectModel {

    public String uid;
    public String id;
    public String name;
    public String location;
    public String isFinished;

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
}
