package com.example.peonline.database;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {
    String courseName;
    String courseKey;
    ArrayList<String> students;
    int numOfStudents;
    ArrayList<Assignment> assignments;

    // *** Consider getting rid of numOfStudents and use students.size() instead

    // Constructor
    public Course(String courseName) {
        this.courseName = courseName;
        this.courseKey = UUID.randomUUID().toString();
        this.numOfStudents = 0;
        this.students = new ArrayList<String>();
        this.assignments = new ArrayList<>();
    }

    // Purpose: adds a student name into the course student list
    public void addStudent(String studentName) {
        students.add(studentName);
        numOfStudents++;
    }

    // Purpose: Updates the firebase database
    public void updateDatabase() {
        FirebaseDatabase.getInstance().getReference().child("Courses").child(this.courseKey).child("courseName").setValue(this.courseName);
        FirebaseDatabase.getInstance().getReference().child("Courses").child(this.courseKey).child("courseKey").setValue(this.courseKey);
        FirebaseDatabase.getInstance().getReference().child("Courses").child(this.courseKey).child("numOfStudents").setValue(this.numOfStudents);

        for (int i = 0; i < students.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Courses").child(this.courseKey).child("students").child(Integer.toString(i)).setValue(students.get(i));
        }
    }

    // Getters and Setters

    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
}
