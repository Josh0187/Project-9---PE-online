package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.login.MainActivity;
import com.example.peonline.video.VideoSubmission;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class StudentMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void locationActivity(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

    public void videoActivity(View view) {
        Intent intent = new Intent(this, VideoSubmission.class);
        startActivity(intent);
    }

    public void viewAssignments(View view) {

    }

    public void viewSelfStats(View view) {

    }
}