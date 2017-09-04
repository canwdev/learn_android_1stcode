package com.example.activity_start_mode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

//�ڻ�������У�����ͨ��һ�� List ���ݴ���Ȼ���ṩ��һ�� addActivity()������
//���� List �����һ������ṩ��һ�� removeActivity()�������ڴ� List ���Ƴ�������
//�ṩ��һ�� finishAll()�������ڽ� List �д洢�Ļȫ�������ٵ���
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
