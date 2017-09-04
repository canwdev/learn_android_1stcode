package com.example.ui_demo;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button button1;
	private Button button2;
	private EditText edittext;
	private ImageView imageView;
	private ProgressBar progressBar1;
	private ProgressBar progressBar2;
	
	private boolean switch_flag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		edittext = (EditText) findViewById(R.id.editText1);
		imageView = (ImageView) findViewById(R.id.imageView1);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);	
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:
			edittext.setText(R.string.hello_world);
			
			int progress = progressBar1.getProgress();
			if(progress < 100){
				progress += 10;
				progressBar1.setProgress(progress);
			}else {
				confirmDialog("Full", "Power 100%£¡");
				progress = 0;
				progressBar1.setProgress(progress);
			}
			
			if (progressBar2.getVisibility() == View.GONE) {
				progressBar2.setVisibility(View.VISIBLE);
			} else {
				progressBar2.setVisibility(View.GONE);
			}
			
			break;
		case R.id.button2:
			
			if (switch_flag) {
				progressDialog("Loading", edittext.getText().toString());
				//Toast.makeText(this, edittext.getText().toString(), Toast.LENGTH_SHORT).show();
				
				imageView.setImageResource(R.raw.test);
				switch_flag = false;
			} else {
				imageView.setImageResource(R.drawable.ic_launcher);
				switch_flag = true;
			}
			break;
		default:
			break;
		}
	}
	
	boolean flag = false;
	public boolean confirmDialog(String title, String message) {
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogInterface.
				OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				flag = true;
			}
			});
		/*dialog.setNegativeButton("Cancel", new DialogInterface.
				OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				flag = false;
			}
			});*/
		dialog.show();
		return flag;
	}
	
	public void progressDialog(String title, String message) {
		
		
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setCancelable(true);
		dialog.show();
	}
}
