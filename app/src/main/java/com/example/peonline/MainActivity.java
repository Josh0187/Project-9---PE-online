package com.example.peonline;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.gps.LocationTracker;
import com.google.android.gms.maps.model.LatLng;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
        } else {
            // Disable vibrate
        }
    }
}