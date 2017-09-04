package com.example.activity_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class activity2 extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//Òþ²Ø²Ù×÷À¸£¨±êÌâÀ¸£©
		setContentView(R.layout.activity2);
		
		Intent intent = getIntent();
		String data = intent.getStringExtra("extra_data");
		TextView textView = (TextView) findViewById(R.id.textView2);
		textView.setText(data);
	}
	
	public void onFinishClick(View view) {
		finish();
	}
}
