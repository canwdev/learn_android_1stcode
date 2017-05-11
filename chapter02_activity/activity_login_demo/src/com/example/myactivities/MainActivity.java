package com.example.myactivities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn_loggin = (Button)findViewById(R.id.btn_login);
		btn_loggin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				EditText username = (EditText) findViewById(R.id.editText1);
				EditText password = (EditText) findViewById(R.id.editText2);
				actionStart(MainActivity.this, username.getText().toString(), password.getText().toString());
			}
		});
		
	}
	
	public void actionStart(Context context, String username, String password) {
		Intent intent = new Intent(context, LoggedActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		context.startActivity(intent);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(MainActivity.this, "click! Menu item settings", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		return true;
	}
}
