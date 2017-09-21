package com.example.threadtest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button buttonShow;
    ProgressBar progressBar1;
    public static final int UPDATE_UI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonShow = (Button) findViewById(R.id.button_show);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMethod2();
            }
        });
    }

    private void testMethod1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 使用Looper在子线程中更新UI，防止崩溃
                Looper.prepare();

                for (int i = 1; i <= 100; i++) {
                    progressBar1.setProgress(i);
                    // 经测试以下语句在4.4中造成崩溃
                    // buttonShow.setText(String.valueOf(i) + "%");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(MainActivity.this, "Full!", Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        }).start();
    }

    private void testMethod2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {

                    Message message = new Message();
                    message.what = UPDATE_UI;
                    // 用 Bundle 传给 handler 数据
                    Bundle data = new Bundle();
                    data.putInt("percent", i);
                    message.setData(data);
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // 使用 Android 提供的异步消息处理机制来更新 UI
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    // 在这里可以进行UI操作
                    Bundle data = msg.getData();
                    int i = data.getInt("percent");
                    buttonShow.setText(String.valueOf(i) + "%");
                    progressBar1.setProgress(i);
                    break;
                default:
                    break;
            }
        }

    };
}
