package com.example.databasetest;

import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 四个参数：上下文对象、数据库名、游标、版本号(1-->2)
        dbHelper = new MyDatabaseHelper(this, "Book.db", null, 2);
    }

    public void onCreateDbClick(View view) {
        // 创建或打开数据库（如果没数据库文件，才会创建）
        dbHelper.getWritableDatabase();

        Toast.makeText(this, "/data/data/" + this.getPackageName() + "/databases/Book.db", Toast.LENGTH_SHORT).show();
    }

    public void onDeleteDbClick(View view) {
        // 删除数据库
        deleteDatabase("Book.db");

        Toast.makeText(this, "Delete database Book.db", Toast.LENGTH_SHORT).show();
    }

    // 添加数据
    public void onAddClick(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // 开始组装第一条(一列)数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values);
        values.clear();
        // 组装另一条数据
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        db.insert("Book", null, values);

        /*// SQL 语句
        db.execSQL("insert into Book (name, author, pages, price) values(?,?,?,?)"
                , new String[]{"The Da Vinci Code", "Dan Brown", "454", "16.96"});
        db.execSQL("insert into Book (name, author, pages, price) values(?,?,?,?)"
                , new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});*/


        Toast.makeText(this, "INSERT Complete", Toast.LENGTH_SHORT).show();
    }

    // 更新数据
    public void onUpdateClick(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 10.9999);
        db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});

        /*// SQL 语句
        db.execSQL("update Book set price=? where name=?"
                , new String[]{"10.8888", "The Da Vinci Code"});*/

        Toast.makeText(this, "UPDATE Complete", Toast.LENGTH_SHORT).show();
    }

    // 删除数据
    public void onDeleteClick(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book", "pages > ?", new String[]{"500"});

        /*// SQL 语句
        db.execSQL("delete from Book where pages>?", new String[]{"500"});*/

        Toast.makeText(this, "DELETE Complete", Toast.LENGTH_SHORT).show();
    }

    // 查询数据
    public void onQueryClick(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 查询Book表的所有数据
        String allData = "";
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            // 遍历Cursor对象
            do {
                String oneLineData =
                        cursor.getString(cursor.getColumnIndex("id")) + " " +
                                cursor.getString(cursor.getColumnIndex("name")) + " " +
                                cursor.getString(cursor.getColumnIndex("author")) + " " +
                                cursor.getString(cursor.getColumnIndex("pages")) + " " +
                                cursor.getString(cursor.getColumnIndex("price")) + "\n";
                allData += oneLineData;
            } while (cursor.moveToNext());
        }
        cursor.close();

        /*// SQL 语句
        db.rawQuery("select * from Book", null).toString();*/

        Toast.makeText(this, allData, Toast.LENGTH_SHORT).show();
    }
}
