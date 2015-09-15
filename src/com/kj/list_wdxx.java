package com.kj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import leadcloud.ConversationType;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.cmcc.attendance.activity.BaseActivity;
import com.cmcc.attendance.activity.chatActivity;
import com.cmcc.attendance.activity.chatqunActivity;
import com.gps.MyApplication;
import com.lianlin.R;



public class list_wdxx extends LinearLayout { 
     
	 Boolean isShow;
     private TextView  text_type,text_name,text_content,text_time,text_id,text_cliendid; 
     ImageView img;
     LinearLayout l1;
     Context ctx;
    public list_wdxx(Context context) { 
        super(context); 
       
    } 
    public list_wdxx(final Context context, AttributeSet attrs) { 
        super(context, attrs); 
        
        ctx=context;
        // TODO Auto-generated constructor stub 
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.list_wdxx, this); 
    
        img=(ImageView)findViewById(R.id.list_wdxx_img);
     
        text_id=(TextView)findViewById(R.id.list_wdxx_text_id);   
        text_type=(TextView)findViewById(R.id.list_wdxx_text_type);  
        text_cliendid=(TextView)findViewById(R.id.list_wdxx_text_cliendid);  
        text_content=(TextView)findViewById(R.id.list_wdxx_text_content);  
        text_time=(TextView)findViewById(R.id.list_wdxx_text_time);  
        text_name=(TextView)findViewById(R.id.list_wdxx_text_name);  
        
        l1=(LinearLayout)findViewById(R.id.list_wdxx_l1);
        l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
			      Log.v("当前对方cliendid",text_cliendid.getText().toString());
			      if(text_type.getText().toString().equals("one")){
			      
			          fetchConversationWithClientIds(Arrays.asList(text_cliendid.getText().toString()), ConversationType.OneToOne, new
			              AVIMConversationCreatedCallback
			                  () {
			                @Override
			                public void done(AVIMConversation conversation, AVException e) {
			                  if (filterException(e)) {
			                	   chatActivity.startActivity(getContext(), conversation.getConversationId(),text_name.getText().toString());
			                  
			                  }
			                }
			              });
			        
			      }else{
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
						
						chat_qun(BaseActivity.now_channelid,list_people, ConversationType.Group, new
					              AVIMConversationCreatedCallback
				                  () {
				                @Override
				                public void done(AVIMConversation conversation, AVException e) {
				                  if (filterException(e)) {
				                	   
				                	   chatqunActivity.startActivity(getContext(), conversation.getConversationId(),text_name.getText().toString());
				                  
				                  }
				                }
				              });
			      }
			}
		});
        
      
    }
        
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
    
    private void chat_qun(String channelid,List<String> clientIds, final ConversationType type, final
  		  AVIMConversationCreatedCallback
  		      callback) {
  		  //  final AVIMClient imClient = Application.getIMClient();
  			  final AVIMClient imClient =AVIMClient.getInstance(channelid);
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
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
      }
    /**  
     * ������ʾ������  
     */   
      public void setTextCliendID(String a){
      	text_cliendid.setText(a);
      }
    public void setTextID(String a){
    	text_id.setText(a);
    }
    public void setTextType(String a){
    	text_type.setText(a);
    }
    public String getTextId(){
    	return text_id.getText().toString();
    }
    public void setTextContent(String a){
    	text_content.setText(a);
    }
 
    public void setTextName(String a){
    	text_name.setText(a);
    }
    
    public String getTextName(){
    	return text_name.getText().toString();
    }
    public void setTextTime(String a){
    	text_time.setText(a);
    }
    
    
    public void setTextImg(int a){
    	img.setImageResource(a);
    }
} 

