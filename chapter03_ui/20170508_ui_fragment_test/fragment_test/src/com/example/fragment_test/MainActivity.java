package com.example.fragment_test;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements android.view.View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
        
        RightFragment rFragment = new RightFragment();
		FragmentManager fManager = getFragmentManager();
		FragmentTransaction fTransaction = fManager.beginTransaction();
		fTransaction.replace(R.id.right_layout, rFragment);
		fTransaction.commit();
    }

    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
		case R.id.button1:
			AnotherRightFragment aRightFragment = new AnotherRightFragment();
			FragmentManager fManager = getFragmentManager();
			FragmentTransaction fTransaction = fManager.beginTransaction();
			fTransaction.replace(R.id.right_layout, aRightFragment);
			fTransaction.addToBackStack(null); // ∑µªÿ’ª
			fTransaction.commit();
			break;

		default:
			break;
		}
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
