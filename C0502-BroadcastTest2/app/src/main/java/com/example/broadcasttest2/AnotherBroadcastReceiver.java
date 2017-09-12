package com.example.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AnotherBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String DateTime = timesdf.format(new Date()).toString();//获取系统时间

        abortBroadcast();
        Toast.makeText(context
                , "BroadcastTest2: AnotherBroadcastReceiver " + DateTime
                + "\n给我一片二向箔，清理用。"
                + "\n优先级200：已将广播阻断！", Toast.LENGTH_SHORT).show();

    }
}
