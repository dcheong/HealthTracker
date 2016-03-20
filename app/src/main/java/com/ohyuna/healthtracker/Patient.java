package com.ohyuna.healthtracker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Douglas on 3/18/2016.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private int height;
    private int weight;
    public Patient(){}
    public Patient(JSONObject patient) {
        try {
            firstName = patient.getString("firstName");
            lastName = patient.getString("lastName");
            age = patient.getInt("age");
            height = patient.getInt("height");
            weight = patient.getInt("weight");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getAge() {
        return age;
    }
    public int getHeight() {
        return height;
    }
    public int getWeight() {
        return weight;
    }
}
