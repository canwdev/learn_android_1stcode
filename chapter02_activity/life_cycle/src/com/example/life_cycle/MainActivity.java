package com.example.life_cycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivityLog";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState != null) {
			String tempData = savedInstanceState.getString("data_key");
			Log.w(TAG, tempData);
		}
	}

	public void on2norClick(View view) {
		Intent intent = new Intent(MainActivity.this, NormalActivity.class);
		startActivity(intent);
	}

	public void on2diaClick(View view) {
		Intent intent = new Intent(MainActivity.this, DialogActivity.class);
		startActivity(intent);
	}
	
	@Override		//活动在被迫被系统销毁前执行以保存临时数据
	protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	String tempData = "Something you just typed，保存的临时数据";
	outState.putString("data_key", tempData);
	Log.w(TAG, "onSaveInstanceState");
	}
	
	@Override
	protected void onStart() {
	super.onStart();
	
	}
	@Override
	protected void onResume() {
	super.onResume();
	Log.d(TAG, "onResume");
	}
	@Override
	protected void onPause() {
	super.onPause();
	Log.d(TAG, "onPause");
	}
	@Override
	protected void onStop() {
	super.onStop();
	Log.d(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
	super.onDestroy();
	Log.d(TAG, "onDestroy");
	}
	@Override
	protected void onRestart() {
	super.onRestart();
	Log.d(TAG, "onRestart");
	}
}
