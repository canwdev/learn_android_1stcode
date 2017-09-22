package com.example.servicebestpractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    //finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = (Button) findViewById(R.id.button_Start);
        Button buttonPause = (Button) findViewById(R.id.button_Pause);
        Button buttonCancel = (Button) findViewById(R.id.button_Cancel);
        buttonStart.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

        checkMyPermission();
    }

    @Override
    public void onClick(View view) {
        if (downloadBinder == null || !checkMyPermission()) {
            return;
        }
        switch (view.getId()) {
            case R.id.button_Start:
                String url = ((EditText) findViewById(R.id.editText_url))
                        .getText().toString();
                downloadBinder.startDownload(url);
                break;
            case R.id.button_Pause:
                downloadBinder.pauseDownload();
                break;
            case R.id.button_Cancel:
                downloadBinder.CancelDownload();
                break;
            default:
                break;
        }
    }

    private boolean checkMyPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        } else {
            return true;
        }
    }
}
