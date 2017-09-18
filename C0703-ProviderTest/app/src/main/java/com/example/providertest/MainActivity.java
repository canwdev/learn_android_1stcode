package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 添加数据
    public void onAddClick(View view) {
        try {
            Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
            ContentValues values = new ContentValues();
            values.put("name", "A Clash of Kings");
            values.put("author", "George Martin");
            values.put("pages", "1040");
            values.put("price", 22.85);
            Uri newUri = getContentResolver().insert(uri, values);
            newId = newUri.getPathSegments().get(1);

            Toast.makeText(this, "INSERT COMPLETE. "+newUri, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 查询数据
    public void onQueryClick(View view) {
        Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
        Cursor cursor = getContentResolver().query(uri, null, null, null,null);
        String allData = "";
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String oneLineData =
                        cursor.getString(cursor.getColumnIndex("id")) + "\t" +
                                cursor.getString(cursor.getColumnIndex("name")) + "\t" +
                                cursor.getString(cursor.getColumnIndex("author")) + "\t" +
                                cursor.getString(cursor.getColumnIndex("pages")) + "\t" +
                                cursor.getString(cursor.getColumnIndex("price")) + "\n";
                allData += oneLineData;
            }
            cursor.close();
        }

        Toast.makeText(this, allData, Toast.LENGTH_SHORT).show();
    }

    // 更新数据
    public void onUpdateClick(View view) {
        try {
            Uri uri = Uri.parse("content://com.example.databasetest.provider/book/"+newId);
            ContentValues values = new ContentValues();
            values.put("price", 10.9999);
            getContentResolver().update(uri, values, null, null);

            Toast.makeText(this, "UPDATE COMPLETE. "+uri, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 删除数据
    public void onDeleteClick(View view) {
        try {
            Uri uri = Uri.parse("content://com.example.databasetest.provider/book/"+newId);
            getContentResolver().delete(uri, null, null);

            Toast.makeText(this, "DELETE COMPLETE. "+uri, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
