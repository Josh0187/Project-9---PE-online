package com.example.peonline.teachermain;

import android.os.Bundle;

import com.example.peonline.studentmain.RecycleVewAdaptorAssignments;
import com.example.peonline.video.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.example.peonline.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSubmissionsForAssignment extends AppCompatActivity {

    private String classKey;
    private int assignmentNum;
    private TextView tv_assignName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submissions_for_assignment);

        Bundle extras = getIntent().getExtras();

        final String classID = extras.getString("classID");
        classKey = classID;
        final int assignNum = extras.getInt("assignmentNum");
        assignmentNum  = assignNum;
        final String assignName = extras.getString("assignmentName");

        final Boolean stationary = extras.getBoolean("stationary");

        tv_assignName = findViewById(R.id.tv_assignName);
        tv_assignName.setText(assignName);

        recyclerView = findViewById(R.id.rv_submissions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        if (stationary) {

            // Find student names and IDs and create ArrayList of Submission objects
            final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<Submission> allsubmissions = new ArrayList<Submission>();

                    for (DataSnapshot video: snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(assignNum)).child("submissions").getChildren()) {

                        Video newVideo = video.getValue(Video.class);
                        String userID = video.getKey();
                        String name = snapshot.child("Users").child(userID).child("name").getValue().toString();
                        Submission newSubmission = new Submission(userID, name,  newVideo, stationary);
                        allsubmissions.add(newSubmission);

                    }

                    setRv(allsubmissions);
                    databaseRef.removeEventListener(this);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else {
            // for GPS submission
            // Find student names and IDs and create ArrayList of Submission objects
            final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<Submission> allsubmissions = new ArrayList<Submission>();

                    for (DataSnapshot stats: snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(assignNum)).child("submissions").getChildren()) {

                        String userID = stats.getKey();
                        String name = snapshot.child("Users").child(userID).child("name").getValue().toString();
                        float distance = stats.child("distance").getValue(Float.class);
                        double speed = stats.child("speed").getValue(Double.class);
                        double time = stats.child("time").getValue(Double.class);
                        Submission newSubmission = new Submission(userID, name,  distance, speed, time, stationary);
                        allsubmissions.add(newSubmission);

                    }

                    setRv(allsubmissions);
                    databaseRef.removeEventListener(this);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



    }


    public void setRv(ArrayList<Submission> submissionList) {
        List<Submission> listExample = new ArrayList<Submission>();
        for (Submission submission: submissionList) {
            listExample.add(submission);
        }

        RecycleVewAdaptorSubmission adaptor = new RecycleVewAdaptorSubmission(listExample);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }





}