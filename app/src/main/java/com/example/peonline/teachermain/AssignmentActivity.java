package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peonline.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AssignmentActivity extends AppCompatActivity {

    //somehow here storage providing function should be implemented
    //to store list with all added assignments

    //in this activity all needed info is collected and assigned to Strings below

    private String Ass_title;
    private String Ass_body;
    public Boolean Stationary = false;
    private CheckBox check1;
    private String classID;
    private String className;
    private Button bt_save_assignment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment);

        bt_save_assignment = findViewById(R.id.save_assignment);
        bt_save_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_assignment();
            }
        });

        Bundle extras = getIntent().getExtras();
        final String classKey = extras.getString("classID");
        final String className1 = extras.getString("className");
        classID = classKey;
        className = className1;

        //CheckBox
        addListnerToCheckBox();

    }

    public void addListnerToCheckBox(){
        check1 = (CheckBox) findViewById(R.id.checkBox_stationary);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    //problem with checkiong the checkbox twice
                    Stationary = true;
                }
            }
        });
    }

    public void setAss_title() {
        EditText simpleEditText = (EditText) findViewById(R.id.assignment_name);
        Ass_title = simpleEditText.getText().toString();

    }

    public void setAss_body() {
        EditText simpleEditText2 = (EditText) findViewById(R.id.assignment_description);
        Ass_body = simpleEditText2.getText().toString();
    }


    public void save_assignment() {
        System.out.println("In save assignment functions");
        System.out.println("classID is" + classID);
        setAss_title();
        setAss_body();
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference( "Courses/"+ classID);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("inside value event listener");
                int numOfAssignments = snapshot.child("numOfassignments").getValue(Integer.class);

                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("assignments").child(Integer.toString(numOfAssignments+1)).child("name").setValue(Ass_title);
                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("assignments").child(Integer.toString(numOfAssignments+1)).child("assignmentNum").setValue(numOfAssignments+1);
                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("assignments").child(Integer.toString(numOfAssignments+1)).child("instructions").setValue(Ass_body);
                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("assignments").child(Integer.toString(numOfAssignments+1)).child("stationary").setValue(Stationary);
                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("assignments").child(Integer.toString(numOfAssignments+1)).child("numOfsubmissions").setValue(0);

                FirebaseDatabase.getInstance().getReference().child("Courses").child(classID).child("numOfassignments").setValue(numOfAssignments+1);

                System.out.println("Should have saved new assignment");
                databaseRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent i = new Intent(this, ViewClass.class);
        i.putExtra("classID", classID);
        i.putExtra("className", className);
        startActivity(i);
    }



}
