package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.database.Course;
import com.example.peonline.login.MainActivity;
import com.example.peonline.login.User;
import com.example.peonline.studentmain.StudentMainMenu;
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

public class TeacherMainMenu extends AppCompatActivity {

    public static TextView courseKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_menu);
        courseKey = findViewById(R.id.tv_courseKey);


        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseref = database.getReference("Users/"+ID);

        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                courseKey.setText(user.getClassID());
                databaseref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent toMainIntent = new Intent(TeacherMainMenu.this, MainActivity.class);
        startActivity(toMainIntent);
    }

    public void viewClass(View view) {

    }

    public void createCourse(View view) {
        Intent createClassIntent = new Intent(TeacherMainMenu.this, createCourse.class);
        startActivity(createClassIntent);
    }


}