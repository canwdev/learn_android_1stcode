package com.example.xmljsontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // 10.0.2.2 表示Android模拟器的主机地址 127.0.0.1
    public static final String URL_XML = "http://10.0.2.2/get_data.xml";
    public static final String URL_JSON = "http://10.0.2.2/get_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonXML = (Button) findViewById(R.id.button_XML);
        Button buttonJSON = (Button) findViewById(R.id.button_JSON);

        buttonXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, XmlTestActivity.class);
                startActivity(intent);
            }
        });

        buttonJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JsonTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
