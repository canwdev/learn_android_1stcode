package com.example.fragmentbestpractice;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 启动画面 Splash
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 500; //延迟0.5秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

}
