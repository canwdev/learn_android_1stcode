package com.example.playmediatest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.videoView);

        Button play = (Button) findViewById(R.id.button_play);
        Button pause = (Button) findViewById(R.id.button_pause);
        Button replay = (Button) findViewById(R.id.button_replay);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);

        // 检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "Permission denied: \n" + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void initVideoPath() {
        File file = new File("/sdcard/Movies/video.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.button_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.button_replay:
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView!=null) {
            videoView.suspend();
        }
    }
}
