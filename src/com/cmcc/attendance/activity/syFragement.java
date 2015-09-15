package com.cmcc.attendance.activity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leadcloud.CustomConversationHandler;
import leadcloud.CustomMsgHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.gps.MyApplication;
import com.huison.tools.Chuli;
import com.lianlin.R;
import com.plistview.Message_zy;
import com.plistview.MyAdapter_zy;
import com.plistview.PullToRefreshBase;
import com.plistview.PullToRefreshBase.OnRefreshListener;
import com.plistview.PullToRefreshListView;
public class syFragement extends BaseFragement {
	RelativeLayout r_xb,r_lw,r_wp,r_zf,r_jz,r_zy;
	RelativeLayout r_chose1,r_chose2;
	TextView text_chose1,text_chose2;
	RelativeLayout r_aqxy,r_nsns,r_jhjj,r_jpzs,r_zjmd,r_cjbm;	
	String zhuangtai;	//1  表示获取已经完成的作业列表   0  表示获取未完成的作业列表
	String xx_num;	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragement_sy, container, false);
		setContentView(rootView);		
		text_chose1=(TextView)rootView.findViewById(R.id.sy_text_chose1);//已完成选项卡
		text_chose2=(TextView)rootView.findViewById(R.id.sy_text_chose2);//未完成选项卡
		r_chose1=(RelativeLayout)rootView.findViewById(R.id.sy_r_tab1);//选择选项卡
		r_chose2=(RelativeLayout)rootView.findViewById(R.id.sy_r_tab2);//选择选项卡
		  /***************************************列表1部分*************************************/
	    mPullListView1 = new PullToRefreshListView(rootView.getContext());
	    list1=(LinearLayout)rootView.findViewById(R.id.sy_list1);
	    LinearLayout.LayoutParams prarms = new LinearLayout.LayoutParams(android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT,android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT);
	   //设置LIST的边距离
	        //  prarms.setMargins(10, 10, 10, 10);
	    mPullListView1.setLayoutParams(prarms);
	    mPullListView1.setDividerDrawable(null);      

	    list1.addView(mPullListView1);
	        
	    FrameLayout.LayoutParams params1 = new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,FrameLayout.LayoutParams.FILL_PARENT);         
	    params1.setMargins(0, 0, 0, 0);
	    list1.setLayoutParams(params1);
	    mPullListView1.setPullLoadEnabled(false);
	    mPullListView1.setScrollLoadEnabled(true);
	    
	    mCurIndex1 = mLoadDataCount1;
	    mListItems1 = new LinkedList<String>();
	    mListView1 = mPullListView1.getRefreshableView();
	      /*  mListView1.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	               
	            	webActivity.title="宝贝详情";
					webActivity.url=messageList2.get(position).getlink();
					
	            	Intent it=new Intent(rootView.getContext(),webActivity.class);
					startActivity(it);
	            }
	        });*/
	        
	     mPullListView1.setOnRefreshListener(new OnRefreshListener<ListView>() {
	    	 @Override
	         public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	                mIsstart1 = true;	               
                 /*   now_page1=1;
	    	        isSX1=false;
	    	        now_page1=1;
	    	        messageList1=new ArrayList<com.plistview.Message_zy>();
	    	        adapter1 = new MyAdapter_zy(messageList1.size(), rootView.getContext(),messageList1,null);
	    	        */
	                new GetDataTask().execute();	           
	         }

	    	 @Override
	    	 public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	                mIsstart1 = false;
	                new GetDataTask().execute();
	            }
	     });
	     setLastUpdateTime1();	        
	     isSX1=false;
	     mIsstart1 = false;
	     now_page1=1;
	     messageList1=new ArrayList<com.plistview.Message_zy>();
	     adapter1 = new MyAdapter_zy(messageList1.size(), rootView.getContext(),messageList1,null);
	     mListView1.setAdapter(adapter1);
	     mListView1.setDivider(null);
	     mPullListView1.doPullRefreshing(true, 500);
/***************************************列表1部分*************************************/
	        
/***************************************列表2部分*************************************/
	     mPullListView2 = new PullToRefreshListView(rootView.getContext());
	     list2=(LinearLayout)rootView.findViewById(R.id.sy_list2);
	     LinearLayout.LayoutParams prarms2 = new LinearLayout.LayoutParams(android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT,android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT);
	   //设置LIST的边距离
	        //  prarms.setMargins(10, 10, 10, 10);
	     mPullListView2.setLayoutParams(prarms2);
	     mPullListView2.setDividerDrawable(null);  	        

	     list2.addView(mPullListView2);
	        
	     FrameLayout.LayoutParams params2 = new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,FrameLayout.LayoutParams.FILL_PARENT);         
	     params1.setMargins(0, 0, 0, 0);
	     list2.setLayoutParams(params2);
	        
	        
	     mPullListView2.setPullLoadEnabled(false);
	     mPullListView2.setScrollLoadEnabled(true);
	    
	     mCurIndex2 = mLoadDataCount2;
	     mListItems2 = new LinkedList<String>();
	     mListView2 = mPullListView2.getRefreshableView();
	    /*    mListView2.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	               
	            	
					webActivity.title="店铺详情";
					webActivity.url=messageList2.get(position).getlink();
					
	            	Intent it=new Intent(rootView.getContext(),webActivity.class);
					startActivity(it);
	            }
	        });*/
	        
	      mPullListView2.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	                mIsstart2 = true;
	              /*  now_page2=1;
	                isSX2=false;
	    	        now_page2=1;
	    	        messageList2=new ArrayList<com.plistview.Message_zy>();
	    	        adapter2 = new MyAdapter_zy(messageList2.size(), rootView.getContext(),messageList2,null);
	    	        */
	                new GetDataTask2().execute();
	           
	            }

	            @Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	                mIsstart2 = false;
	                new GetDataTask2().execute();
	            }
	       });
	       setLastUpdateTime();
	        
	       isSX2=false;
	       mIsstart2 = false;
	       now_page2=1;
	       messageList2=new ArrayList<com.plistview.Message_zy>();
	       adapter2 = new MyAdapter_zy(messageList2.size(), rootView.getContext(),messageList2,null);
	       mListView2.setAdapter(adapter2);
	       mListView2.setDivider(null);
	       mPullListView2.doPullRefreshing(true, 500);
	        
/***************************************列表2部分*************************************/
		 
	        
	        
	        list1.setVisibility(View.VISIBLE);
	        list2.setVisibility(View.INVISIBLE);
	        
	        r_chose1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					  
			        list1.setVisibility(View.VISIBLE);
			        list2.setVisibility(View.INVISIBLE);
			        r_chose1.setBackgroundResource(R.drawable.chose1);
			        r_chose2.setBackgroundResource(R.drawable.chose2);
			        text_chose1.setTextColor(0xff25bdd4);
			        text_chose2.setTextColor(0xff666666);
			        
			        zhuangtai="0";
			        
			        mIsstart1 = true;
		               
	                now_page1=1;
		    	    isSX1=false;
		    	    now_page1=1;
		    	    messageList1=new ArrayList<com.plistview.Message_zy>();
		    	    adapter1 = new MyAdapter_zy(messageList1.size(), rootView.getContext(),messageList1,null);
		    	        
		    	    mListView1.setAdapter(adapter1);
		    	    mListView1.setDivider(null);
		    	        
		            new GetDataTask().execute();
				}
			});
	        
            r_chose2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					  
			        list1.setVisibility(View.INVISIBLE);
			        list2.setVisibility(View.VISIBLE);

			        r_chose1.setBackgroundResource(R.drawable.chose2);
			        r_chose2.setBackgroundResource(R.drawable.chose1);

			        
			        text_chose1.setTextColor(0xff666666);
			        text_chose2.setTextColor(0xff25bdd4);
			        
			        zhuangtai="1";
			        
			        mIsstart2 = true;
		               
                    now_page2=1;
	    	        isSX2=false;
	    	        messageList2=new ArrayList<com.plistview.Message_zy>();
	    	        adapter2 = new MyAdapter_zy(messageList2.size(), rootView.getContext(),messageList2,null);
	    	        
	    	        mListView2.setAdapter(adapter2);
	    	        mListView2.setDivider(null);
	    	        
	                new GetDataTask2().execute();
				}
			});
	        
            zhuangtai="";
            
		return rootView;
	}
	
	
/***************************************列表1部分*************************************/
		 LinearLayout list1;	 
	     public static ListView mListView1;
		 private PullToRefreshListView mPullListView1;
		 private ArrayAdapter<String> madapter1;
		 private LinkedList<String> mListItems1;
		 private SimpleDateFormat mDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 private boolean mIsstart1 = true;
		 private int mCurIndex1 = 0;
		 private static final int mLoadDataCount1 = 100;
		 private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		        @Override
		        protected String[] doInBackground(Void... params) {
		            // Simulates a background job.
		        	handle_getList1();
		            return mStrings1;
		        }
		        @Override
		        protected void onPostExecute(String[] result) {
		            boolean hasMoreData = true;
		            if (mIsstart1) {
		                mListItems1.addFirst("Added after refresh...");
		            } else {
		            
		            	if(messageList1.size()%10!=0){
		                    hasMoreData = false;
		            	}
		            }
		            
		            mPullListView1.onPullDownRefreshComplete();
		            mPullListView1.onPullUpRefreshComplete();
		            mPullListView1.setHasMoreData(hasMoreData);
		            setLastUpdateTime();           
		      
		            try{
		            	
		            	for(int i=0;i<now_add1.size();i++){
		            	ImageView imageView = (ImageView) mListView1.findViewWithTag(i+((now_page1-1)*10-1));  
		 		        String url = "";
		 				url =now_add1.get(i);
		 				
		 			//	mImageLoader2.loadImage(url, adapter2, imageView);
		            	}
		            	
		            adapter1.gx(messageList1.size(), rootView.getContext(),messageList1,now_add1);
		            adapter1.notifyDataSetChanged();    
		         //   mPullListView2.setLastItem(messageList2.size());
		                 //这句话很重要，加载完毕后是否滚动靠他
		           // mListView2.setSelection(messageList2.size()-21 );//设置listview的当前位置，如果不设
		           // mListView2.getdi
		           
		            mListView1.scrollTo(BaseActivity.now_x,BaseActivity.now_y);
		            }catch(Exception e){
		            	e.printStackTrace();
		            }
		            super.onPostExecute(result);
		        }
		    }
		    private void setLastUpdateTime1() {
		        String text = formatDateTime(System.currentTimeMillis());
		        mPullListView1.setLastUpdatedLabel(text);
		    }

		    private String formatDateTime1(long time) {
		        if (0 == time) {
		            return "";
		        }
		        
		        return mDateFormat1.format(new Date(time));
		    }
		    
		    public static final String[] mStrings1 = {
		        "Abbaye de Belloc", "Abbaye du Mont des Cats"
		    };
			private Handler mHandler1;
		    private List<Message_zy> messageList1;
			private List<Message_zy> messageTemp1=new ArrayList<Message_zy>();
			MyAdapter_zy adapter1;
			private int start1 = 0;
			private static int refreshCnt1 = 0;
			ArrayList<HashMap<String, Object>> arraylist1=new ArrayList<HashMap<String,Object>>();
			boolean isSX1;
		    SharedPreferences userMessage1  ;
		    String nextUrl1="";
			static boolean isExit1=false;
		    
			  public static String replaceBlank1(String str) {
			        String dest = "";
			        if (str!=null) {
			            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			            Matcher m = p.matcher(str);
			            dest = m.replaceAll("");
			        }
			        return dest;
			    }
			    ImageGetter imageGetter1 = new ImageGetter()  
			    {  
			        @Override  
			        public Drawable getDrawable(String source)  
			        {  
			            int id = Integer.parseInt(source);  
			            Drawable d = getResources().getDrawable(id);  
			            d.setBounds(0, 0, d.getIntrinsicWidth(), d .getIntrinsicHeight());  
			            return d;  
			        }  
			    };  
			    int now_page1=1;
			    
			    ArrayList<String> now_add1=new ArrayList<String>();
			    
	private void handle_getList1() {
		
				if(!isSX1){
					
					Log.v("第一次","1");
					 isSX1=true;
					    
					    try{
					messageList1=new ArrayList<Message_zy>();
				
					now_add1.clear();
					
					String b= Chuli.HttpGetData(Chuli.yuming+"/v1/users/me",BaseActivity.now_userid);
	            	Log.v("返回1：",b);
	   		        
	            	JSONObject e=new JSONObject(b);
	            	String lj="";
	            	if(e.getString("student_workathoners_endpoint")!=null){//根据用户类型判断获取作业的不同的地址
	            		lj=e.getString("student_workathoners_endpoint");
	            	}else{
	            		lj=e.getString("teacher_workathons_endpoint");
	            	}
	            	
	            	
	            	//String lj=e.getString("student_workathoners_endpoint");
	            	//移动到login中直接获取  modified by taly 10.11
	            	//BaseActivity.now_clientToken=e.getString("client_token");//获取用户聊天的唯一标识
	            	
	            	   String a="";
	                   
	                   if(zhuangtai.equals("1")){//根据不同的进入页面方式，选择不同的作业列表  已完成
	                   a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=finished",BaseActivity.now_userid);
	                   Log.v("List1   作业1","k:"+a);
	                   }
	                   
	                   if(zhuangtai.equals("0")){//未完成
	                       a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=unfinished",BaseActivity.now_userid);
	                       Log.v("List1   作业2","k:"+a);
	                       }
	                   
	                   if(zhuangtai.equals("")){//第一次进入
	                       a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=unfinished",BaseActivity.now_userid);
	                       Log.v("List1   作业3","k:"+a);
	                       }
					    JSONArray p2=new JSONArray(a);
			               JSONObject p3;
			             
			               for(int i=0;i<p2.length();i++){
			              	 p3=p2.getJSONObject(i);
			              	 
			              	 String work=p3.getString("workathon");
			              	 JSONObject p4=new JSONObject(work);
			                Message_zy message=new  Message_zy();
				            message.setdate(p4.getString("published_on"));
							message.setjson(p4.getString("homeworks"));//作业内容
							message.setjson2(p3.getString("homework_papers"));	//答题内容
							//message.setclassJason(p4.getString("clazz"));
							//message.setremark(p3.getString("remark"));
							
							now_add1.add("");
							messageList1.add(message);

						//	 loadImage(Chuli.yuming+p3.getString("titlephoto"),i);
			                  
			               }
			               
					    }catch(Exception e){
					    	e.printStackTrace();
					    }
	   		        	cwjHandler.post(mUpdateResults_success);
				}else{
					Log.v("第2次","1");
					/*now_add1.clear();
					now_page1=now_page1+1;
					try{
						String b= Chuli.HttpGetData("https://api.ll100.com/v1/users/me",BaseActivity.now_userid);
		            	Log.v("返回1：",b);
		   		        
		            	JSONObject e=new JSONObject(b);
		            	String lj=e.getString("student_workathoners_endpoint");
		            	
		                String a= Chuli.HttpGetData("https://api.ll100.com/"+lj,BaseActivity.now_userid);
		            	
		            			
						
						    JSONArray p2=new JSONArray(a);
				               JSONObject p3;
				               for(int i=0;i<p2.length();i++){
				              	 p3=p2.getJSONObject(i);
				              	 
				              	 String work=p3.getString("workathon");
				              	 JSONObject p4=new JSONObject(work);
				              	 
				              			 
				              			 
				                	 Message_zy message=new  Message_zy();
					            	 message.setdate(p4.getString("published_on"));
									 message.setjson(p4.getString("homeworks"));
									 message.setjson2(p3.getString("homework_papers"));
										
									 now_add1.add("");
									 messageList1.add(message);
				                
				              	

						//	 loadImage(Chuli.yuming+p3.getString("titlephoto"),i);
			                  
			               }
			               
		            
				    }catch(Exception e){
				    	e.printStackTrace();
				    }
					*/
				 
				} 
				
				try{
				for(int i=0;i<messageList1.size();i++){
					 Message_zy message=messageList1.get(i);
					messageTemp1.add(message);
					HashMap< String, Object> hashmap=new HashMap<String, Object>();
					hashmap.put("message",message.getdate()); 
					hashmap.put("time", message.getid());
					hashmap.put("imgitem", R.drawable.cxmr);
					arraylist1.add(hashmap);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				 
		
		
		
				
		}

	/***************************************列表1部分*************************************/

	
	
	
	  /***************************************列表2部分*************************************/
	 LinearLayout list2;	 
     public static ListView mListView2;
	    private PullToRefreshListView mPullListView2;
	    private ArrayAdapter<String> madapter2;
	    private LinkedList<String> mListItems2;
	    private SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    private boolean mIsstart2 = true;
	    private int mCurIndex2 = 0;
	    private static final int mLoadDataCount2 = 100;
	    
	    
	    private class GetDataTask2 extends AsyncTask<Void, Void, String[]> {
	        @Override
	        protected String[] doInBackground(Void... params) {
	            // Simulates a background job.
	        	handle_getList2();
	            return mStrings2;
	        }
	        @Override
	        protected void onPostExecute(String[] result) {
	            boolean hasMoreData = true;
	            if (mIsstart2) {
	                mListItems2.addFirst("Added after refresh...");
	            } else {
	            
	            	if(messageList2.size()%10!=0){
	                    hasMoreData = false;
	            	}
	            }
	            
	            mPullListView2.onPullDownRefreshComplete();
	            mPullListView2.onPullUpRefreshComplete();
	            mPullListView2.setHasMoreData(hasMoreData);
	            setLastUpdateTime();           
	      
	            try{
	            	
	            	for(int i=0;i<now_add2.size();i++){
	            	ImageView imageView = (ImageView) mListView2.findViewWithTag(i+((now_page2-1)*10-1));  
	 		        String url = "";
	 				url =now_add2.get(i);
	 				
	 			//	mImageLoader2.loadImage(url, adapter2, imageView);
	            	}
	            	
	            adapter2.gx(messageList2.size(), rootView.getContext(),messageList2,now_add2);
	            adapter2.notifyDataSetChanged();    
	         //   mPullListView2.setLastItem(messageList2.size());
	                 //这句话很重要，加载完毕后是否滚动靠他
	           // mListView2.setSelection(messageList2.size()-21 );//设置listview的当前位置，如果不设
	           // mListView2.getdi
	           
	            mListView2.scrollTo(BaseActivity.now_x,BaseActivity.now_y);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            super.onPostExecute(result);
	        }
	    }
	    
	    
	    
	 
	  
	    private void setLastUpdateTime() {
	        String text = formatDateTime(System.currentTimeMillis());
	        mPullListView2.setLastUpdatedLabel(text);
	    }

	    private String formatDateTime(long time) {
	        if (0 == time) {
	            return "";
	        }
	        
	        return mDateFormat2.format(new Date(time));
	    }
	    
	    public static final String[] mStrings2 = {
	        "Abbaye de Belloc", "Abbaye du Mont des Cats"
	    };
	    
		private Handler mHandler2;
	    private List<Message_zy> messageList2;
		private List<Message_zy> messageTemp2=new ArrayList<Message_zy>();
		MyAdapter_zy adapter2;
		private int start2 = 0;
		private static int refreshCnt2 = 0;
		ArrayList<HashMap<String, Object>> arraylist2=new ArrayList<HashMap<String,Object>>();
		boolean isSX2;
	    SharedPreferences userMessage2  ;
	    String nextUrl2="";
		static boolean isExit2=false;
	    
		  public static String replaceBlank(String str) {
		        String dest = "";
		        if (str!=null) {
		            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		            Matcher m = p.matcher(str);
		            dest = m.replaceAll("");
		        }
		        return dest;
		    }
		    ImageGetter imageGetter = new ImageGetter()  
		    {  
		        @Override  
		        public Drawable getDrawable(String source)  
		        {  
		            int id = Integer.parseInt(source);  
		            Drawable d = getResources().getDrawable(id);  
		            d.setBounds(0, 0, d.getIntrinsicWidth(), d .getIntrinsicHeight());  
		            return d;  
		        }  
		    };  
		    int now_page2=1;
		    
		    ArrayList<String> now_add2=new ArrayList<String>();
		    
	private void handle_getList2() {	
			if(!isSX2){				
				Log.v("第一次","1");
				 isSX2=true;				    
				try{
				messageList2=new ArrayList<Message_zy>();			
				now_add2.clear();
				String b= Chuli.HttpGetData(Chuli.yuming+"/v1/users/me",BaseActivity.now_userid);
            	//Log.v("返回1：",b);   		        
            	JSONObject e=new JSONObject(b);//用户当前信息            	
            	String lj="";//作业列表地址            	
            	if(e.getString("student_workathoners_endpoint")!=null){//根据用户类型判断获取作业的不同的地址
            		lj=e.getString("student_workathoners_endpoint");
            	}else{
            		lj=e.getString("teacher_workathons_endpoint");
            	}
            	xx_num=e.getString("unfinished_homeworks_count");
            	//聊天ID  login中获取    modif by  taly
            	//BaseActivity.now_clientToken=e.getString("client_token");
            	
                String a="";
                
                if(zhuangtai.equals("1")){
                a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=finished",BaseActivity.now_userid);//已完成
                }
                
                if(zhuangtai.equals("0")){
                    a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=unfinished",BaseActivity.now_userid);//未完成
                    }
                
                if(zhuangtai.equals("")){
                    a=Chuli.HttpGetData(Chuli.yuming+lj+"?filter=unfinished",BaseActivity.now_userid);//第一次进入
                    }
            	
                //Log.v("最终链接:",Chuli.yuming+lj+"?filter=finished");
            	
            	
            /*	String k=Chuli.HttpGetData("https://api.ll100.com//v1/clazzes", BaseActivity.now_userid);
				Log.v("测试返回节点：",k);*/
				
				    JSONArray p2=new JSONArray(a);
		               JSONObject p3;
		               for(int i=0;i<p2.length();i++){
		              	 p3=p2.getJSONObject(i);
		              	 
		              	 String work=p3.getString("workathon");
		              	 JSONObject p4=new JSONObject(work);   
		                	 Message_zy message=new  Message_zy();
			            	 message.setdate(p4.getString("published_on"));
							 message.setjson(p4.getString("homeworks"));
							 message.setjson2(p3.getString("homework_papers"));
							 //message.setclassJason(p4.getString("clazz"));
							 //message.setremark(p3.getString("remark"));	
							 now_add2.add("");
							 messageList2.add(message);
					//	 loadImage(Chuli.yuming+p3.getString("titlephoto"),i);
		                  
		               }
		               
		               cwjHandler.post(mUpdateResults_success2);
				    }catch(Exception e){
				    	e.printStackTrace();
				    }
					
				    
   		        //	cwjHandler.post(mUpdateResults_success);
   		        
				 
			}else{
				Log.v("第2次","1");
			/*	now_add2.clear();
				now_page2=now_page2+1;
				try{
					String b= Chuli.HttpGetData("https://api.ll100.com/v1/users/me",BaseActivity.now_userid);
	            	Log.v("返回1：",b);
	   		        
	            	JSONObject e=new JSONObject(b);
	            	String lj=e.getString("student_workathoners_endpoint");
	            	
	                String a= Chuli.HttpGetData("https://api.ll100.com/"+lj,BaseActivity.now_userid);
	            	
	            			
					
					    JSONArray p2=new JSONArray(a);
			               JSONObject p3;
			               for(int i=0;i<p2.length();i++){
			              	 p3=p2.getJSONObject(i);
			              	 
			              	 String work=p3.getString("workathon");
			              	 JSONObject p4=new JSONObject(work);
			              	 
			              			 
			              			 
			                	 Message_zy message=new  Message_zy();
				            	 message.setdate(p4.getString("published_on"));
								 message.setjson(p4.getString("homeworks"));
								 message.setjson2(p3.getString("homework_papers"));
									
								 now_add2.add("");
								 messageList2.add(message);
			                
			              	
					//	 loadImage(Chuli.yuming+p3.getString("titlephoto"),i);
		                  
		               }
		               
	            
			    }catch(Exception e){
			    	e.printStackTrace();
			    }
				
			 */
			} 
			
			try{
			for(int i=0;i<messageList2.size();i++){
				 Message_zy message=messageList2.get(i);
				messageTemp2.add(message);
				HashMap< String, Object> hashmap=new HashMap<String, Object>();
				hashmap.put("message",message.getdate()); 
				hashmap.put("time", message.getid());
				hashmap.put("imgitem", R.drawable.cxmr);
				arraylist2.add(hashmap);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

/***************************************列表2部分*************************************/



/***************************************leadcloud************************************/

AVIMClientCallback clientCallback;
final Handler cwjHandler = new Handler();
final Runnable mUpdateResults_success = new Runnable() {
    public void run() {
    	
    	HomeActivity.setTextNum(xx_num);    	

    	//Log.v("消息数量？：","k:"+xx_num);
    	
    	//Log.v("当前的ClientToken1","k:"+BaseActivity.now_clientToken);
    	//登录即时通讯
    	final AVIMClient imClient = AVIMClient.getInstance(BaseActivity.now_clientToken);
    	
    	//Log.v("当前的ClientToken2","k:"+BaseActivity.now_clientToken);
    	
    	clientCallback=new AVIMClientCallback(){
	    	  @Override
	    	  public void done(AVIMClient client, AVException e) {
	    	    if (null != e) {
	    	      // 出错了，可能是网络问题无法连接 LeanCloud 云端，请检查网络之后重试。
	    	      // 此时聊天服务不可用。
	    	    	Log.v("聊天登录失败！","即时通讯登录成功！");
	    	      e.printStackTrace();
	    	    } else {
	    	      // 成功登录，可以开始进行聊天了（假设为 MainActivity）。	    	    
	    	        MyApplication.setClientIdToPre(BaseActivity.now_clientToken);
	    	          
	    	    	//Log.v("聊天登录成功！","即时通讯登录成功！");
	    	    };
	    	  }
	    	};
    	imClient.open(clientCallback);
    	
    	
 		// 设置事件响应接口
 	//	AVIMClient.setClientEventHandler(new CustomNetworkHandler());
 		AVIMMessageManager.setConversationEventHandler(new CustomConversationHandler());
 		AVIMMessageManager.registerDefaultMessageHandler(new CustomMsgHandler());
    }
	};
	
	
	final Runnable mUpdateResults_success2 = new Runnable() {
	    public void run() {
	    	HomeActivity.setTextNum(xx_num);
	    }
		};
		
	/*******************************************leancloud********************************************************/
	
	

		@Override
		public void onDestroy(){
			super.onDestroy();
		}
		/*******************************************leancloud********************************************************/

/***************************************leadcloud************************************/


}
