package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Intent 过滤器
    private IntentFilter intentFilter;
    // 网络变化广播接收器
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        // 动态注册广播接收器
        registerReceiver(networkChangeReceiver, intentFilter);

        Button button = (Button) findViewById(R.id.button_send_my_broadcast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送自定义广播（targetSdkVersion 26+可能存在问题）
                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                // 发送标准广播
//                sendBroadcast(intent);
                // 发送有序广播（可被阻断）
                sendOrderedBroadcast(intent, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 一定要取消动态注册的广播接收器，TODO 否则?
        unregisterReceiver(networkChangeReceiver);
    }

    // 网络变化广播接收器
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取网络连接管理器
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            // 判断连接状态
            if(networkInfo!=null && networkInfo.isAvailable()) {
                Toast.makeText(context, "BroadcastTest：网络可用", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "BroadcastTest：网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
