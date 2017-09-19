package com.example.notificationtest;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 消除一条通知
        // manager.cancel(notiId);
        // 消除全部自己的通知
        // manager.cancelAll();
    }
}
