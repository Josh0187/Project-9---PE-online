package com.example.peonline.teachermain;

import android.os.Bundle;

import com.example.peonline.database.Student;
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

public class ViewClass extends AppCompatActivity {

    TextView tv_ClassName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);
        tv_ClassName = findViewById(R.id.tv_className);

        Bundle extras = getIntent().getExtras();

        final String classID = extras.getString("classID");
        final String className = extras.getString("className");
        tv_ClassName.setText(className);


        recyclerView = findViewById(R.id.rv_students);
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

               setRv(allstudents);
               databaseRef.removeEventListener(this);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    public void setRv(ArrayList<Student> classStudents) {
        List<Student> listExample = new ArrayList<Student>();
        for (Student student: classStudents) {
            listExample.add(student);
        }

        RecycleVewAdaptorClassView adaptor = new RecycleVewAdaptorClassView(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }
}