package com.example.recyclerview;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    // 存放水果对象的数组
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Snackbar
        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.container);
        Snackbar.make(container , "You Found a new Toast: Snackbar!"
                , Snackbar.LENGTH_LONG).setAction("Action", null).show();

        // 初始化水果数组
        initFruits();

        // 实例化RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);

        // 设置layoutManager布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 设置2水平滚动
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);


        // 设置Adapter（将fruitList数据传入）
        FruitAdapter adapter = new FruitAdapter(fruitList);
        FruitAdapterGrid adapter2 = new FruitAdapterGrid(fruitList);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);


    }

    // 依次将信息添加到数组
    private void initFruits() {
        for (int i=0; i<20; i++) {
            Fruit apple = new Fruit(i+1+" Apple", R.drawable.apple_pic);
            fruitList.add(apple);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sg:
                Intent intentSg = new Intent(this, MainActivityStaggeredGrid.class);
                startActivity(intentSg);
                break;
            case R.id.item_g:
                Intent intentG = new Intent(this, MainActivityGrid.class);
                startActivity(intentG);
                break;
            default:
        }
        return true;
    }
}
