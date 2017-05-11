package com.example.myactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoggedActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged);
		
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");
		
		TextView textView = (TextView) findViewById(R.id.txw_logged_info);
		textView.setText("»¶Ó­µÇÂ½£º" + username);
	}
	
	public void onLogoffClick(View view) {
		finish();
	}
}
