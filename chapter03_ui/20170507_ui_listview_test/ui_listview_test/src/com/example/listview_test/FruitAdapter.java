package com.example.listview_test;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit>{
	private int resourceId;
	
	public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Fruit fruit = getItem(position); // 获取当前Fruit的实例
		//View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		//优化? convertView 参数，这个参数用于将之前加载好的布局进行缓存，以便之后可以进行重用。
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView) view.findViewById(R.id.imageView1);
			viewHolder.fruitText = (TextView) view.findViewById(R.id.textView1);
			view.setTag(viewHolder); // 将ViewHolder 存储在View中
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.fruitImage.setImageResource(fruit.getImageId());
		viewHolder.fruitText.setText(fruit.getName());
		return view;
	}
	
	class ViewHolder {
		ImageView fruitImage;
		TextView fruitText;
	}
	
}





