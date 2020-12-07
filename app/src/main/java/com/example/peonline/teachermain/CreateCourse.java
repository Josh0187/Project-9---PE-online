package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.database.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.peonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateCourse extends AppCompatActivity {

    EditText className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        className = findViewById(R.id.et_className);

    }



    public void createClass(View view) {
        Course newCourse = new Course(className.getText().toString());
        final String Key = newCourse.getCourseKey();
        newCourse.updateDatabase();

        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseref = database.getReference("Users/"+ID);

        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int numOfClasses = snapshot.child("numOfClasses").getValue(Integer.class);
                ArrayList<String> classIDs = new ArrayList<String>();
                for (DataSnapshot dataSnapshot : snapshot.child("classID").getChildren()) {
                    classIDs.add(dataSnapshot.getValue().toString());
                }
                classIDs.add(Key);

                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numOfClasses").setValue(classIDs.size());

                for (int i = 0; i < classIDs.size(); i++) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("classID").child(Integer.toString(i)).setValue(classIDs.get(i));
                }
                databaseref.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent teacherMainIntent = new Intent(CreateCourse.this, TeacherMainMenu.class);
        startActivity(teacherMainIntent);
    }
}