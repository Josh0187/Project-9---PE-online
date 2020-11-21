package com.example.peonline.video;

import android.net.Uri;
import android.os.Bundle;

import com.example.peonline.R;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.VideoView;

// Use when replaying video for student or teacher
public class PlayVideoActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mVideoView = findViewById(R.id.videoView2);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }
}