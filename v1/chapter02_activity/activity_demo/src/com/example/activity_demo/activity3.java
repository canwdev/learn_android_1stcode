package com.example.activity_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class activity3 extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//Òþ²Ø²Ù×÷À¸£¨±êÌâÀ¸£©
		setContentView(R.layout.activity3);
	}
	
	public void onFinishClick(View view) {
		Intent intent = new Intent();
		intent.putExtra("data_return", "Hello, first activity!");
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("data_return", "Hello, first activity!");
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}
}
