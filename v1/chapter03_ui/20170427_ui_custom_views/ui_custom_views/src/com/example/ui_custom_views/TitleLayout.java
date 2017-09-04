package com.example.ui_custom_views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleLayout extends LinearLayout{

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.ios_title_bar, this);
		
		Button btn_back = (Button) findViewById(R.id.btn_back);
		Button btn_edit = (Button) findViewById(R.id.btn_edit);
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((Activity) getContext()).finish();
			}
		});

		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getContext(), "You clicked Edit button",Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
}
