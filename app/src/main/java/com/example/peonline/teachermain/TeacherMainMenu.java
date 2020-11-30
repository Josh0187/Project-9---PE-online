package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class TeacherMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_menu);

        //switch to assignment add activity
        Button add_assignment_button = (Button) findViewById(R.id.add_assignment);
        add_assignment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainMenu.this, AssignmentActivity.class);
                startActivity(intent);
            }
        });


    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void viewClass(View view) {

    }
}