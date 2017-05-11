package com.example.activity_start_mode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

//在活动管理器中，我们通过一个 List 来暂存活动，然后提供了一个 addActivity()方法用
//于向 List 中添加一个活动，提供了一个 removeActivity()方法用于从 List 中移除活动，最后
//提供了一个 finishAll()方法用于将 List 中存储的活动全部都销毁掉。
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();
	
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
	}
}
