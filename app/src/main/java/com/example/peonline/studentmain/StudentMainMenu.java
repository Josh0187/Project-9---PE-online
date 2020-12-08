package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.login.MainActivity;
import com.example.peonline.teachermain.Assignment;
import com.example.peonline.teachermain.Class;
import com.example.peonline.teachermain.RecycleVewAdaptor;
import com.example.peonline.teachermain.ViewStudentStats;
import com.example.peonline.video.VideoSubmission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StudentMainMenu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String studentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

        recyclerView = findViewById(R.id.rv_studentClasses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Find classID and class Name in database
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseref = database.getReference("Users/" + ID);

        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentName = snapshot.child("name").getValue().toString();
                ArrayList<String> classIDs = new ArrayList<String>();
                for (DataSnapshot dataSnapshot : snapshot.child("classID").getChildren()) {
                    classIDs.add(dataSnapshot.getValue().toString());
                }
                final ArrayList<String> allClassIDs = classIDs;
                if (classIDs.isEmpty()) {
                    databaseref.removeEventListener(this);
                }
                else {
                    final DatabaseReference databaseRefCourse = database.getReference("Courses/");

                    databaseRefCourse.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<Class> allClasses = new ArrayList<Class>();

                            for (String courseKey: allClassIDs) {
                                String className = snapshot.child(courseKey).child("courseName").getValue().toString();
                                Class newClass = new Class(className, courseKey);
                                allClasses.add(newClass);
                            }



                            setRv(allClasses);
                            databaseRefCourse.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                databaseref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setRv(ArrayList<Class> classes) {
        List<Class> listExample = new ArrayList<Class>();
        for (Class classe: classes) {
            listExample.add(classe);
        }

        RecycleVewAdaptorStudent adaptor = new RecycleVewAdaptorStudent(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
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

    public void ViewMyStats(View view) {
        Intent intentToStats = new Intent(this, ViewStudentStats.class);
        intentToStats.putExtra("studentID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        intentToStats.putExtra("studentName", studentName);
        startActivity(intentToStats);
    }
}