package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        replaceFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                replaceFragment(new RightFragment2());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        // 获取碎片管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启碎片Transaction事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 设置FragmentLayout的响应碎片对象
        transaction.replace(R.id.right_fragment_layout, fragment);
        // 模拟返回栈，这样点击返回键即可返回上一个碎片
        transaction.addToBackStack(null);
        // 提交事务
        transaction.commit();
    }
}
