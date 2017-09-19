package com.example.broadcasttest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// 静态注册的广播接收器
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String DateTime = timesdf.format(new Date()).toString();//获取系统时间


        // 显示一条通知
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("BroadcastTest: Boot Complete ")
                .setContentText(DateTime)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        manager.notify(1, notification);

        Toast.makeText(context, "BroadcastTest: Boot Complete " + DateTime, Toast.LENGTH_SHORT).show();
    }
}
