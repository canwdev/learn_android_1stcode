package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;

    // 构造方法（获取活动Context、布局资源文件ID、水果List对象数组
    public FruitAdapter(Context context, int resId, List<Fruit> objects) {
        super(context, resId, objects);
        resourceId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前屏幕中的Fruit实例
        Fruit fruit = getItem(position);

        View view;

        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.image_fruit);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.text_fruit);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }

        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());

        return view;
    }

    class ViewHolder {

        ImageView fruitImage;
        TextView fruitName;

    }
}
