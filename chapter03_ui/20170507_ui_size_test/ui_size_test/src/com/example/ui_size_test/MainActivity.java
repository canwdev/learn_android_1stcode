package com.example.ui_size_test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		float xdpi = getResources().getDisplayMetrics().xdpi;
		float ydpi = getResources().getDisplayMetrics().ydpi;
		
		Log.d("MainActivity", "xdpi: " + xdpi);
		Log.d("MainActivity", "ydpi: " + ydpi);
		
		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("xdpi: " + xdpi + "\n" + "ydpi: " + ydpi);
	}
}
