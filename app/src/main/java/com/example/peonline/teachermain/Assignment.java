package com.example.peonline.teachermain;

public class Assignment {

    private String Ass_title;
    private String Ass_body;
    private boolean Stationary;

    public Assignment(String ass_title, String ass_body, boolean checkbox) {
        Ass_title = ass_title;
        Ass_body = ass_body;
        Stationary = checkbox;
    }

    public String getAss_title() {
        return Ass_title;
    }

    public String getAss_body() {
        return Ass_body;
    }

    public boolean isStationary() { return Stationary; }
}
