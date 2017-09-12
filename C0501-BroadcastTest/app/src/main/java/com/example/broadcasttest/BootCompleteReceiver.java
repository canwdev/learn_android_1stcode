package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// 静态注册的广播接收器
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String DateTime = timesdf.format(new Date()).toString();//获取系统时间

        Toast.makeText(context, "BroadcastTest: Boot Complete " + DateTime, Toast.LENGTH_SHORT).show();
    }
}
