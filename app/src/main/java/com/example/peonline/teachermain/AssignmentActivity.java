package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peonline.R;

public class AssignmentActivity extends AppCompatActivity {

    //somehow here storage providing function should be implemented
    //to store list with all added assignments

    //in this activity all needed info is collected and assigned to Strings below

    private String Ass_title;
    private String Ass_body;
    public Boolean Stationary = false;

    private CheckBox check1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment);

        //go back to main menu
        Button back_button = (Button) findViewById(R.id.save_assignment);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentActivity.this, TeacherMainMenu.class);
                startActivity(intent);
            }
        });

        //CheckBox
        addListnerToCheckBox();
    }

    public void addListnerToCheckBox(){
        check1 = (CheckBox) findViewById(R.id.checkBox_stationary);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
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



}
