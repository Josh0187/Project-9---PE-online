package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.login.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class student_main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intToMain = new Intent(student_main_menu.this, MainActivity.class);
        startActivity(intToMain);
    }
}