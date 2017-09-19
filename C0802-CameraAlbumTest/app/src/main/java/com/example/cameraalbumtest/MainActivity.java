package com.example.cameraalbumtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CAT_DEBUG";
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        picture = (ImageView) findViewById(R.id.imageView_pic);

        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个文件对象，并放在缓存目录（/sdcard/Android/<com.example.cameraalbumtest>/cache）
                File outputImage = new File(getExternalCacheDir(), "output_photo.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Android 7.0+ 需要使用FileProvider
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MainActivity.this,
                            "com.example.cameraalbumtest.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                // 启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检查权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    private void openAlbum() {
        // 调用相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "Permission denied: \n" + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 获取返回图像并设置
                        Bitmap bmp = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bmp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        picture.setImageResource(R.drawable.fail);
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        // Android 4.4 以上版本
                        handleImageAfterApi19(data);
                    } else {
                        // 4.4 以下版本
                        handleImageBeforeApi19(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imgPath = getPathFromContent(uri, null);
        displayImage(imgPath);
    }

    @TargetApi(19)
    private void handleImageAfterApi19(Intent data) {
        String imgPath = null;
        Uri uri = data.getData();
        Log.d(TAG, "handleImageAfterApi19: uri="+uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是“文档”类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "handleImageAfterApi19: docId="+docId);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                Log.d(TAG, "handleImageAfterApi19: id="+id);
                String selection = MediaStore.Images.Media._ID + "=" + id;
                Log.d(TAG, "handleImageAfterApi19: selection="+selection);
                imgPath = getPathFromContent(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.d(TAG, "handleImageAfterApi19: imgPath="+imgPath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                // TODO：此处闪退
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                Log.d(TAG, "handleImageAfterApi19: contentUri"+contentUri);
                imgPath = getPathFromContent(contentUri, null);
                Log.d(TAG, "handleImageAfterApi19: imgPath"+imgPath);
            }
            Toast.makeText(this, "以文档模式打开", Toast.LENGTH_SHORT).show();

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是内容提供器类型的uri，则使用普通方式处理
            imgPath = getPathFromContent(uri, null);
            Toast.makeText(this, "以内容提供器模式打开", Toast.LENGTH_SHORT).show();

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是文件类型的uri，则直接获取路径
            imgPath = uri.getPath();
            Toast.makeText(this, "以文件模式打开", Toast.LENGTH_SHORT).show();

        }
        displayImage(imgPath);
    }

    private String getPathFromContent(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imgPath) {
        if (imgPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            picture.setImageBitmap(bitmap);
        } else {
            picture.setImageResource(R.drawable.fail);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
