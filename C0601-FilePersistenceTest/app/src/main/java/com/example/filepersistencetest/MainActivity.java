package com.example.filepersistencetest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button saveButton;
    Button loadButton;
    private final String SAVEPATH = Environment.getExternalStorageDirectory().getPath() + "/Download/";
    private final String FILENAME = "data";


    @Override
    protected void onDestroy() {
        super.onDestroy();

        String string = editText.getText().toString();
        // 在此时保存文件
        save(string);
    }

    public void save(String string) {
        // 文件输出流
        FileOutputStream out = null;
        // 缓冲器
        BufferedWriter writer = null;
        try {
            // 文件名“data”，输出模式“覆盖”
            out = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            // 写文件，将保存至：/data/data/com.example.filepersistencetest/files
            // 因此不需要读写外存储器权限
            writer.write(string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String load() {
        // 文件输入流
        FileInputStream in = null;
        // Reader缓冲器
        BufferedReader reader = null;
        // 字符串构建器
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 文件名“data”
            in = openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            // 读取每一行，追加到构建器
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        String text = load();
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
            // 将光标移到行尾（7.0+自动？）
            editText.setSelection(text.length());
        }

        saveButton = (Button) findViewById(R.id.button_save_os);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOSF(editText.getText().toString());
            }
        });

        loadButton = (Button) findViewById(R.id.button_load_os);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = loadOSF();
                if (!TextUtils.isEmpty(text)) {
                    editText.setText(text);
                }
            }
        });

    }

    // "OutStorageFile"
    public void saveOSF(String string) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int PERMISSION_REQUEST_CODE = 10;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // 当没有获得权限时
                // 弹出系统权限提示框
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
                Toast.makeText(this, "缺失权限：" + permission, Toast.LENGTH_SHORT).show();
            } else {
                // 否则已允许
            }
        }

        try {
            // 文件对象
            final File file = new File(SAVEPATH, FILENAME);

            if (file.exists()) {
                Toast.makeText(this, "文件已存在，取消写入: " + SAVEPATH + FILENAME, Toast.LENGTH_SHORT).show();
            } else {
                FileOutputStream out = new FileOutputStream(file);
                byte[] bs = string.getBytes();
                out.write(bs);
                out.flush();
                out.close();
                Toast.makeText(this, "OK: " + SAVEPATH + FILENAME, Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadOSF() {
        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        int PERMISSION_REQUEST_CODE = 10;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // 当没有获得权限时
                // 弹出系统权限提示框
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
                Toast.makeText(this, "缺失权限：" + permission, Toast.LENGTH_SHORT).show();
            } else {
                // 否则已允许
            }
        }

        File file = null;
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            file = new File(SAVEPATH + FILENAME);
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            // 读取每一行，追加到构建器
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            Toast.makeText(this, "读取：" + SAVEPATH + FILENAME, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

}
