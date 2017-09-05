package com.example.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivityV2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_v2);
    }

    public void finishClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello SecondActivity from v2.");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello SecondActivity from v2.");
        setResult(RESULT_OK, intent);
        finish();
    }
}
