package com.example.peonline.studentmain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.login.MainActivity;
import com.example.peonline.teachermain.Assignment;
import com.example.peonline.video.VideoSubmission;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StudentMainMenu extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

        recyclerView = findViewById(R.id.assignment_cycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<Assignment> listExample = new ArrayList<>();
        listExample.add(new Assignment("title 1","body 1",true));
        listExample.add(new Assignment("title 2","body 2",false));
        listExample.add(new Assignment("title 3","body 3",true));


        RecycleVewAdaptor adaptor = new RecycleVewAdaptor(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void locationActivity(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

    public void videoActivity(View view) {
        Intent intent = new Intent(this, VideoSubmission.class);
        startActivity(intent);
    }

    public void viewAssignments(View view) {

    }

    public void viewSelfStats(View view) {

    }
}