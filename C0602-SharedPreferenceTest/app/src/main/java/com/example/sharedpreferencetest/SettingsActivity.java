package com.example.sharedpreferencetest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Switch switch1;
    Switch switch2;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);

        // 注意与getSharedPreferences的区别，
        // getDefaultSharedPreferences不需要设置文件名，
        // 存为com.example.sharedpreferencetest_preferences.xml。
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        loadSettings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSettings();
    }

    private void saveSettings() {
        editor = pref.edit();
        editor.putBoolean("switch1", switch1.isChecked());
        editor.putBoolean("switch2", switch2.isChecked());
        // editor.clear();
        editor.apply();
    }

    private void loadSettings() {
        switch1.setChecked(pref.getBoolean("switch1", false));
        switch2.setChecked(pref.getBoolean("switch2", false));
    }
}
