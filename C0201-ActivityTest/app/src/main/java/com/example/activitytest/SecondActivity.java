package com.example.activitytest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.d("SecondActivity", "onActivityResult: "+ returnedData);
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // TaskID 返回栈ID
        Log.d("SecondActivity", "TaskID: "+getTaskId());

        // 接收FirstActivity传来的数据
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        Log.d("SecondActivity" , "onCreate: "+data);

        Button button2 = (Button) findViewById(R.id.button2);
        Button buttonGo2 = (Button) findViewById(R.id.button_go2);
        Button buttonGo3 = (Button) findViewById(R.id.button_go3);
        Button buttonRe = (Button) findViewById(R.id.button_re);
        Button buttonWww = (Button) findViewById(R.id.button_www);
        Button buttonCall = (Button) findViewById(R.id.button_call);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动隐式Activity
                Intent intent = new Intent("com.example.activitytest.V2_ACTION_START");

                // 可以指定多个Category
                /* Sintent.addCategory("com.example.activitytest.MY_CATEGORY"); */

                //- startActivity(intent);

                // Activity返回数据给上一个活动（即本活动）
                startActivityForResult(intent, 1);
            }
        });

        buttonGo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动隐式Activity
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        buttonRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        buttonWww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用浏览器
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/"));
                startActivity(intent);
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用拨号器
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:10086"));
                if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SecondActivity.this, "请在设置中开启电话权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
            }
        });
    }
}
