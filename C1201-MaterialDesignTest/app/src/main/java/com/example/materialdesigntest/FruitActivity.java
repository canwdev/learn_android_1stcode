package com.example.materialdesigntest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "FRUIT_NAME";
    public static final String FRUIT_IMAGE_ID = "FRUIT_IMAGE_ID";
    int fruitImageId;
    ImageView fruitImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);


        // 接收信息
        Intent intent = getIntent();
        String frutiName = intent.getStringExtra(FRUIT_NAME);
        fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, R.drawable.icon_image);

        // 设置 Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        fruitImageView = (ImageView) findViewById(R.id.fruit_info_image);
        setSupportActionBar(toolbar);

        // 设置 ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            // 显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(frutiName);

        // 设置标题水果图片
        fruitImageView.setImageResource(fruitImageId);
        // Glide.with(this).load(fruitImageId).into(fruitImageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
