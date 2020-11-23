package com.example.peonline.gps;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.peonline.R;

public class GPSActivity extends AppCompatActivity {

    private static final String TAG = "gpsactivity";
    private boolean started = false;
    private GPSService gpsService;
    private boolean bound = false;
    private float distance = 0;

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            GPSService.LocalBinder binder = (GPSService.LocalBinder) service;
            gpsService = binder.getService();
            bound = true;
            Log.d(TAG, "onServiceConnected: ");
        }

        public void onServiceDisconnected(ComponentName className) {
            gpsService = null;
            bound = false;
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_activity);
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
    }

    //start and stop gps
    public void startGPS(View view) {
        Button button = findViewById(R.id.GPSTracking);
        Intent intent = new Intent(this, GPSService.class);
        if (!started) {
            button.setText("Stop Tracking Distance");
            startService(intent);
            if (!bound) {
                bindService(intent, connection, BIND_AUTO_CREATE);
                Log.d(TAG, "bind ");
            }
            started = true;
            Log.d(TAG, "gps started ");
        }
        else {
            button.setText("Start Tracking Distance");
            distance = gpsService.getDistance();
            Log.d(TAG, "distance travelled: " + distance);
            stopService(intent);
            started = false;

            Log.d(TAG, "gps stopped ");
        }
    }
}
