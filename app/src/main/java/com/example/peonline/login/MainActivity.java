package com.example.peonline.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.studentmain.StudentMainMenu;
import com.example.peonline.teachermain.TeacherMainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    EditText emailID, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);

        //FirebaseAuth.getInstance().signOut();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();

                    String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseref = database.getReference("Users/"+ID);

                    databaseref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            // student account
                            if (user.getTeacher() == false) {
                                Intent intToStudentMainMenu = new Intent(MainActivity.this, StudentMainMenu.class);
                                startActivity(intToStudentMainMenu);
                            }
                            // Teacher account
                            else {
                                Intent intToTeacherMainMenu = new Intent(MainActivity.this, TeacherMainMenu.class);
                                startActivity(intToTeacherMainMenu);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void Register(View view) {
        Intent registerIntent = new Intent(this, Register.class);
        startActivity(registerIntent);
    }

    public void login(View view) {
        String email = emailID.getText().toString();
        String pwd = password.getText().toString();
        User user;

        if (email.isEmpty()) {
            emailID.setError("Please enter email");
            emailID.requestFocus();
        }
        else if(pwd.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        }
        else if(email.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        }
        // User has filled out everything
        else if(!(email.isEmpty() && pwd.isEmpty())) {
            mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Users/"+ID);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                // student account
                                if (user.getTeacher() == false) {
                                    Intent intToStudentMainMenu = new Intent(MainActivity.this, StudentMainMenu.class);
                                    startActivity(intToStudentMainMenu);
                                }
                                // Teacher account
                                else {
                                    Intent intToTeacherMainMenu = new Intent(MainActivity.this, TeacherMainMenu.class);
                                    startActivity(intToTeacherMainMenu);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            });

        }

        else {
            Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

