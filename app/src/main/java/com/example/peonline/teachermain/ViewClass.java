package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.database.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peonline.R;
import com.example.peonline.studentmain.RecycleVewAdaptorAssignments;
import com.example.peonline.studentmain.RecycleVewAdaptorStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewClass extends AppCompatActivity {

    TextView tv_ClassName;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewAssignmentList;
    private String classKey;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

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



        // Find student names and IDs and create ArrayList of student objects
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(); // "Courses/"+ classID + "/students");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Student> allstudents = new ArrayList<Student>();
               int numOfStudents = snapshot.child("Courses").child(classID).child("numOfStudents").getValue(Integer.class);

               for (int i = 1; i <= numOfStudents; i++ ) {
                   String studentID = snapshot.child("Courses").child(classID).child("students").child(Integer.toString(i)).getValue().toString();
                   String studentName = snapshot.child("Users").child(studentID).child("name").getValue().toString();

                   Student newStudent = new Student(studentName, studentID);
                   allstudents.add(newStudent);
               }

               System.out.println(allstudents.get(0).getName());

               setRvS(allstudents);
               databaseRef.removeEventListener(this);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerViewAssignmentList = findViewById(R.id.rv_displayAssignmentsForTeacher);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAssignmentList.setLayoutManager(layoutManager1);



        // Find student names and IDs and create ArrayList of Assignment objects
        final DatabaseReference databaseRefa = FirebaseDatabase.getInstance().getReference(); // "Courses/"+ classID + "/students");

        databaseRefa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Assignment> allAssignments = new ArrayList<Assignment>();
                int numOfAssignments = snapshot.child("Courses").child(classID).child("numOfassignments").getValue(Integer.class);

                for (int i = 1; i <= numOfAssignments; i++ ) {
                    String assignmentName = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("name").getValue().toString();
                    String assignmentInstr = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("instructions").getValue().toString();
                    int assignmentNum =  snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("assignmentNum").getValue(Integer.class);
                    Boolean assignmentStationary = snapshot.child("Courses").child(classID).child("assignments").child(Integer.toString(i)).child("stationary").getValue(Boolean.class);

                    Assignment newAssignment = new Assignment(assignmentName,assignmentInstr,assignmentStationary,classID, assignmentNum,false);
                    allAssignments.add(newAssignment);
                }

                setRvA(allAssignments);
                databaseRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button button_back = (Button) findViewById(R.id.back_button);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });


    }

    private void openActivity() {
        Intent intent = new Intent(this,TeacherMainMenu.class);
        startActivity(intent);
    }


    public void setRvS(ArrayList<Student> classStudents) {
        List<Student> listExample = new ArrayList<Student>();
        for (Student student: classStudents) {
            listExample.add(student);
        }

        RecycleVewAdaptorClassView adaptor = new RecycleVewAdaptorClassView(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }


    public void setRvA(ArrayList<Assignment> classStudents) {
        List<Assignment> listExample = new ArrayList<Assignment>();
        for (Assignment assignment: classStudents) {
            listExample.add(assignment);
        }

        RecycleVewAdaptorAssignmentsListForTeacher adaptor = new RecycleVewAdaptorAssignmentsListForTeacher(listExample);
        recyclerViewAssignmentList.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

    public void createAssignment(View view) {
        Intent i = new Intent(this, AssignmentActivity.class);
        i.putExtra("classID", classKey);
        i.putExtra("className", className);
        startActivity(i);
    }
}