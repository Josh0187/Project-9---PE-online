package com.example.peonline;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.gps.LocationTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button Start = (Button) findViewById(R.id.Start_gps_button);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLocationTracker();
            }
        });
    }

    private void StartLocationTracker() {
        Intent intent = new Intent(this, LocationTracker.class);
        startActivity(intent);
    }
}