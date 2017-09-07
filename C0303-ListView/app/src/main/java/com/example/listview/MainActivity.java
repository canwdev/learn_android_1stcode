package com.example.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private String data[] = { "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
//            "Pineapple", "Strawberry", "Cherry", "Mango"};
// 存放水果对象的数组
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view1);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, data);
//        listView.setAdapter(adapter);

        // 初始化水果数组
        initFruits();

        // 设置FruitAdapter（将活动、布局、fruitList数据传入）
        FruitAdapter adapter = new FruitAdapter(this, R.layout.layout_fruit_item, fruitList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 依次将信息添加到数组
    private void initFruits() {
        for (int i=0; i<20; i++) {
            Fruit apple = new Fruit(i+1+" Apple", R.drawable.apple_pic);
            fruitList.add(apple);
        }
    }
}
