package com.example.activitytest;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

//随时随地优雅的退出，需要结合BaseActivity使用
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        // 再次确保关闭
        Process.killProcess(Process.myPid());
    }
}
