package com.example.peonline;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationTracker extends Activity implements LocationListener {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng latLng;
    private ArrayList<Integer> stats;
    private ArrayList<LatLng> points;

    private static final int MIN_DISTANCE_UPDATE = 5; //5 meters
    private static final int MIN_TIME_UPDATE = 5000; //5 seconds

    /* session stats should be of the format {distance, time, speed (optional, can be calculated later)}
     * public class LocationTracker(ArrayList<Integer> sessionStats) {
     *      stats = sessionStats;
     * }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //locationManager.
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            //latLng = location.get;
            points.add(latLng);
        }

    }
}








/* please add this to where ever the code is for app startup. if no gps permission, app should not run
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PackageManager.PERMISSION_GRANTED);
 */