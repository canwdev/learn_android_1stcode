package com.example.materialdesigntest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private Fruit[] fruits = {new Fruit("Apple", R.drawable.fruit_apple)
            , new Fruit("Banana", R.drawable.fruit_banana)
            , new Fruit("Orange", R.drawable.fruit_orange)
            , new Fruit("Pear", R.drawable.fruit_pear)
            , new Fruit("Pineapple", R.drawable.fruit_pineapple)
            , new Fruit("Cherry", R.drawable.fruit_cherry)
            , new Fruit("Grape", R.drawable.fruit_grape)
            , new Fruit("Mango", R.drawable.fruit_mango)
            , new Fruit("Watermelon", R.drawable.fruit_watermelon)
    };

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 延时
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // （重新）初始化水果
                        initFruits();
                        // 更新 adapter
                        adapter.notifyDataSetChanged();
                        // 隐藏转圈々
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置MD的ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // MD 抽屉 弹出方法1
        /*
        // 获取ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 设置点击按钮弹出Drawer
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_48dp);
        }*/

        // MD 抽屉 弹出方法2
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        // 设置抽屉内的点击事件
        final NavigationView navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setCheckedItem(R.id.item_settings);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.drawer_item_backup) {
                    Intent intent = new Intent(MainActivity.this, FruitActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.drawer_item_github) {
                    // 浏览器
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://github.com/"));
                    startActivity(intent);
                }

                navigation.setCheckedItem(item.getItemId());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // 点击按钮弹出Drawer
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // 设置下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.fruit_swipe_refresh);
        // 主色调
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        // 初始化水果 RecyclerView
        initFruits();
        recyclerView = (RecyclerView) findViewById(R.id.fruit_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 点击按钮弹出Drawer
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.item_settings:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.item_backup:
                Toast.makeText(this, "备份成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_delete:
                recyclerView.setVisibility(View.GONE);
                // MD Snackbar。注意，Snackbar不是Toast的代替品。
                // CoordinatorLayout + FloatingActionButton
                Snackbar.make(fab, "Data deleted.", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recyclerView.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // 判断是否打开了抽屉，打开了则关闭，否则退出
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

