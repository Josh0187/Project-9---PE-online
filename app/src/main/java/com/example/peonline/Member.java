package com.example.peonline;

public class Member {
    private String videoName;
    private String videoURI;

    private Member() {}

    public Member(String name, String videoUri) {
        if(name.trim().equals("")) {
            name = "not available";
        }
        videoName = name;
        videoURI = videoUri;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoURI() {
        return videoURI;
    }

    public void setVideoURI(String videoURI) {
        this.videoURI = videoURI;
    }
}
