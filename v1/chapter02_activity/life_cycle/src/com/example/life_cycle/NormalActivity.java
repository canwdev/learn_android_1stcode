package com.example.life_cycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NormalActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//Òþ²Ø²Ù×÷À¸£¨±êÌâÀ¸£©
		setContentView(R.layout.normal_activity);
	}
}
