package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

// SQLite帮助类 SQLiteOpenHelper 是一个抽象函数
public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 创建表“Book”的字符串
    public static final String CREATE_BOOK = "create table Book ("
            // 列名“id” 整型 主 键 自增
            + "id integer primary key autoincrement, "
            // text=文本类型
            + "author text, "
            // real=浮点类型
            + "price real, "
            + "pages integer, "
            + "name text)";

    // 创建表：Category 的字符串
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    // 如果没数据库文件，才会创建，如果有，则需要用onUpgrade更新
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, CREATE_BOOK+"\n\n"+CREATE_CATEGORY, Toast.LENGTH_SHORT).show();
    }

    // 当 MyDatabaseHelper的版本号大于上一个版本时执行
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // 简单粗暴的更新
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
