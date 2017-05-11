package com.example.activity_demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class browser extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//Òþ²Ø²Ù×÷À¸£¨±êÌâÀ¸£©
		setContentView(R.layout.browser);
		
	}
	
	public void onFinishClick(View view) {
		finish();
	}
}
