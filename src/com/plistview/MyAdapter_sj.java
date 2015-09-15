package com.plistview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lianlin.R;

public class MyAdapter_sj extends BaseAdapter {
	private static final String TAG = "MyAdapter";
	private boolean mBusy = false;
	   private ImageLoader2 mImageLoader2;
	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}
	private List<Message_sj> messageList;
	private ArrayList<String> nowimglist;
	private int mCount;
	private Context mContext;

	public MyAdapter_sj(int count, Context context,List<Message_sj> messageList,ArrayList<String> nowimglist) {
		this.mCount = count;
		this.mContext = context;
		this.messageList=messageList;
		this.nowimglist=nowimglist;
		mImageLoader2 = new ImageLoader2();
	}

	public void gx(int count, Context context,List<Message_sj> messageList,ArrayList<String> nowimglist){
		this.mCount = count;
		this.mContext = context;
		this.nowimglist=nowimglist;
		this.messageList=messageList;
		
	//	mImageLoader = new ImageLoader();
	//	mImageLoader2 = new ImageLoader2();
	}
	
	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	
    ArrayList<ViewHolder> holders=new ArrayList<ViewHolder>();

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "position=" + position + ",convertView=" + convertView);
		ViewHolder viewHolder = null;
	//	   loader = new AsnycImageLoader(); 
		//if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_sj, null);
			//viewHolder = new ViewHolder();
			holders.add( new ViewHolder());
			holders.get(position).text_title=(TextView)convertView.findViewById(R.id.listitem_sj_text_title);   
		    holders.get(position).text_id=(TextView)convertView.findViewById(R.id.listitem_sj_text_id);  
		    holders.get(position).text_phone=(TextView)convertView.findViewById(R.id.listitem_sj_text_phone);  
		    holders.get(position).text_data=(TextView)convertView.findViewById(R.id.listitem_sj_text_date);  
		    holders.get(position).text_address=(TextView)convertView.findViewById(R.id.listitem_sj_text_address);  
		    holders.get(position).r1=(LinearLayout)convertView.findViewById(R.id.listitem_sj_r1);  
		
			
			holders.get(position).mImageView=(ImageView) convertView
					.findViewById(R.id.listitem_sj_img);
			
			
		/*	mImageView.add((ImageView) convertView
					.findViewById(R.id.imgitem));*/
			convertView.setTag(holders.get(position));
	/*	} else {
			holders.add( new ViewHolder());
		}*/
		
		holders.get(position).text_title.setText(messageList.get(position).getTitle());
		holders.get(position).text_id.setText(messageList.get(position).getId());
		holders.get(position).text_data.setText(messageList.get(position).getdata());
		holders.get(position).text_phone.setText(messageList.get(position).getphone());
		holders.get(position).text_address.setText(messageList.get(position).getaddress());
	
		
	  /*  holders.get(position).r1.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View arg0) {
				cpdetailActivity.id=messageList.get(position).getId();
				Intent it=new Intent(mContext,cpdetailActivity.class);
				mContext.startActivity(it);
			}
			
		});*/
		    
		        String url = "";
						url =messageList.get(position).getUrl();
							holders.get(position).mImageView.setImageResource(R.drawable.loading);  

							mImageLoader2.loadImage(url, this, holders.get(position));
				
		/*String url = "";
		url =messageList.get(position).getUrl();
		if (!mBusy) {
			mImageLoader.loadImage(url, this, viewHolder);
			viewHolder.timeView.setText(messageList.get(position).getTime());
			viewHolder.messageView.setText(messageList.get(position).getMessage());
		} else {
			Bitmap bitmap = mImageLoader.getBitmapFromCache(url);
			if (bitmap != null) {
				viewHolder.mImageView.setImageBitmap(bitmap);
			} else {
				mImageLoader.loadImage(url, this, viewHolder);
			}
			viewHolder.timeView.setText(messageList.get(position).getTime());
			viewHolder.messageView.setText(messageList.get(position).getMessage());
		}*/
		
		
		
		return convertView;
	}
	
	/*public void setImageView(Drawable a){
		ViewHolder.mImageView.setImageDrawable(a);
	}*/
	
	 

	//ArrayList<ImageView> mImageView=new ArrayList<ImageView>();
	class ViewHolder {
		
		ImageView mImageView;
		TextView text_id,text_title,text_phone,text_data,text_address;
		LinearLayout r1,l_address;
	}

}
