package com.example.fragmentbestpractice;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //再按一次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.main_activity_layout);
            Snackbar.make(layout , "再按一次退出程序"
                    , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //System.exit(0);
        }
    }
}
