package com.example.wallpapertool;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView wallpaperView;
    ProgressDialog mDialog;

    // 获取系统当前壁纸
    private Bitmap getSystemWallpaper() {
        // 壁纸管理器
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        // 将Drawable,转成Bitmap，返回Bitmap
        return ((BitmapDrawable) wallpaperDrawable).getBitmap();
    }

    // suffix == ".png" || ".jpg"
    public void saveWallpaper(final String suffix) {

        // 等待框，UI。
        mDialog = ProgressDialog.show(
                MainActivity.this, "正在保存" + suffix + "格式图片",
                "Saving wallpaper，请稍等...", true, false);

        // 开线程运行耗时的逻辑
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // 在子线程中更新UI，防止崩溃
                Looper.prepare();

                String savePath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
                //String picName = getSystemWallpaper().getConfig().toString() + suffix;

                // 将时间设为文件名
                SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String FileTime = timesdf.format(new Date()).toString();//获取系统时间
                String filename = FileTime.replace(":", "").replace("-", "").replace(" ", "");
                String picName = "Wallpaper_" + filename + suffix;

                // 保存壁纸
                try {
                    final File f = new File(savePath, picName);
                    if (f.exists()) {
                        Snackbar.make(wallpaperView, "文件已存在", Snackbar.LENGTH_SHORT)
                                .setAction("Delete!", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        f.delete();
                                    }
                                }).show();
                    } else {
                        FileOutputStream out = new FileOutputStream(f);
                        if (".jpg".equals(suffix)) {
                            getSystemWallpaper().compress(Bitmap.CompressFormat.JPEG, 90, out);
                        } else if (".png".equals(suffix)) {
                            getSystemWallpaper().compress(Bitmap.CompressFormat.PNG, 90, out);
                        }
                        out.flush();
                        out.close();

                        Snackbar sb = Snackbar.make(wallpaperView, "已保存: "
                                + savePath + picName, Snackbar.LENGTH_LONG);
                        sb.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Snackbar.make(wallpaperView, "错误：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Snackbar.make(wallpaperView
                            , "错误：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

                // 更新UI
                mDialog.dismiss();
                Looper.loop();
            }
        });
        t.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wallpaperView = (ImageView) findViewById(R.id.image_wallpaper);
        wallpaperView.setImageBitmap(getSystemWallpaper());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // 设置浮动按钮点击动作（存为.jpg）
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 检查权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 弹出系统权限授予
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    // 权限允许后的逻辑
                    saveWallpaper(".jpg");
                }

            }
        });

        // 设置浮动按钮长按动作（存为.png）
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // 检查权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 弹出系统权限授予
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    // 权限允许后的逻辑
                    saveWallpaper(".png");
                }

                return true;
            }
        });
    }

    // 权限检查回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveWallpaper(".jpg");
                } else {
                    Toast.makeText(this, "Permission denied: \n" + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveWallpaper(".png");
                } else {
                    Toast.makeText(this, "Permission denied: \n" + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            // 重新加载系统壁纸
            wallpaperView.setImageBitmap(getSystemWallpaper());
            Snackbar.make(wallpaperView, getResources().getString(R.string.action_refresh) + " OK", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 防止报错
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
