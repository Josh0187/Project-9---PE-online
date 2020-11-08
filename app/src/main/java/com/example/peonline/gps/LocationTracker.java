package com.example.peonline.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.peonline.DataCalculation;
import com.example.peonline.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationTracker extends FragmentActivity implements OnMapReadyCallback {


    private LocationManager locationManager;
    private LatLng latLng;
    private ArrayList<LatLng> points;

    private static final int MIN_DIST = 5; //5 meters
    private static final int MIN_TIME = 5000; //5 seconds

    private LocationListener locationListener;

    // needs a default constructor in order to exist in manifest as an action
    // so it could be activated whe user press button (@Kris)
    public LocationTracker() { }

  //function below should display map on the activity_maps.xml (line 8 code),
  //however i got an error message. (@Kris)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);   // u displayed activity_maps.xml but did not
                                                   // code how fragment of the map will be added to context view
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        final Button Stop = (Button) findViewById(R.id.Stop_gps_button);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopUsingGPS();            //i called your method to stop tracking (@kristina)
            }
        });
    }

    // added for future, so stop button can open data calculation activity when pressed.
   /* private void StopLocationTracker() {
        Intent intent = new Intent(this, DataCalculation.class);
        startActivity(intent);
    }*/

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onMapReady(GoogleMap map) {
        final GoogleMap googleMap = map;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Log.w("location not null",  "=" + true);
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        points.add(latLng);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("My Position"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    points.add(latLng);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("My Position"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }

    public void stopUsingGPS(){
        if(locationManager != null) {
            locationManager.removeUpdates((LocationListener) LocationTracker.this);
        }
    }
}

/* please add this to where ever the code is for app startup. if no gps permission, app should not run
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PackageManager.PERMISSION_GRANTED);
 */