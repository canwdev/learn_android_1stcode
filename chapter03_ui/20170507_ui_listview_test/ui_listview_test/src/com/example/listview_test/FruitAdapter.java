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
		Fruit fruit = getItem(position); // ��ȡ��ǰFruit��ʵ��
		//View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		//�Ż�? convertView ����������������ڽ�֮ǰ���غõĲ��ֽ��л��棬�Ա�֮����Խ������á�
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView) view.findViewById(R.id.imageView1);
			viewHolder.fruitText = (TextView) view.findViewById(R.id.textView1);
			view.setTag(viewHolder); // ��ViewHolder �洢��View��
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // ���»�ȡViewHolder
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





