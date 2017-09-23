package com.example.advancedskill;

import android.app.Application;
import android.content.Context;

/**
 * 全局获取Context的技巧
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // 如何初始化 LitePal
        // LitePalApplication.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
