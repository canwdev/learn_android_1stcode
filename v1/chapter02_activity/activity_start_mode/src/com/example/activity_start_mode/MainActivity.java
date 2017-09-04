package com.example.activity_start_mode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", this.toString());
		Toast.makeText(this, "onCreate: " + getClass().getSimpleName() + ", TaskID: " + getTaskId(), Toast.LENGTH_SHORT).show();
		
		ActivityCollector.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
		Toast.makeText(this, "onDestroy: " + getClass().getSimpleName() + ", TaskID: " + getTaskId(), Toast.LENGTH_SHORT).show();
	}
	
	public void onKai2Click(View view) {
		Intent intent = new Intent(this, Activity2.class);
		startActivity(intent);
	}
	
	public void onKaiClick(View view) {
		Toast.makeText(this, getClass().getSimpleName() + ", TaskID: " + getTaskId(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);

	}
	
	public void onFinishClick(View view) {
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
