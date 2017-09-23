package com.example.advancedskill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TestObject object = (TestObject) getIntent().getSerializableExtra("objData");

        object.tShort("Transfer Complete: "+object.getInteger()+object.getString());
        LogUtil.d("SA!!", object.getString());
    }
}
