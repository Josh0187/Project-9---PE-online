package com.example.peonline.video;

import android.net.Uri;
import android.os.Bundle;

import com.example.peonline.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.VideoView;

// Use when replaying video for student or teacher
public class Play_Video_Activity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__video_);
        mVideoView = findViewById(R.id.videoView2);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }
}