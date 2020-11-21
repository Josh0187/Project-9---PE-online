package com.example.peonline.database;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    public Database() { }

    // Updates stats of student in database
    // parameter : Student object

    public void updateStatsDatabase(Student student) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        int n = student.getNumOfExercises();
        double[][] stats =  student.getStatistics();

        for (int i = 0; i < n; i++) {
            FirebaseDatabase.getInstance().getReference().child("Students").child(userID).child("statistics").child("non-stationary" + Integer.toString(i));
            FirebaseDatabase.getInstance().getReference().child("Students").child(userID).child("statistics").child("non-stationary" + Integer.toString(i)).child("time").setValue(stats[i][0]);
            FirebaseDatabase.getInstance().getReference().child("Students").child(userID).child("statistics").child("non-stationary" + Integer.toString(i)).child("distance").setValue(stats[i][1]);
            FirebaseDatabase.getInstance().getReference().child("Students").child(userID).child("statistics").child("non-stationary" + Integer.toString(i)).child("speed").setValue(stats[i][2]);
        }
    }



    //
    // WORK IN PROGRESS : RETRIEVING DATA FROM DATABASE (DOES NOT WORK YET)
    //
    public Student retreiveStudent(String studentName) {
        Student student = new Student(studentName);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students").child(studentName).child("statistics");
        final double[][] statistics = new double[50][3];
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int c = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    double[] stats = {};
                    c = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        stats[c++] = (double)snapshot1.getValue();
                    }
                    statistics[i++] = stats;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        student.setStatistics(statistics);

        return student;
    }
}
