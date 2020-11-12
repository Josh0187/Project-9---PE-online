package com.example.peonline;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    EditText emailID, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.et_email2);
        password = findViewById(R.id.et_password2);

    }

    // onClick for "Already have an account" Text View
    public void loginScreen(View view) {
        Intent loginIntent = new Intent(register.this, MainActivity.class);
        startActivity(loginIntent);
    }

    // OnClick for signUp Button
    public void signUp(View view) {
        String email = emailID.getText().toString();
        String pwd = password.getText().toString();

        if (email.isEmpty()) {
            emailID.setError("Please enter email");
            emailID.requestFocus();
        }
        else if(pwd.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        }
        else if(email.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(register.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        }
        // User has filled out everything
        else if(!(email.isEmpty() && pwd.isEmpty())) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(register.this, "Sign up unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent loginIntent = new Intent(register.this, MainActivity.class);
                        startActivity(loginIntent);
                    }
                }
            });
        }

        else {
            Toast.makeText(register.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }
}

