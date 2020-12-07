package com.example.peonline.gps;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.peonline.R;
import com.example.peonline.studentmain.StudentMainMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GPSActivity extends AppCompatActivity {

    private static final String TAG = "gpsactivity";
    Boolean stopped = false;
    private boolean started = false;
    private GPSService gpsService;
    private boolean bound = false;
    private float distance;
    private long startTime, endTime;
    private long elapsedTime;
    double elapsedSeconds;
    private double speed;
    private String classKey;
    private int assignmentNum;
    private DatabaseReference databaseReference;
    private TextView tv_distance;
    private TextView tv_time;
    private TextView tv_speed;


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

        tv_distance = findViewById(R.id.tv_distance);
        tv_time = findViewById(R.id.tv_time);
        tv_speed = findViewById(R.id.tv_speed);

        Bundle extras = getIntent().getExtras();

        final String classID = extras.getString("classID");
        classKey = classID;
        final int assignmentNum1 = extras.getInt("assignmentNum");
        assignmentNum  = assignmentNum1;

        databaseReference = FirebaseDatabase.getInstance().getReference("Courses/"+classKey+"/assignments/"+Integer.toString(assignmentNum1));

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startGPS(View view) {
        Button button = findViewById(R.id.GPSTracking);
        Intent intent = new Intent(this, GPSService.class);
        if (!started) {
            startTime = SystemClock.elapsedRealtime();
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
            stopped = true;
            endTime = SystemClock.elapsedRealtime();
            elapsedTime = endTime - startTime;
            elapsedSeconds = elapsedTime/1000;
            Log.d(TAG, "startGPS: " + elapsedTime + " " + elapsedSeconds);
            button.setText("Start Tracking Distance");
            distance = gpsService.getDistance();
            Log.d(TAG, "distance travelled: " + distance);
            speed = distance/elapsedSeconds;
            stopService(intent);
            started = false;

            tv_distance.setText(Float.toString(distance));
            tv_time.setText(Double.toString(elapsedSeconds));
            tv_speed.setText(Double.toString(speed));

            Log.d(TAG, "gps stopped ");
        }
    }

    public void submit(View view) {
        if (stopped) {
            // Save submission to database
            databaseReference = FirebaseDatabase.getInstance().getReference("Courses/" + classKey + "/assignments/" + Integer.toString(assignmentNum));
            databaseReference.child("submissions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("distance").setValue(distance);
            databaseReference.child("submissions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("speed").setValue(speed);
            databaseReference.child("submissions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("time").setValue(elapsedSeconds);


            // Update student stats
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseref = database.getReference("students/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/statistics");

            databaseref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long numOfnonStat = snapshot.getChildrenCount();

                    databaseref.child("non-stationary" + Long.toString(numOfnonStat)).child("distance").setValue(distance);
                    databaseref.child("non-stationary" + Long.toString(numOfnonStat)).child("speed").setValue(speed);
                    databaseref.child("non-stationary" + Long.toString(numOfnonStat)).child("time").setValue(elapsedSeconds);

                    databaseref.removeEventListener(this);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //Go back to student main menu
            Intent i = new Intent(this, StudentMainMenu.class);
            startActivity(i);

        }
        else {
            Toast.makeText(getApplicationContext(),"Please stop the GPS before submitting",Toast.LENGTH_SHORT).show();
        }

    }
}
