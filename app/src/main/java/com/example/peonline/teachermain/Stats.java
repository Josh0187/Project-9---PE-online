package com.example.peonline.teachermain;

public class Stats {
    float distance;
    double speed;
    double time;

    public Stats() {}

    public Stats(float distance, double speed, double time) {
        this.distance = distance;
        this.speed = speed;
        this.time = time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
