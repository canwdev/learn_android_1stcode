package com.example.uiwidgettest;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private EditText editText1;
    private ImageView imageView1;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);
        editText1 = (EditText) findViewById(R.id.editText1);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setImageResource(R.drawable.img1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);


//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 在此处添加逻辑
//                Toast.makeText(MainActivity.this, "Method 1", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_go_p:
                Intent intent = new Intent(MainActivity.this, PercentFrameLayoutActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // 获取InputText文本
                String inputText = editText1.getText().toString();
                textView.setText(inputText);

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                // 获取当前壁纸
                Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                // 将Drawable,转成Bitmap
                Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
                imageView1.setImageBitmap(bm);


                break;
            case R.id.button2:
                imageView1.setImageResource(R.drawable.img1);
                // 设置进度圈可见性
                if (progressBar.getVisibility() == View.INVISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                // 设置进度条进度
                int progress = progressBar2.getProgress();
                if (progress < 100) {
                    progress = progress + 10;
                    progressBar2.setProgress(progress);
                } else {
                    progressBar2.setProgress(0);
                    // 新建弹出对话框
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle(android.R.string.dialog_alert_title);
                    dialog.setMessage(this.getTaskId()+" ProgressBar is Full!");
                    // 不可以被取消的属性
                    dialog.setCancelable(false);
                    dialog.setPositiveButton(android.R.string.ok , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.setNegativeButton(android.R.string.cancel , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setTitle("Now @"+ android.R.string.cancel);
                            progressDialog.setMessage("Wait for Charge...");
                            progressDialog.show();
                            progressBar2.setProgress(100);
                        }
                    });
                    dialog.show();
                }

                break;
            default:
                break;
        }
    }
}
