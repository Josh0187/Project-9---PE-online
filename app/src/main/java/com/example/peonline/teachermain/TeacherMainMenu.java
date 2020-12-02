package com.example.peonline.teachermain;

import android.content.Intent;
import android.os.Bundle;

import com.example.peonline.R;
import com.example.peonline.login.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class TeacherMainMenu extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_menu);

        //switch to assignment add activity
        Button add_assignment_button = (Button) findViewById(R.id.add_assignment);
        add_assignment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainMenu.this, AssignmentActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_teachMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Find classID and class Name in database
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseref = database.getReference("Users/" + ID);

        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> classIDs = new ArrayList<String>();
                for (DataSnapshot dataSnapshot : snapshot.child("classID").getChildren()) {
                    classIDs.add(dataSnapshot.getValue().toString());
                }
                final ArrayList<String> allClassIDs = classIDs;
                System.out.println(allClassIDs);
                if (classIDs.isEmpty()) {
                    databaseref.removeEventListener(this);
                }
                else {
                        final DatabaseReference databaseRefCourse = database.getReference("Courses/");

                        databaseRefCourse.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Class> allClasses = new ArrayList<Class>();

                                for (String courseKey: allClassIDs) {
                                    String className = snapshot.child(courseKey).child("courseName").getValue().toString();
                                    System.out.println(className);
                                    Class newClass = new Class(className, courseKey);
                                    allClasses.add(newClass);
                                }



                                setRv(allClasses);
                                databaseRefCourse.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                databaseref.removeEventListener(this);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void setRv(ArrayList<Class> classes) {
        List<Class> listExample = new ArrayList<Class>();
        for (Class classe: classes) {
            listExample.add(classe);
        }

        RecycleVewAdaptor adaptor = new RecycleVewAdaptor(listExample);
        recyclerView.setAdapter(adaptor);

        adaptor.notifyDataSetChanged();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void goToCreateCourse(View view) {
        Intent intent = new Intent(this, createCourse.class);
        startActivity(intent);
    }
}