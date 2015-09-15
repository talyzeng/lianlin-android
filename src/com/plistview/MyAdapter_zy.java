package com.plistview;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.listitem_zy2;
import com.lianlin.R;

public class MyAdapter_zy extends BaseAdapter {
	private static final String TAG = "MyAdapter";
	private boolean mBusy = false;
    private ImageLoader_gwc mImageLoader_gwc;
	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}
	private List<Message_zy> messageList;
	private ArrayList<String> nowimglist;
	private int mCount;
	private Context mContext;

	public MyAdapter_zy(int count, Context context,List<Message_zy> messageList,ArrayList<String> nowimglist) {
		this.mCount = count;
		this.mContext = context;
		this.messageList=messageList;
		this.nowimglist=nowimglist;
		mImageLoader_gwc = new ImageLoader_gwc();
	}

	public void gx(int count, Context context,List<Message_zy> messageList,ArrayList<String> nowimglist){
		this.mCount = count;
		this.mContext = context;
		this.nowimglist=nowimglist;
		this.messageList=messageList;
		
	//	mImageLoader = new ImageLoader();
		mImageLoader_gwc = new ImageLoader_gwc();
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
		//Log.d(TAG, "position=" + position + ",convertView=" + convertView);
		ViewHolder viewHolder = null;
	//	   loader = new AsnycImageLoader(); 
		//if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_zy, null);
			//viewHolder = new ViewHolder();
			holders.add( new ViewHolder());
			holders.get(position).text_date=(TextView)convertView.findViewById(R.id.listitem_zy_text_date);  
			holders.get(position).list=(LinearLayout)convertView.findViewById(R.id.listitem_zy_list);  
			
		/*	mImageView.add((ImageView) convertView
					.findViewById(R.id.imgitem));*/
			convertView.setTag(holders.get(position));
	/*	} else {
			holders.add( new ViewHolder());
		}*/
		
		holders.get(position).text_date.setText(messageList.get(position).getdate());
	    
	    try{  	
	    
	    	//Log.v("JSON1","k:"+messageList.get(position).getjson());
	    	//Log.v("JSON2","k:"+messageList.get(position).getjson2());	    	
	    	
	    	JSONArray p=new JSONArray(messageList.get(position).getjson());//homework
	    	JSONArray k=new JSONArray(messageList.get(position).getjson2());//homework_paper
	    	JSONObject p2;
	    	JSONObject k2;
	    	for(int i=0;i<p.length();i++){
	    		String contentUrl = "";
	    		String url = "";
	    		String title2="";
	    		String state="false";
	    		p2=p.getJSONObject(i);//homework  jason
	    		String a=p2.getString("courseware");//courseware jason
	    		JSONObject p3=new JSONObject(a);
	    		try{
	    			title2=p2.getString("textbook_name");
	    		}catch(Exception e){
	    			//Log.v("没有标题2","1");
	    			title2="";
	    			e.printStackTrace();
	    		}
	    		url = p2.getString("homework_paper_endpoint");
	    		//根据不同的作业类型处理不同的作业内容
	    		if(p3.getString("type").equals("listen_text")){
	    			contentUrl = p2.getString("listen_text_endpoint");
	    			//todo  listen_text_endpoint
	    		}else{
	    			//todo
	    			if(p3.getString("type").equals("test_paper")){
	    				contentUrl = p2.getString("test_paper_endpoint");
	    				//todo  test_paper_endpoint
	    			}else{
	    				if(p3.getString("type").equals("reference")){
	    					//contentUrl = p2.getString("listen_text_endpoint");
	    					//todo
	    				}else{
	    					
	    				}
	    			}
	    		}
	    		try{
	    		k2=k.getJSONObject(i);
	    		//Log.v("k2","ccc:"+k2.getString("finished_at"));
	    		if(k2.getString("finished_at").equals(null)||k2.getString("finished_at").equals("null")){
	    			state="";
	    		}else{
	    			state="已完成";
	    		}
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    		setList(position,p2.getString("id"),p3.getString("name"),title2,state,p3.getString("type"),contentUrl,url);
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		    
	  /*  if(messageList.get(position).getUrl().equals(Chuli.yuming)){
	    	Log.v("为空","1");
	    	holders.get(position).mImageView.setVisibility(View.GONE);
	    }else{*/
		  /*      String url = "";
						url =messageList.get(position).getUrl();
							holders.get(position).mImageView.setImageResource(R.drawable.loading);  

							mImageLoader_gwc.loadImage(url, this, holders.get(position));*/
	   // }	
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
		TextView text_id,text_date;
		LinearLayout r1,list;
	}

	String now_id,now_title1,now_title2,now_state;
	
	 ArrayList<String> p_title1=new ArrayList<String>();
	 ArrayList<String> p_title2=new ArrayList<String>();
	 ArrayList<String> p_id=new ArrayList<String>();
	 
	 Integer tj_count=0;
     ArrayList <listitem_zy2> lt_tj=new ArrayList<listitem_zy2>();
    

	    private void setList(int position,String id,String title1,String title2,String state,String type,String contentUrl,String url){
	    	   tj_count=tj_count+1;
	    	 
	  		   lt_tj.add(new listitem_zy2(mContext,null));
	  		   //Bundle bundle2=new Bundle();       
	  	       //bundle2.putString("contentUrl", contentUrl); 
	  	       //bundle2.putString("url", url); 
	  		   
	  	       
	  	       lt_tj.get(tj_count-1);
			listitem_zy2.url = url;
	  	       lt_tj.get(tj_count-1);
			listitem_zy2.contentUrl = contentUrl;
			listitem_zy2.workType = type;
	  		   
	  		   lt_tj.get(tj_count-1).setTextTitle1(title1);
	  		   lt_tj.get(tj_count-1).setTextTitle2(title2);
	  		   lt_tj.get(tj_count-1).setTextID(id);
	  		   
	  		   //Log.v("state",state);
	  		   if(state.equals("已完成")){
	  			  lt_tj.get(tj_count-1).setR1();//设置已完成图标
	  		   }else{
	  			 lt_tj.get(tj_count-1).setR2(); //设置未完成图标
	  		   }
	  		   //根据不同的作业类型设置不同的图标
	  		   if(type.equals("test_paper")){
	  			    //lt_tj.get(tj_count-1).setIMG(R.drawable.type2);
	  			lt_tj.get(tj_count-1).setIMG(R.drawable.type1);
	  		   }else{
	  			  // lt_tj.get(tj_count-1).setIMG(R.drawable.type1);
	  			 lt_tj.get(tj_count-1).setIMG(R.drawable.type2);
	  		   }
	  		   holders.get(position).list.addView(lt_tj.get(tj_count-1));
	  		  	
	      }
}
