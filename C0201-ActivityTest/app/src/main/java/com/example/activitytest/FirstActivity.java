package com.example.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 给当前活动加载布局
        setContentView(R.layout.activity_first);

        // Activity ID
        Log.d("FirstActivity", this.toString());

        // TaskID 返回栈ID
        Log.d("FirstActivity", "TaskID: "+getTaskId());

        // 获取按钮
        Button button1 = (Button) findViewById(R.id.button1);
        Button buttonGo = (Button) findViewById(R.id.button_go);
        Button buttonRe = (Button) findViewById(R.id.button_re);

        // 设置按钮点击监听器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出Toast提示
                Toast.makeText(FirstActivity.this, "You Clicked Button1!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // 设置按钮长按监听器
        button1.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(FirstActivity.this, "You Long Clicked Button1",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 启动显式Activity
                /* Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent); */

                // 启动显式Activity，并向下一个活动传递数据
                String data = "Hello SecondActivity!";
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });

        buttonRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
    }

    // 添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 设置菜单点击响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You Clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You Clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_menu:
                // 销毁一个活动
                finish();
                break;
            default:
        }
        return true;
    }
}
