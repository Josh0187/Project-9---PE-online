package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.database.Course;
import com.example.peonline.database.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.example.peonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Enroll extends AppCompatActivity {

    EditText courseKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        courseKey = findViewById(R.id.et_enrollKey);

    }

    // Purpose: To enroll a student in a course (adding their name into the course list of students)
    public void enroll(View view) {
        final String CourseKey = courseKey.getText().toString();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // check if key exists


        final DatabaseReference databaseRefcheck = FirebaseDatabase.getInstance().getReference("Courses/");
        databaseRefcheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(CourseKey)) {
                    courseKey.setError("Course does not exists");
                    databaseRefcheck.removeEventListener(this);
                }
                else {
                    final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users/"+userID);
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int numOfClasses = snapshot.child("numOfClasses").getValue(Integer.class);
                            ArrayList<String> classIDs = new ArrayList<String>();
                            for (DataSnapshot dataSnapshot : snapshot.child("classID").getChildren()) {
                                classIDs.add(dataSnapshot.getValue().toString());
                            }
                            classIDs.add(CourseKey);

                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numOfClasses").setValue(numOfClasses+1);

                            for (int i = 0; i < numOfClasses+1; i++) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("classID").child(Integer.toString(i)).setValue(classIDs.get(i));
                            }

                            final DatabaseReference databaseRefcourse = FirebaseDatabase.getInstance().getReference("Courses/"+CourseKey);
                            databaseRefcourse.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // Adding the student to the list of students in the course
                                    DataSnapshot dataSnapshotStudents = snapshot.child("numOfStudents");
                                    int numOfStudents = dataSnapshotStudents.getValue(Integer.class);
                                    FirebaseDatabase.getInstance().getReference().child("Courses").child(CourseKey).child("students").child(Integer.toString(numOfStudents+1)).setValue(userID);
                                    FirebaseDatabase.getInstance().getReference().child("Courses").child(CourseKey).child("numOfStudents").setValue(numOfStudents+1);


                                    databaseRefcourse.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            databaseRef.removeEventListener(this);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Intent StudentMainIntent = new Intent(Enroll.this, StudentMainMenu.class);
                    startActivity(StudentMainIntent);
                }
                databaseRefcheck.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}