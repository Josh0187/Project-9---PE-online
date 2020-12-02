package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.login.MainActivity;
import com.example.peonline.teachermain.Assignment;
import com.example.peonline.video.VideoSubmission;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StudentMainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent toMainIntent = new Intent(StudentMainMenu.this, MainActivity.class);
        startActivity(toMainIntent);
    }

    public void locationActivity(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

    public void videoActivity(View view) {
        Intent intent = new Intent(this, VideoSubmission.class);
        startActivity(intent);
    }

    public void goToEnroll(View view) {
        Intent i = new Intent(this, Enroll.class);
        startActivity(i);

    }

    public void viewAssignments(View view) {

    }

    public void viewSelfStats(View view) {

    }
}