package com.example.peonline;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Student implements Serializable {
    String name;
    double [][] statistics;
    int NumOfExercises;

    public Student(String name) {
        this.name = name;
        // Array of 50 non-stationary exercises each containing 3 stats (Time, Distance, Speed) in that order
        this.statistics = new double[50][3];

        // Update Current Number of Exercises
        this.NumOfExercises = 0;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[][] getStatistics() {
        return statistics;
    }

    public void setStatistics(double[][] statistics) {
        this.statistics = statistics;
    }

    public int getNumOfExercises() {
        return NumOfExercises;
    }

    public void setNumOfExercises(int numOfExercises) {
        NumOfExercises = numOfExercises;
    }


    // Purpose : add a set of stats for a non stationary exercise, use when student has submitted non-stationary assignment
    // Parameter : double[] TimeDistanceSpeed = {double time, double distance, double speed}
    public void Add_Non_Stationary_Exercise(double[] TimeDistanceSpeed) {

        // if statistics is full
        if (NumOfExercises+1 > 50) {
            // Remove statistics[0] and shift everything down
            for (int i = 0; i < 49; i++) {
                statistics[i] = statistics[i+1];
            }
        }

        statistics[NumOfExercises] = TimeDistanceSpeed;

        //Increment NumOfExercises
        NumOfExercises++;
    }

}
