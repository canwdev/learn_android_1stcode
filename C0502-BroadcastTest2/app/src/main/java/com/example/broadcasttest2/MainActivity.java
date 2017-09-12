package com.example.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager broadcastManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取实例
        broadcastManager = LocalBroadcastManager.getInstance(this);

        // 注册本地广播接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest2.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        broadcastManager.registerReceiver(localReceiver, intentFilter);

        Button button = (Button) findViewById(R.id.button_send_local_broadcast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcasttest2.LOCAL_BROADCAST");
                broadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 反注册本地广播接收器
        broadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context
                    , "BroadcastTest2: Received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}
