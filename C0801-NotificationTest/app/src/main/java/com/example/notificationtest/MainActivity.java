package com.example.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    // 通知的id
    private int notiId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonShowNoti = (Button) findViewById(R.id.button_showNotification);
        buttonShowNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通知点击意图
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                // 可以理解为延时执行的 Intent
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                // 构建通知
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        // 标题
                        .setContentTitle(getResources().getString(R.string.noti_title))
                        // 常规文本
                        .setContentText(getResources().getString(R.string.long_text))
                        // 送达时间
                        .setWhen(System.currentTimeMillis())
                        // 状态栏小图标
                        .setSmallIcon(R.mipmap.ic_launcher)
                        // 通知中心大图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        // 设置点击意图
                        .setContentIntent(pi)
                        // 设置点击后自动消失
                        .setAutoCancel(true)

                        // 提示音
                        //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Orion.ogg")))
                        // 震动
                        .setVibrate(new long[] {0, 200, 100, 300})
                        // LED 灯闪烁
                        //.setLights(Color.RED, 1000, 1000)

                        // 全部自动设置
                        // .setDefaults(NotificationCompat.DEFAULT_ALL)

                        // 显示长文字
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText(getResources().getString(R.string.long_text)))
                        // 显示图片
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.drawable.mentalomega_sides_2016)))

                        // 设置优先级
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(notiId, notification);
                notiId += 1;
            }
        });
    }
}
