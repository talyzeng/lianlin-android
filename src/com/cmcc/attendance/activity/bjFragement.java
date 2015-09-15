package com.cmcc.attendance.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.gps.MyApplication;
import com.lianlin.R;
import com.plistview.Message_student;
import com.plistview.MyAdapter_student;
import com.plistview.PullToRefreshBase;
import com.plistview.PullToRefreshBase.OnRefreshListener;
import com.plistview.PullToRefreshListView;

public class bjFragement extends BaseFragement {
	
	
	TextView text_class,text_school,text_bzr;
	LinearLayout l_xslb,l_gglb,l_qun;  //学生列表  公告列表  群聊列表
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		rootView = inflater.inflate(R.layout.fragement_bj, container, false);
		setContentView(rootView);
	
	    text_class=(TextView)rootView.findViewById(R.id.bj_text_class);
	    text_school=(TextView)rootView.findViewById(R.id.bj_text_school);
		text_bzr=(TextView)rootView.findViewById(R.id.bj_text_bzr);
		
		try{
		text_class.setText(BaseActivity.now_classname);
		text_school.setText(BaseActivity.now_schoolname);
		text_bzr.setText("班主任："+BaseActivity.now_bzrname);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		l_qun=(LinearLayout)rootView.findViewById(R.id.bj_l_qun);
		l_qun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				List<String> list_people = new ArrayList<String>();
				try{
				
				  JSONArray p2=new JSONArray(BaseActivity.now_studentlist);	
	               JSONObject p3;
	               for(int i=0;i<p2.length();i++){
	              	 p3=p2.getJSONObject(i);
	               
	              	 String st=p3.getString("student");
	              	 JSONObject p4=new JSONObject(st);
	              	 list_people.add(p4.getString("client_token"));
	               }
	               
  		       
			    }catch(Exception e){
			    	e.printStackTrace();			    	
			    	
			    }
				//班级聊天
				Log.v("当前班级人数：","k:"+list_people.size());
				chat_qun(BaseActivity.now_clientToken,list_people, ConversationType.Group, new
			              AVIMConversationCreatedCallback
		                  () {
		                @Override
		                public void done(AVIMConversation conversation, AVException e) {
		                  if (filterException(e)) {		                	   
		                	  // chatqunActivity.startActivity(rootView.getContext(), conversation.getConversationId(),text_class.getText().toString());
		                	chatqunActivity.startActivity(rootView.getContext(), BaseActivity.now_channelid,text_class.getText().toString());
			                	    
		                  }
		                }
		              });
			
			}
		});
		
		
		l_xslb=(LinearLayout)rootView.findViewById(R.id.bj_l_xslb);
		
		/*展示学生列表*/
		l_xslb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it=new Intent(rootView.getContext(),studentlistActivity.class);
				startActivity(it);
			}
		});
		
		l_gglb=(LinearLayout)rootView.findViewById(R.id.bj_l_gglb);
		
		
		l_gglb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*Intent it=new Intent(rootView.getContext(),studentlistActivity.class);
				startActivity(it);*/
			}
		});
		
        /***************************************列表部分*************************************/
        mPullListView = new PullToRefreshListView(rootView.getContext());
        list1=(LinearLayout)rootView.findViewById(R.id.bj_list1);
        LinearLayout.LayoutParams prarms = new LinearLayout.LayoutParams(android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT,android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT);
   //设置LIST的边距离
        //  prarms.setMargins(10, 10, 10, 10);
        mPullListView.setLayoutParams(prarms);
        mPullListView.setDividerDrawable(null);  
        

        list1.addView(mPullListView);
        
        LinearLayout.LayoutParams params1 = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);         
        params1.setMargins(0, 0, 0, 0);
        list1.setLayoutParams(params1);
        
        
        mPullListView.setPullLoadEnabled(false);
        mPullListView.setScrollLoadEnabled(true);
    
        mCurIndex = mLoadDataCount;
        mListItems = new LinkedList<String>();
        mListView = mPullListView.getRefreshableView();
        //屏蔽一对一单聊
        /**/
        //屏蔽一对一单聊
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {              
           /*屏蔽教师单聊
            	  fetchConversationWithClientIds(Arrays.asList(messageList.get(position).getId()), ConversationType.OneToOne, new
			              AVIMConversationCreatedCallback
			                  () {
			                @Override
			                public void done(AVIMConversation conversation, AVException e) {
			                  if (filterException(e)) {
			                	   
			                	   chatActivity.startActivity(rootView.getContext(), conversation.getConversationId(),messageList.get(position).getname());
			                	 
			                  }
			                }
			              });	*/		   
            }               
        });
        mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsStart = true;
                
                now_page=0;
                messageList=new ArrayList<com.plistview.Message_student>();
                adapter = new MyAdapter_student(messageList.size(), rootView.getContext(),messageList,null);
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
        adapter = new MyAdapter_student(messageList.size(), rootView.getContext(),messageList,null);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
        /***************************************列表部分*************************************/
        
    	  mIsStart = true;
    	  isSX=false;
        
		return rootView;
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
            //一次性加载完毕，不需要更新并重新加载
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
            	
            adapter.gx(messageList.size(), rootView.getContext(),messageList,now_add);
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
			  
		    Log.v("学生列表数据：","k:"+BaseActivity.now_teacherlist);
   		        JSONArray p2=new JSONArray(BaseActivity.now_teacherlist);	
	               JSONObject p3;
	               for(int i=0;i<p2.length();i++){
	              	 p3=p2.getJSONObject(i);
	               
	              	 String st=p3.getString("teacher");
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
 
 
 private void chat_qun(final String channelid,List<String> clientIds, final ConversationType type, final
		  AVIMConversationCreatedCallback
		      callback) {
		  //  final AVIMClient imClient = Application.getIMClient();
			  final AVIMClient imClient =AVIMClient.getInstance(channelid);
			  
			  //modified by taly 0816
			  
			  imClient.open(new AVIMClientCallback(){
				  @Override
				  public void done(AVIMClient client, AVException e) {
				    if (null != e) {
				      // 出错了，可能是网络问题无法连接 LeanCloud 云端，请检查网络之后重试。
				      // 此时聊天服务不可用。
				      e.printStackTrace();
				      Log.v("打开聊天错误",e.toString());
				    } else {
				      // 成功登录，可以开始进行聊天了（假设为 MainActivity）。
				      //Intent intent = new Intent(currentActivity, MainActivity.class);
				      //currentActivity.startActivity(intent);
				    	//callback.done(imClient, arg1)
				    	Log.v("进入聊天",  imClient.toString());
				    };
				  }
				});
			  
			  
			/*  
		    final List<String> queryClientIds = new ArrayList<String>();
		    queryClientIds.addAll(clientIds);
		    if (!clientIds.contains(imClient.getClientId())) {
		      queryClientIds.add(imClient.getClientId());
		    }
		    AVIMConversationQuery query = imClient.getQuery();
		    //query.whereEqualTo(Conversation.ATTRIBUTE_MORE + ".type", type.getValue());
		    query.whereContainsAll(Conversation.COLUMN_MEMBERS, queryClientIds);
		    //query.whereContainsIn(key, value);//(Conversation.COLUMN_MEMBERS, queryClientIds);
		    query.findInBackground(new AVIMConversationQueryCallback() {
		      @Override
		      public void done(List<AVIMConversation> list, AVException e) {
		        if (e != null) {
		          callback.done(null, e);
		        } else {
		          if (list == null || list.size() == 0) {//没有找到对应的群聊
		            //Map<String, Object> attributes = new HashMap<String, Object>();
		            //attributes.put(ConversationType.KEY_ATTRIBUTE_TYPE, type.getValue());
		            
		            //创建会话，有问题
		            //imClient.createConversation(queryClientIds, attributes, callback);
		        	toast("没有找到班级聊天组！！");
		            
		            //修改为获取会话  modified by  taly
		            //imClient.
		            //imClient.getConversation(channelid);
		          } else {
		            callback.done(list.get(0), null);
		          }
		        }
		      }
		    });
		    */
		    //end of modified by taly 0816
		 
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
   Toast.makeText(rootView.getContext(), str, Toast.LENGTH_SHORT).show();
 }
 		

}
