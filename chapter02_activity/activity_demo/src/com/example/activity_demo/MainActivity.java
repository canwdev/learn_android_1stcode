package com.example.activity_demo;

import java.security.PublicKey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);	//���ز���������������
		setContentView(R.layout.activity_main);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(MainActivity.this, "����˰�ť", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(MainActivity.this, activity2.class);	//��ʽ����
				String data = "Hello���ڶ����~\n�������Ե�һ���";
				intent.putExtra("extra_data", data);
				startActivity(intent);
			}
		});
		
		
	}
	
	public void onGo3Click(View view) {
		Intent intent = new Intent("com.example.activity_demo.ACTION_START3");	//��ʽ����
		//startActivity(intent);
		startActivityForResult(intent, 1);
	}
	
	public void onBrowseClick(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://bing.com"));
		startActivity(intent);
	}
	
	public void onCallClick(View view) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:10086"));
		startActivity(intent);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					String ret = data.getStringExtra("data_return");
					Toast.makeText(MainActivity.this, "Activity3 �ɹ�����ֵ: "+ret, Toast.LENGTH_SHORT).show();
				}
				break;
		}
	};
	
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
			Toast.makeText(MainActivity.this, "�����action_settings", Toast.LENGTH_SHORT).show();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
