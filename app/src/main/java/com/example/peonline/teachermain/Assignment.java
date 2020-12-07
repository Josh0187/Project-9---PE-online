package com.example.peonline.teachermain;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Assignment {

    private String courseKey;
    private String Ass_title;
    private String Ass_body;
    private boolean Stationary;
    private boolean submited;
    private int assignmentNum;
    private ArrayList<String> submissions;

    public Assignment(String ass_title, String ass_body, boolean checkbox, String CourseKey, int assignmentNum, boolean submit_checkBox) {
        courseKey = CourseKey;
        Ass_title = ass_title;
        Ass_body = ass_body;
        Stationary = checkbox;
        submited = submit_checkBox;
        this.assignmentNum = assignmentNum;
        submissions = new ArrayList<String>();
    }


    public String getAss_title() {
        return Ass_title;
    }

    public String getAss_body() {
        return Ass_body;
    }

    public Boolean getStationary() {
        return this.Stationary;
    }

    public String getCourseKey() {
        return this.courseKey;
    }

    public int getAssignmentNum() {
        return this.assignmentNum;
    }

    public boolean isStationary() { return Stationary; }

    public boolean isSubmited() { return submited; }

    public void setSubmited() { this.submited = true; }
}
