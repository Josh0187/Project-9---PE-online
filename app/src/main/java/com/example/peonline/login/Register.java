package com.example.peonline.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Register extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    EditText emailID, password, Name;
    Button student_or_teacher;
    Boolean isTeacher = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.et_email2);
        password = findViewById(R.id.et_password2);
        Name = findViewById(R.id.et_Name);
        student_or_teacher = findViewById(R.id.student_or_teacher);


    }

    public void setIsTeacher(View view) {
        if(!isTeacher) {
            isTeacher = true;
            student_or_teacher.setText("Teacher");
        }
        else {
            isTeacher = false;
            student_or_teacher.setText("Student");
        }
    }

    // onClick for "Already have an account" Text View
    public void loginScreen(View view) {
        Intent loginIntent = new Intent(Register.this, MainActivity.class);
        startActivity(loginIntent);
    }

    // OnClick for signUp Button
    public void signUp(View view) {

        // Collecting Fields
        String email = emailID.getText().toString();
        String pwd = password.getText().toString();
        final String name = Name.getText().toString();
        final User newuser = new User(name, isTeacher);



        // Check for empty fields
        if (name.isEmpty()) {
            Name.setError("Please enter name");
            Name.requestFocus();
        }

        else if (email.isEmpty()) {
            emailID.setError("Please enter email");
            emailID.requestFocus();
        }
        else if(pwd.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        }
        else if(email.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(Register.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        }
        // User has filled out everything
        else if(!(email.isEmpty() && pwd.isEmpty())) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Register.this, "Sign up unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    // account creation is successful
                    else {
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newuser);
                        Intent loginIntent = new Intent(Register.this, MainActivity.class);
                        startActivity(loginIntent);
                    }
                }
            });
        }

        else {
            Toast.makeText(Register.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }
}

