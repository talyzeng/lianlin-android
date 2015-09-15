package com.plistview;

import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;

import com.plistview.MyAdapter_zy.ViewHolder;

public class ImageLoader_gwc {
	//异步加载图标
			private static AsnycImageLoader_listview loader = null; 
	
			public ImageLoader_gwc(){
			loader = new AsnycImageLoader_listview(); 
			}
			
	public void loadImage(String url, BaseAdapter adapter,final ViewHolder v) { 
		
		Drawable cacheImage = loader.loadDrawable(url, new AsnycImageLoader_listview.ImageCallback() { 
		@Override 
		public void imageLoaded(Drawable drawable) { 
		try{
			
			v.mImageView.setImageDrawable(drawable);
		
		}catch(Exception e){
		e.printStackTrace();
	}
		} 
		}); 
		if (cacheImage != null) { 
			
		
			v.mImageView.setImageDrawable(cacheImage);
		} 
	
		} 
	
	 Drawable img_loading;
 /*****************************搜索商品*************************************/
}
