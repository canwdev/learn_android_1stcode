package com.example.sharedpreferencetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    EditText ageText;
    CheckBox singleCheckBox;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText) findViewById(R.id.editText_name);
        ageText = (EditText) findViewById(R.id.editText_age);
        singleCheckBox = (CheckBox) findViewById(R.id.checkBox_single);
        button = (Button) findViewById(R.id.save_data);

        loadPreference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取一个SharedPreference.Editor对象
                // 存于 /data/data/com.example.sharedpreferencetest/shared_prefs/data.xml
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                // 往对象里put值
                editor.putString("name", nameText.getText().toString());
                if (!"".equals(ageText.getText().toString())) {
                    editor.putInt("age", Integer.parseInt(ageText.getText().toString()));
                } else {
                    editor.putInt("age", 0);
                }
                editor.putBoolean("single", singleCheckBox.isChecked());
                // 数据提交
                editor.apply();
            }
        });
    }

    public void loadPreference() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String name = pref.getString("name", "");
        nameText.setText(name);
        int age = pref.getInt("age", 0);
        ageText.setText(String.valueOf(age));
        Boolean isSingle = pref.getBoolean("single", false);
        singleCheckBox.setChecked(isSingle);

    }

    public void goSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
