package com.example.peonline.gps;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class GPSService extends Service {

    private static final String TAG = "peonlinegpsservice";
    private LocationManager locationManager = null;
    private static final int LOCATION_INTERVAL = 1;
    private static final int LOCATION_DISTANCE = 5;
    private final IBinder iBinder = new LocalBinder();
    private float distance = 0;
    private ArrayList<Location> points = new ArrayList<>();

    public class LocalBinder extends Binder {
        GPSService getService() {
            return GPSService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return iBinder;
    }

    private class LocationListener implements android.location.LocationListener {

        @SuppressLint("MissingPermission")
        public LocationListener(String provider) {
            Log.d(TAG, "LocationListener " + provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + location);
            points.add(location);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    LocationListener[] locationListener = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        distance = 0;
        initializeLocationManager();
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    locationListener[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    locationListener[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null) {
            for (int i = 0; i < locationListener.length; i++) {
                try {
                    locationManager.removeUpdates(locationListener[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void calcDist() {
        for (int i = 0; i < points.size() - 1; i++) {
            distance += points.get(i).distanceTo(points.get(i + 1));
            Log.d(TAG, "calcDist: " + distance);
        }
    }
    public float getDistance() {
        calcDist();
        return distance;
    }
}