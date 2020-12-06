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

import android.view.View;
import android.widget.TextView;

import com.example.peonline.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentStats extends AppCompatActivity {

    private String studentID;
    private String studentName;
    private RecyclerView recyclerView;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_stats);

        Bundle extras = getIntent().getExtras();
        studentID = extras.getString("studentID");
        studentName = extras.getString("studentName");

        name = findViewById(R.id.tv_studName);
        name.setText(studentName);

        recyclerView = findViewById(R.id.rv_studentStats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Find student stats and display in recycle view
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("students/"+studentID+"/statistics");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Stats> allStats = new ArrayList<Stats>();

                for (DataSnapshot stat: snapshot.getChildren()) {
                    Stats newStat = stat.getValue(Stats.class);
                    System.out.println(newStat.getDistance());
                    System.out.println(newStat.getSpeed());
                    System.out.println(newStat.getTime());
                    allStats.add(newStat);
                }

                setRvS(allStats);
                databaseRef.removeEventListener(this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void setRvS(ArrayList<Stats> studentStats) {
        List<Stats> listExample = new ArrayList<Stats>();
        for (Stats stat: studentStats) {
            listExample.add(stat);
        }

        RecycleVewAdaptorStudentStats adaptor = new RecycleVewAdaptorStudentStats(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }
}