package com.lunbo2;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.lianlin.R;

public class ImageAdapter extends BaseAdapter {

	private ArrayList<String> data;
	private LayoutInflater inflater;
	private int width;

	private AsyncBitmapLoader asyncBitmapLoader;

	public ImageAdapter(Context context, ArrayList<String> data, int width) {
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.width = width;
		this.asyncBitmapLoader = new AsyncBitmapLoader();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (data != null) {
			count = data.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		String item = null;
		if (data != null) {
			item = data.get(position);
		}
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.cell_iv);
	//	int height = width * 300 / 500;
		convertView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		BitmapDrawable bitmapDrawable = asyncBitmapLoader.loadBitmap(
				data.get(position), iv, new ImageCallBack() {

					@Override
					public void imageLoad(ImageView imageView,
							BitmapDrawable bitmap) {
						imageView.setImageBitmap(bitmap.getBitmap());
					}
				});
		if (bitmapDrawable != null) {
			iv.setImageBitmap(bitmapDrawable.getBitmap());
		} else {
			iv.setImageResource(R.drawable.loading_lb);
		}
		
		
				
		return convertView;
	}

}
