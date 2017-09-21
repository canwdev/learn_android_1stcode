package com.example.threadtest;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonShow = (Button) findViewById(R.id.button_show);
        final ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 在子线程中更新UI，防止崩溃
                        Looper.prepare();

                        for (int i = 1; i <= 100; i++) {
                            progressBar1.setProgress(i);
                            buttonShow.setText(String.valueOf(i)+"%");
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
        });
    }
}
