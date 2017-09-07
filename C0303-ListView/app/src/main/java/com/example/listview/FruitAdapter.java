package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit>{

    private int resourceId;

    public FruitAdapter(Context context, int resId, List<Fruit> objects) {
        super(context, resId, objects);
        resourceId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前的Fruit实例
        Fruit fruit = getItem(position);

        View view;
        // 没有优化
//        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        // 优化
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById (R.id.image_fruit);
            viewHolder.fruitName = (TextView) view.findViewById (R.id.text_fruit);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }

        // 没有优化
//        ImageView fruitImage = (ImageView) view.findViewById(R.id.image_fruit);
//        TextView fruitName = (TextView) view.findViewById(R.id.text_fruit);
//        fruitImage.setImageResource(fruit.getImageId());
//        fruitName.setText(fruit.getName());

        // 优化
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());

        return view;
    }

    class ViewHolder {

        ImageView fruitImage;

        TextView fruitName;

    }
}
