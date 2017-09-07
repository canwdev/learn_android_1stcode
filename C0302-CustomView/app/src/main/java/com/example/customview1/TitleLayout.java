package com.example.customview1;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// 因为自定义控件TitleLayout类继承的是LinearLayout，因此不能在AndroidManifest.xml注册。
// 但需要在activity_main.xml写明。
public class TitleLayout extends LinearLayout{

    // 构造函数，获取当前活动context，类似于onCreate。
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.ios_title_bar, this);
		
		Button btn_back = (Button) findViewById(R.id.btn_back);
		Button btn_edit = (Button) findViewById(R.id.btn_edit);


		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                // 销毁所在活动
				((Activity) getContext()).finish();
			}
		});

		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getContext(), "如何在TitleLayout（自定义View）中获取activity_main.xml的组件？ ",Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
}
