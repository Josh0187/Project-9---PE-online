package com.example.peonline;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.peonline.gps.LocationTracker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

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

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void Register(View view) {
        Intent registerIntent = new Intent(this, register.class);
        startActivity(registerIntent);
    }

    public void login(View view) {
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
                        Intent intToHome = new Intent(MainActivity.this, home.class);
                        startActivity(intToHome);
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

