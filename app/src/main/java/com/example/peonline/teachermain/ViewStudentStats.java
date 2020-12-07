package com.example.peonline.teachermain;

import android.os.Bundle;

import com.example.peonline.database.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.example.peonline.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentStats extends AppCompatActivity {

    private String studentID;
    private String studentName;
    private TextView name;
    private ArrayList<Stats> allStats;
    GraphView lineGraph;
    DataPoint[] dataPointsdistance;
    DataPoint[] dataPointsspeed;
    DataPoint[] dataPointstime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_stats);

        Bundle extras = getIntent().getExtras();
        studentID = extras.getString("studentID");
        studentName = extras.getString("studentName");

        name = findViewById(R.id.tv_studName);
        name.setText(studentName);

        // Find student stats and display in recycle view
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("students/"+studentID+"/statistics");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                allStats = new ArrayList<>();

                for (DataSnapshot stat: snapshot.getChildren()) {
                    Stats newStat = stat.getValue(Stats.class);
                    allStats.add(newStat);
                }

                lineGraph = (GraphView) findViewById(R.id.graph);
                int size = allStats.size();
                dataPointsdistance = new DataPoint[size];
                dataPointsspeed = new DataPoint[size];
                dataPointstime = new DataPoint[size];
                for (int i = 0; i < size; i++) {
                    dataPointsdistance[i] = new DataPoint(i + 1, allStats.get(i).distance);
                    dataPointsspeed[i] = new DataPoint(i + 1, allStats.get(i).speed);
                    dataPointstime[i] = new DataPoint(i + 1, allStats.get(i).time);
                }
                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(dataPointsdistance);
                lineGraph.addSeries(lineGraphSeries);

                GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Exercise #");
                gridLabel.setVerticalAxisTitle("Distance (m)");


                databaseRef.removeEventListener(this);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void distanceGraph(View view) {
        lineGraph.removeAllSeries();
        GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Exercise #");
        gridLabel.setVerticalAxisTitle("Distance (m)");
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(dataPointsdistance);
        lineGraph.addSeries(lineGraphSeries);
    }

    public void speedGraph(View view) {
        lineGraph.removeAllSeries();
        GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Exercise #");
        gridLabel.setVerticalAxisTitle("Speed (m/s)");
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(dataPointsspeed);
        lineGraph.addSeries(lineGraphSeries);
    }

    public void timeGraph(View view) {
        lineGraph.removeAllSeries();
        GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Exercise #");
        gridLabel.setVerticalAxisTitle("Time (s)");
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(dataPointstime);
        lineGraph.addSeries(lineGraphSeries);
    }

}