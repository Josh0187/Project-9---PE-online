package com.example.peonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

// Use for student submission page for each stationary assignment
public class student_assignment_submission extends AppCompatActivity {

    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment_submission);
    }

    // Goes in the onClick listener of Record Button when we implement stationary student submission
    public void captureVideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // if(videoIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(videoIntent, VIDEO_REQUEST);
        //  }
    }

    // Goes in the onClick listener of replay Button when we implement stationary student submission
    // Can also use in Teacher Assignment view for stationary exercises
    public void playVideo() {
        Intent playIntent = new Intent(this, Play_Video_Activity.class);
        playIntent.putExtra("videoUri", videoUri.toString());
        startActivity(playIntent);
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
