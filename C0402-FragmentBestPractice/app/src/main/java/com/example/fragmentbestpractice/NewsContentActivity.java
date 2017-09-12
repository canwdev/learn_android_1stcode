package com.example.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 用于单页模式启动的新闻内容活动
public class NewsContentActivity extends AppCompatActivity {

    // 2.6.3 启动活动的最佳写法
    public static void ActionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_activity);

        // 接收Intent数据
        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");

        NewsContentFragment fragment = (NewsContentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.news_content_fragment1);
        // 活动调用碎片方法，刷新内容
        fragment.refresh(newsTitle, newsContent);
    }
}
