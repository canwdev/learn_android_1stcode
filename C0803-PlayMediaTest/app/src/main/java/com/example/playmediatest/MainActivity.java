package com.example.playmediatest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MUSIC!";

    private String musicPath = null;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private TextView textMusicTitle;
    private TextView durTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMusicTitle = (TextView) findViewById(R.id.textView_musicTitle);
        durTime = (TextView) findViewById(R.id.textView_durTime);
        Button play = (Button) findViewById(R.id.button_play);
        Button pause = (Button) findViewById(R.id.button_pause);
        Button stop = (Button) findViewById(R.id.button_stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.button_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.button_stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    initMusicPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_openFile) {
            // 检查权限
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openMusic();
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openMusic();
                } else {
                    Toast.makeText(this, "Permission denied: \n" + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void openMusic() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("audio/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        // Android 4.4 以上版本
                        try {
                            handleFileAfterApi19(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, e.getMessage() + "\n出现问题，请使用文件管理器打开文件", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // 4.4 以下版本
                        handleFileBeforeApi19(data);
                    }
                    Uri uri = data.getData();
                    initMusicPlayer();
                }
                break;
            default:
                break;
        }
    }

    private void initMusicPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(musicPath);
            mediaPlayer.prepare();
            Log.d(TAG, "initMusicPlayer: "+mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textMusicTitle.setText(musicPath);
    }

    private void handleFileBeforeApi19(Intent data) {
        Uri uri = data.getData();
        musicPath = getPathFromContent(uri, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @TargetApi(19)
    private void handleFileAfterApi19(Intent data) {
        Uri uri = data.getData();
        Log.d(TAG, "handleFileAfterApi19: uri.getPath()=" + uri.getPath());

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是“文档”类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "handleFileAfterApi19: docId=" + docId);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                Log.d(TAG, "handleFileAfterApi19: id=" + id);
                String selection = MediaStore.Audio.Media._ID + "=" + id;
                Log.d(TAG, "handleFileAfterApi19: selection=" + selection);
                musicPath = getPathFromContent(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                // TODO: 此处闪退
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                Log.d(TAG, "handleFileAfterApi19: contentUri=" + contentUri);
                musicPath = getPathFromContent(contentUri, null);
                Log.d(TAG, "handleFileAfterApi19: contentUri=" + musicPath);
            } else if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                // TODO: 非正式解决方法（隐患）
                String path = uri.getPath();
                path = path.replace("/document/primary:", "/sdcard/");
                Log.d(TAG, "handleFileAfterApi19: path=" + path);
                musicPath = path;
            }
            //Toast.makeText(this, "以文档模式打开", Toast.LENGTH_SHORT).show();

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是内容提供器类型的uri，则使用普通方式处理
            musicPath = getPathFromContent(uri, null);
            //Toast.makeText(this, "以内容提供器模式打开", Toast.LENGTH_SHORT).show();

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是文件类型的uri，则直接获取路径
            musicPath = uri.getPath();
            //Toast.makeText(this, "以文件模式打开", Toast.LENGTH_SHORT).show();

        }
    }

    private String getPathFromContent(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
