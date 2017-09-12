package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// 发送自定义广播的（静态）广播接收器
public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String DateTime = timesdf.format(new Date()).toString();//获取系统时间

        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("BroadcastTest: ");
        builder.setMessage(DateTime);
        builder.show();*/

        Toast.makeText(context, "BroadcastTest: MyBroadcastReceiver " + DateTime
                + "\n优先级100", Toast.LENGTH_SHORT).show();

    }
}
