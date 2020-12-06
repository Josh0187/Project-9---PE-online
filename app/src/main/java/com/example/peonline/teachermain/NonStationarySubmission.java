package com.example.peonline.teachermain;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peonline.R;

public class NonStationarySubmission extends AppCompatActivity {

    private String studentName;
    private float distance;
    private double speed;
    private double time;
    private TextView tv_student_name;
    private TextView tv_distance;
    private TextView tv_speed;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_stationary_submission);

        tv_student_name = findViewById(R.id.tv_studentName);
        tv_distance = findViewById(R.id.tv_dist);
        tv_speed = findViewById(R.id.tv_sp);
        tv_time = findViewById(R.id.tv_Time);

        Bundle extras = getIntent().getExtras();

        studentName = extras.getString("studentName");
        distance = extras.getFloat("distance");
        speed = extras.getDouble("speed");
        time = extras.getDouble("time");

        tv_student_name.setText(studentName);
        tv_distance.setText(Float.toString(distance));
        tv_speed.setText(Double.toString(speed));
        tv_time.setText(Double.toString(time));

    }
}