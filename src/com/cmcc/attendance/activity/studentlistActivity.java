package com.cmcc.attendance.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leadcloud.ConversationType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.gps.MyApplication;
import com.huison.tools.Chuli;
import com.lianlin.R;
import com.plistview.Message_student;
import com.plistview.MyAdapter_student;
import com.plistview.PullToRefreshBase;
import com.plistview.PullToRefreshBase.OnRefreshListener;
import com.plistview.PullToRefreshListView;

public class studentlistActivity extends Activity {

	ImageView btn_return;
	public static String title;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlist);
    
        btn_return=(ImageView)findViewById(R.id.studentlist_btn_return);
        btn_return.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        });
        
     
        
        /***************************************列表部分*************************************/
        mPullListView = new PullToRefreshListView(this);
        list1=(LinearLayout)findViewById(R.id.studentlist_list1);
        LinearLayout.LayoutParams prarms = new LinearLayout.LayoutParams(android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT,android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT);
   //设置LIST的边距离
        //  prarms.setMargins(10, 10, 10, 10);
        mPullListView.setLayoutParams(prarms);
        mPullListView.setDividerDrawable(null);  
        

        list1.addView(mPullListView);
        
        LinearLayout.LayoutParams params1 = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);         
        params1.setMargins(10, 10, 10, 10);
        list1.setLayoutParams(params1);
        
        
        mPullListView.setPullLoadEnabled(false);
        mPullListView.setScrollLoadEnabled(true);
    
        mCurIndex = mLoadDataCount;
        mListItems = new LinkedList<String>();
        mListView = mPullListView.getRefreshableView();
        
        
        /*学生列表点击开始单聊
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
               
        
            	  fetchConversationWithClientIds(Arrays.asList(messageList.get(position).getId()), ConversationType.OneToOne, new
			              AVIMConversationCreatedCallback
			                  () {
			                @Override
			                public void done(AVIMConversation conversation, AVException e) {
			                  if (filterException(e)) {
			                	   chatActivity.startActivity(studentlistActivity.this, conversation.getConversationId(),messageList.get(position).getname());
			                  
			                  }
			                }
			              });
            }
        });
        */
        mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsStart = true;
                
                now_page=0;
                messageList=new ArrayList<com.plistview.Message_student>();
                adapter = new MyAdapter_student(messageList.size(), studentlistActivity.this,messageList,null);
                mListView.setAdapter(adapter);
                mListView.setDivider(null);
                /***************************************列表部分*************************************/
                
            	  mIsStart = true;
            	  isSX=false;
                new GetDataTask().execute();
           
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsStart = false;
                new GetDataTask().execute();
            }
        });
        setLastUpdateTime();
        
        mPullListView.doPullRefreshing(true, 500);
        
        
        now_page=0;
        messageList=new ArrayList<com.plistview.Message_student>();
        adapter = new MyAdapter_student(messageList.size(), studentlistActivity.this,messageList,null);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
        /***************************************列表部分*************************************/
        
    	  mIsStart = true;
    	  isSX=false;
        
    	  
   
    	  
    	  
    }
	  
	 /*****************************搜索商品*************************************/
	  
    
	   
    
        LinearLayout list1;	 
        public static ListView mListView;
	    private PullToRefreshListView mPullListView;
	    private ArrayAdapter<String> mAdapter;
	    private LinkedList<String> mListItems;
	    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    private boolean mIsStart = true;
	    private int mCurIndex = 0;
	    private static final int mLoadDataCount = 100;
	    
	    
	    @Override
	    protected void onDestroy(){
	    	messageList.clear();
	        now_page=0;
	        messageList=null;
	        adapter =null;
	    	mListView.setAdapter(null);
	    	mIsStart = true;
	    	mCurIndex = 0;
	    	super.onDestroy();
	    }
	    
	    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

	        @Override
	        protected String[] doInBackground(Void... params) {
	            // Simulates a background job.
	        	handle_getList();
	            return mStrings;
	        }
	        @Override
	        protected void onPostExecute(String[] result) {
	            boolean hasMoreData = true;
	            if (mIsStart) {
	                mListItems.addFirst("Added after refresh...");
	            } else {
	            
	            	if(messageList.size()%10!=0){
	                    hasMoreData = false;
	            	}
	            }
	            
	            mPullListView.onPullDownRefreshComplete();
	            mPullListView.onPullUpRefreshComplete();
	            mPullListView.setHasMoreData(hasMoreData);
	            setLastUpdateTime();           
	      
	            try{
	            	
	            	for(int i=0;i<now_add.size();i++){
	            	ImageView imageView = (ImageView) mListView.findViewWithTag(i+((now_page-1)*10-1));  
	 		        String url = "";
	 				url =now_add.get(i);
	 				
	 			//	mImageLoader2.loadImage(url, adapter, imageView);
	            	}
	            	
	            adapter.gx(messageList.size(), studentlistActivity.this,messageList,now_add);
	            adapter.notifyDataSetChanged();    
	         //   mPullListView.setLastItem(messageList.size());
	                 //这句话很重要，加载完毕后是否滚动靠他
	           // mListView.setSelection(messageList.size()-21 );//设置listview的当前位置，如果不设
	           // mListView.getdi
	           
	            mListView.scrollTo(BaseActivity.now_x,BaseActivity.now_y);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            super.onPostExecute(result);
	        }
	    }
	    
	    
	    
	 
	  
	    private void setLastUpdateTime() {
	        String text = formatDateTime(System.currentTimeMillis());
	        mPullListView.setLastUpdatedLabel(text);
	    }

	    private String formatDateTime(long time) {
	        if (0 == time) {
	            return "";
	        }
	        
	        return mDateFormat.format(new Date(time));
	    }
	    
	    public static final String[] mStrings = {
	        "Abbaye de Belloc", "Abbaye du Mont des Cats"
	    };
	    
	    
	    
	    
	    
		private Handler mHandler;
	    private List<Message_student> messageList;
		private List<Message_student> messageTemp=new ArrayList<Message_student>();
		MyAdapter_student adapter;
		private int start = 0;
		private static int refreshCnt = 0;
		ArrayList<HashMap<String, Object>> arraylist=new ArrayList<HashMap<String,Object>>();
		boolean isSX=true;
	    SharedPreferences userMessage  ;
	    String nextUrl="";
	    String type="cx";
		static boolean isExit=false;
	    
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
		    int now_page=0;
		    
		    ArrayList<String> now_add=new ArrayList<String>();
		    
   private void handle_getList() {
	
			if(!isSX){
				 isSX=true;
				    
				    try{
				messageList=new ArrayList<Message_student>();
			
				now_add.clear();
				  
			    Log.v("学生列表数据：","k:"+BaseActivity.now_studentlist);
	   		        JSONArray p2=new JSONArray(BaseActivity.now_studentlist);	
		               JSONObject p3;
		               for(int i=0;i<p2.length();i++){
		              	 p3=p2.getJSONObject(i);
		               
		              	 String st=p3.getString("student");
		              	 JSONObject p4=new JSONObject(st);
		              	 
		              	 
		              	 Message_student message=new  Message_student();
		              	 message.setId(p4.getString("client_token"));
		            	 message.setname(p4.getString("username"));
		            	 message.setUrl(p4.getString("avatar_url"));
		            	 
						 now_add.add(p4.getString("avatar_url"));
						 messageList.add(message);
		              	 
        
		               }
		               
	   		       
				    }catch(Exception e){
				    	e.printStackTrace();
				    	
				    	
				    }
					
				
				 
			}else{
			 /*
				now_add.clear();
				now_page=now_page+1;
				try{
					   String a="";
			            SoapObject request =new SoapObject(Chuli.NAMESPACE, "RGWarnInfo"); 
			            request.addProperty("DeviceID",syActivity.now_deviceid); 
				        request.addProperty("UserID",syActivity.now_userid);
				        request.addProperty("Status",now_state);
				        request.addProperty("PageIndex",now_page);
				        request.addProperty("PageSize","10");

				        request.addProperty("Stime",stime);
				        request.addProperty("Etime",etime);

				        
				        Log.v("status2",now_state);
				        Log.v("nowpage2",""+now_page);
				        
				        a=Chuli.getWebServiec(request, "RGWarnInfo");
			            Log.v("告警列表返回",a);

			   		        JSONObject p=new JSONObject(a);
			   		        String b=p.getString("arr");
			   		        JSONArray p2=new JSONArray(b);	
				               JSONObject p3;
				               for(int i=0;i<p2.length();i++){
				              	 p3=p2.getJSONObject(i);
				               
				              	 Message_student message=new  Message_student();
				              	 message.setid(p3.getString("ExceptionID"));
				            	 message.setTitle(p3.getString("NotificationType"));
								 message.setMsg(p3.getString("Message"));
								 message.settime(p3.getString("Created"));
								 
								 if(now_state.equals("1")){
								     message.setcheck(true);
								 }else{
									 message.setcheck(false);
								 }
								 
								 now_add.add("");
								 messageList.add(message);
				              	 
		        
				               }
		               
	            
			    }catch(Exception e){
			    	e.printStackTrace();
			    }
				
			 */
			} 
			
			try{
			for(int i=0;i<messageList.size();i++){
				 Message_student message=messageList.get(i);
				messageTemp.add(message);
				HashMap< String, Object> hashmap=new HashMap<String, Object>();
				hashmap.put("message",message.gettime()); 
				hashmap.put("time", message.getname());
				hashmap.put("imgitem", R.drawable.cxmr);
				arraylist.add(hashmap);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			 
	
  	
	
			
	}

	    /***************************************列表内容*******************************************/

     ProgressDialog pg;
	 String dl_msg;
	 
	 
	 		
	 private void fetchConversationWithClientIds(List<String> clientIds, final ConversationType type, final
   		  AVIMConversationCreatedCallback
   		      callback) {
   		  //  final AVIMClient imClient = Application.getIMClient();
   			  final AVIMClient imClient =MyApplication.getIMClient();
   		    final List<String> queryClientIds = new ArrayList<String>();
   		    queryClientIds.addAll(clientIds);
   		    if (!clientIds.contains(imClient.getClientId())) {
   		      queryClientIds.add(imClient.getClientId());
   		    }
   		    AVIMConversationQuery query = imClient.getQuery();
   		    query.whereEqualTo(Conversation.ATTRIBUTE_MORE + ".type", type.getValue());
   		    query.whereContainsAll(Conversation.COLUMN_MEMBERS, queryClientIds);
   		    query.findInBackground(new AVIMConversationQueryCallback() {
   		      @Override
   		      public void done(List<AVIMConversation> list, AVException e) {
   		        if (e != null) {
   		          callback.done(null, e);
   		        } else {
   		          if (list == null || list.size() == 0) {
   		            Map<String, Object> attributes = new HashMap<String, Object>();
   		            attributes.put(ConversationType.KEY_ATTRIBUTE_TYPE, type.getValue());
   		            imClient.createConversation(queryClientIds, attributes, callback);
   		          } else {
   		            callback.done(list.get(0), null);
   		          }
   		        }
   		      }
   		    });
   		  }
   protected boolean filterException(Exception e) {
       if (e != null) {
         e.printStackTrace();
         toast(e.getMessage());
         return false;
       } else {
         return true;
       }
     }

     protected void toast(String str) {
       Toast.makeText(studentlistActivity.this, str, Toast.LENGTH_SHORT).show();
     }
	 		
 
      
}