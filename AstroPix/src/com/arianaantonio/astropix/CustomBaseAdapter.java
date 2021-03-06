package com.arianaantonio.astropix;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loopj.android.image.SmartImageView;

@SuppressLint("InflateParams") public class CustomBaseAdapter extends BaseAdapter {
	Context context;
	List<ImageObject> imageItems;
	
	public CustomBaseAdapter(Context context, List<ImageObject> items) {
		this.context = context;
		this.imageItems = items;
	}
	
	private class ViewHolder {
		SmartImageView imageView;
		
	}

	@Override
	public int getCount() {
		
		return imageItems.size();
	}

	@Override
	public Object getItem(int position) {
		
		return imageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return imageItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
	
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_image, null);  
			holder = new ViewHolder();
			holder.imageView = (SmartImageView) convertView.findViewById(R.id.smartimage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			//holder.imageView = (SmartImageView) convertView.findViewById(R.id.smartimage);
		}
		ImageObject image = (ImageObject) getItem(position);  
		holder.imageView.setImageUrl(image.getUrl());
		
		return convertView;
	}

}
