package com.ohyuna.healthtracker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Douglas on 3/18/2016.
 */
public class Patient {
    public int id;
    public String first;
    public String second;
    public String last;
    public String birth;
    public double height;
    public double weight;
    public double head;
    public boolean gender;
    public Patient(int id, String first, String second, String last, String birth) {
        this(id, first,second, last, birth, 0, 0, 0, false);
    }
    public Patient(int id, String first, String second, String last, String birth, double height, double weight, double head, boolean gender){
        this.id = id;
        this.first = first;
        this.second = second;
        this.last = last;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.head = head;
        this.gender = gender;
    }
    public boolean equals(Patient patient) {
        return(patient.id == id);
    }
}
