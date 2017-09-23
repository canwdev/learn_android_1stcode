package com.example.advancedskill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TestObject testObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testObject = new TestObject();
        testObject.tShort("Toast from MyApplication.getContext()!");

        testObject.setInteger(66666);
        testObject.setString("Hello");

        Button button2 = (Button) findViewById(R.id.button2);
        // Java8 的 Lambda 表达式，需在 app/build.gradle 配置。
        button2.setOnClickListener(v-> {
            TestObject.tShort("Java8 Lambda");
        });
    }

    public void onButton(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("objData", testObject);
        startActivity(intent);
    }
}
