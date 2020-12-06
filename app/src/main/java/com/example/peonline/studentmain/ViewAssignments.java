package com.example.peonline.studentmain;

import android.os.Bundle;

import com.example.peonline.database.Student;
import com.example.peonline.teachermain.Assignment;
import com.example.peonline.teachermain.RecycleVewAdaptorClassView;
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

public class ViewAssignments extends AppCompatActivity {

    TextView tv_ClassName;
    private RecyclerView recyclerView;
    private String classKey;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        Bundle extras = getIntent().getExtras();

        final String classID = extras.getString("classID");
        classKey = classID;
        final String className1 = extras.getString("className");
        className  = className1;

        tv_ClassName = findViewById(R.id.tv_className);
        tv_ClassName.setText(className);


        recyclerView = findViewById(R.id.rv_assignments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        // Find student names and IDs and create ArrayList of Assignment objects
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(); // "Courses/"+ classID + "/students");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Assignment> allAssignments = new ArrayList<Assignment>();
                int numOfAssignments = snapshot.child("Courses").child(classID).child("numOfassignments").getValue(Integer.class);

                for (int i = 1; i <= numOfAssignments; i++ ) {
                    String assignmentName = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("name").getValue().toString();
                    String assignmentInstr = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("instructions").getValue().toString();
                    int assignmentNum =  snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("assignmentNum").getValue(Integer.class);
                    Boolean assignmentStationary = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("stationary").getValue(Boolean.class);

                    Assignment newAssignment = new Assignment(assignmentName,assignmentInstr,assignmentStationary,classID, assignmentNum);
                    allAssignments.add(newAssignment);
                }

                setRv(allAssignments);
                databaseRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    public void setRv(ArrayList<Assignment> classStudents) {
        List<Assignment> listExample = new ArrayList<Assignment>();
        for (Assignment assignment: classStudents) {
            listExample.add(assignment);
        }

        RecycleVewAdaptorAssignments adaptor = new RecycleVewAdaptorAssignments(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }
}