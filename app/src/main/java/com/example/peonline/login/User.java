package com.example.peonline.login;

import java.util.ArrayList;

public class User {
    public static String name;
    public static Boolean isTeacher;
    public static int numOfClasses;
    public static ArrayList<String> classID;


    public User() {}

    public User(String name, Boolean isTeacher, int numOfClasses) {
        this.name = name;
        this.isTeacher = isTeacher;
        this.classID = new ArrayList<String>();
        this.numOfClasses = numOfClasses;
    }

    public String getName() {
        return name;
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public ArrayList<String> getClassID() {
        return this.classID;
    }

    public void setClassID(ArrayList<String> classID) {
        this.classID = classID;
    }
}
