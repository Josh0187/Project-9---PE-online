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
                    System.out.println(newStat.getDistance());
                    System.out.println(newStat.getSpeed());
                    System.out.println(newStat.getTime());
                    allStats.add(newStat);
                }

                databaseRef.removeEventListener(this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GraphView lineGraph = (GraphView) findViewById(R.id.graph);
        int size = allStats.size();
        DataPoint[] dataPoints = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            dataPoints[i] = new DataPoint(i + 1, allStats.get(i).distance);
        }
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(dataPoints);
        lineGraph.addSeries(lineGraphSeries);

    }

}