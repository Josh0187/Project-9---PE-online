package com.example.peonline.video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.peonline.R;
import com.example.peonline.login.MainActivity;
import com.example.peonline.studentmain.StudentMainMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

// Use for student submission page for each stationary assignment
public class VideoSubmission extends AppCompatActivity {

    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    MediaController mediaController;
    private String classKey;
    private int assignmentNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_submission);


        Bundle extras = getIntent().getExtras();

        final String classID = extras.getString("classID");
        classKey = classID;
        final int assignmentNum1 = extras.getInt("assignmentNum");
        assignmentNum  = assignmentNum1;

        storageReference = FirebaseStorage.getInstance().getReference("videos/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses/"+classKey+"/assignments/"+Integer.toString(assignmentNum1));

    }

    private String getFileExt(Uri videoUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    // Goes in the onClick listener of Record Button when we implement stationary student submission
    public void captureVideo(View view) {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // if(videoIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(videoIntent, VIDEO_REQUEST);
        //  }
    }

    // Goes in the onClick listener of replay Button when we implement stationary student submission
    // Can also use in Teacher Assignment view for stationary exercises
    public void playVideo(View view) {
        Intent playIntent = new Intent(this, PlayVideoActivity.class);
        playIntent.putExtra("videoUri", videoUri.toString());
        startActivity(playIntent);
    }

    // OnClick for submission button
    public void UploadVideo(View view) {
        if (videoUri != null) {
            final String uniqueID = UUID.randomUUID().toString();
            StorageReference reference = storageReference.child(uniqueID + "." + getFileExt(videoUri));
            reference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT);
                    Video video = new Video(uniqueID, taskSnapshot.getUploadSessionUri().toString());
                    databaseReference.child("submissions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(video);
                    //Go back to student main menu after submission
                    Intent studentMainIntent = new Intent(VideoSubmission.this, StudentMainMenu.class);
                    startActivity(studentMainIntent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No file", Toast.LENGTH_SHORT);
        }
    }

    // Paired with captureVideo()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            videoUri = data.getData();
        }
    }


}
