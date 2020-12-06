package com.example.peonline.database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Student implements Serializable {
    String name;
    String classID;
    ArrayList<ArrayList<Double>> statistics;
    int NumOfExercises;

    public Student(String name, String classID) {
        this.name = name;
        this.classID = classID;
        // Array of 50 non-stationary exercises each containing 3 stats (Time, Distance, Speed) in that order
        this.statistics = new ArrayList<ArrayList<Double>>();

        // Update Current Number of Exercises
        this.NumOfExercises = 0;
    }

    public void updateDatabase() {

        FirebaseDatabase.getInstance().getReference().child("Students").child(this.name).child("classID").setValue(this.classID);
        FirebaseDatabase.getInstance().getReference().child("Students").child(this.name).child("NumOfExercises").setValue(this.NumOfExercises);

        int n = statistics.size();

        for (int i = 0; i < n; i++) {
            FirebaseDatabase.getInstance().getReference().child("Students").child(this.name).child("statistics").child("non-stationary" + Integer.toString(i)).child("time").setValue(statistics.get(i).get(0));
            FirebaseDatabase.getInstance().getReference().child("Students").child(this.name).child("statistics").child("non-stationary" + Integer.toString(i)).child("distance").setValue(statistics.get(i).get(1));
            FirebaseDatabase.getInstance().getReference().child("Students").child(this.name).child("statistics").child("non-stationary" + Integer.toString(i)).child("speed").setValue(statistics.get(i).get(2));
        }

    }

    public Student() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*public ArrayList<ArrayList<Double>> getStatistics() {
        return statistics;
    }

     */

    /*public void setStatistics(ArrayList<ArrayList<Double>> statistics) {
        this.statistics = statistics;
    }

     */

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public int getNumOfExercises() {
        return NumOfExercises;
    }

    public void setNumOfExercises(int numOfExercises) {
        NumOfExercises = numOfExercises;
    }


    // Purpose : add a set of stats for a non stationary exercise, use when student has submitted non-stationary assignment
    // Parameter : double[] TimeDistanceSpeed = {double time, double distance, double speed}
    /*public void Add_Non_Stationary_Exercise(ArrayList<Double> TimeDistanceSpeed) {
        // If list is full
        if(NumOfExercises >= 50) {
            statistics.clear();
            NumOfExercises = 0;
        }
        statistics.add(TimeDistanceSpeed);
        this.NumOfExercises++;
    }
    */


}


