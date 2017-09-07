
package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivityStaggeredGrid extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    private void initFruits() {
        for (int i=0; i<100; i++) {
            Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            fruitList.add(apple);
        }
    }

    // 构建随机长度重复字符串
    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for (int i=1; i<length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staggered_grid);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                4, StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapterGrid adapterGrid = new FruitAdapterGrid(fruitList);
        recyclerView.setAdapter(adapterGrid);
    }
}
