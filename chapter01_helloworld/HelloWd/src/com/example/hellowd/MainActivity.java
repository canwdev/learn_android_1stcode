package com.example.hellowd;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int flag = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.d("HelloWd", "创建了活动！");
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onSwitchStatBarClick(View view) {
    	ImageView imageview = (ImageView) findViewById(R.id.imageView1);
    	if (flag == 0) {
    		imageview.setVisibility(View.VISIBLE);
        	flag = 1;
    	} else {
    		imageview.setVisibility(View.INVISIBLE);
    		
        	flag = 0;
    	}
    	
    	Log.d("HelloWd", "点击按钮！！");
    }
    
    public void onButtonSAClicked(View view) {
    	Intent intent = new Intent(MainActivity.this, SecondActivity.class);
    	startActivity(intent);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_settings: {
    		Toast.makeText(this, "Bye!",Toast.LENGTH_SHORT).show();
    		finish();
    	}
    		
    		break;
    	}
    	return true;
    }
    
    public void onBtnCallClicked(View view) {
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse("tel:10001"));
    	startActivity(intent);
    }
    
    public void onBtnWebClicked(View view) {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse("http://www.google.com"));
    	startActivity(intent);
    }
}
