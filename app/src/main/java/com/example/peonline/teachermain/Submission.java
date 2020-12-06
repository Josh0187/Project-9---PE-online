package com.example.peonline.teachermain;

import com.example.peonline.video.Video;

public class Submission {
    String submitterID;
    String submitterName;
    Video videoSubmission;
    Boolean stationary;
    float distance;
    double time;
    double speed;


    // For a video submission use this constructor
    public Submission(String submitterID,String submitterName, Video videoSubmission, Boolean stationary) {
        this.submitterID = submitterID;
        this.submitterName = submitterName;
        this.videoSubmission = videoSubmission;
        this.stationary = stationary;
    }

    // For GPS submission use this constructor
    public Submission(String submitterID,String submitterName, float distance, double speed, double time, Boolean stationary) {
        this.submitterID = submitterID;
        this.submitterName = submitterName;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.stationary = stationary;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getSubmitterID() {
        return submitterID;
    }

    public void setSubmitterID(String submitterID) {
        this.submitterID = submitterID;
    }

    public Video getVideoSubmission() {
        return videoSubmission;
    }

    public void setVideoSubmission(Video videoSubmission) {
        this.videoSubmission = videoSubmission;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Boolean getStationary() {
        return stationary;
    }

    public void setStationary(Boolean stationary) {
        this.stationary = stationary;
    }
}
