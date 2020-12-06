package com.example.peonline.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.example.peonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

// Use when replaying video for student or teacher
public class PlayVideoActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mVideoView = findViewById(R.id.videoView2);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));


        System.out.println(videoUri.toString());
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();


    }
}