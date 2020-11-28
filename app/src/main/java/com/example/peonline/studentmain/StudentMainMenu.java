package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.login.MainActivity;
import com.example.peonline.login.User;
import com.example.peonline.teachermain.TeacherMainMenu;
import com.example.peonline.video.VideoSubmission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentMainMenu extends AppCompatActivity {

    TextView classEnrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

        classEnrolled = findViewById(R.id.tv_class);

        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseref = database.getReference("Users/" + ID);

        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> classIDs = new ArrayList<String>();
                for (DataSnapshot dataSnapshot : snapshot.child("classID").getChildren()) {
                    classIDs.add(dataSnapshot.getValue().toString());
                }

                if (classIDs.isEmpty()) {
                    databaseref.removeEventListener(this);
                }
                else {
                    String CourseKey = classIDs.get(classIDs.size()-1);
                    displayClassName(CourseKey);
                    databaseref.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void displayClassName(String CourseKey) {
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseRefcourse = FirebaseDatabase.getInstance().getReference("Courses/" + CourseKey);
                databaseRefcourse.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // finding name of class and displaying it
                        DataSnapshot dataSnapshotCourseName = snapshot.child("courseName");
                        String courseName = dataSnapshotCourseName.getValue().toString();
                        classEnrolled.setText(courseName);
                        databaseRefcourse.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }




    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intToMain = new Intent(StudentMainMenu.this, MainActivity.class);
        startActivity(intToMain);
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
        Intent intent = new Intent(this, Enroll.class);
        startActivity(intent);
    }
}