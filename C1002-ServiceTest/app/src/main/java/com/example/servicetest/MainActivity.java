package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity!! ";

    private MyService.DownloadBinder downloadBinder;
    // 与服务的连接
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Thread id="+Thread.currentThread().getId());
    }


    public void button_startService(View view) {
        // 启动服务
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void button_stopService(View view) {
        // 停止服务
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
    }

    public void button_bindService(View view) {
        Intent bindIntent = new Intent(this, MyService.class);
        // 绑定服务
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    public void button_unBindService(View view) {
        // 解绑服务
        unbindService(connection);
    }

    public void button_startIntentService(View view) {
        Intent intentService = new Intent(this, MyIntentService.class);
        startService(intentService);
    }
}
