package com.example.peonline.login;

public class User {
    public static String name;
    public static Boolean isTeacher;

    public User() {}

    public User(String name, Boolean isTeacher) {
        this.name = name;
        this.isTeacher = isTeacher;
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
}
