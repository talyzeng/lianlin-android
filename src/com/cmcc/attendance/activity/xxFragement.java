package com.cmcc.attendance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leadcloud.ConversationType;

import org.json.JSONArray;
import org.json.JSONObject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.kj.list_wdxx;
import com.lianlin.R;

public class xxFragement extends BaseFragement {
	private static MediaPlayer mp;	
	public static LinearLayout list1;
	 ArrayList<String> p_title=new ArrayList<String>();
	 ArrayList<String> p_content=new ArrayList<String>();
	 ArrayList<String> p_time=new ArrayList<String>();
	 ArrayList<String> p_id=new ArrayList<String>();
	 public static TextView text_class,text_school,text_bzr,text_xx_num,text_xx_time;
	 LinearLayout l_qun;
	 
	 public static  ArrayList <list_wdxx> lt_tj=new ArrayList<list_wdxx>();
	 public static Integer tj_count=0;
	 public static void setList(String cliend,String conversationid,String name,String content,String time,String type){
		 mp = MediaPlayer.create(rootView.getContext(), R.raw.ts);
		 mp.start();
		 tj_count=tj_count+1;
		 lt_tj.add(new list_wdxx(rootView.getContext(),null));  	   	    
		 lt_tj.get(tj_count-1).setTextCliendID(cliend);  
		 lt_tj.get(tj_count-1).setTextType(type);  
		 lt_tj.get(tj_count-1).setTextID(conversationid);
		 lt_tj.get(tj_count-1).setTextName(name);
		 lt_tj.get(tj_count-1).setTextTime(time);
		 lt_tj.get(tj_count-1).setTextContent(content);
		 list1.addView(lt_tj.get(tj_count-1));
	}
		  
    public static void setListHad(Integer i,String content,String time){		      
			  mp = MediaPlayer.create(rootView.getContext(), R.raw.ts);
			  mp.start();
		   	 lt_tj.get(i).setTextTime(time);
		   	 lt_tj.get(i).setTextContent(content);
	}
		  
    public static void setQun(String content){
          	  mp = MediaPlayer.create(rootView.getContext(), R.raw.ts);
			  mp.start();		   	   
  			  text_school.setText(content);
  	}
  		  
            
	@Override   
	public void onDestroy() { 
			  super.onDestroy();   
			  try{
			  mp.release();   
	}catch(Exception e){
				  e.printStackTrace();
	}
			  p_title.clear();
			  p_content.clear();
			  p_time.clear();
			  p_id.clear();
			  lt_tj.clear();
			  tj_count=0;
	} 
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragement_xx, container, false);
		setContentView(rootView);	
		list1=(LinearLayout)rootView.findViewById(R.id.xx_list1);	      
		text_school=(TextView)rootView.findViewById(R.id.xx_text_school);//学校名称
		text_class=(TextView)rootView.findViewById(R.id.xx_text_class);//班级名称
		text_xx_num=(TextView)rootView.findViewById(R.id.message_text_xxnum);//未读消息数量
		text_xx_time=(TextView)rootView.findViewById(R.id.message_text_time);//未读消息时间
		//message_text_xxnum
		try{
		text_school.setText(BaseActivity.now_schoolname);
		text_class.setText(BaseActivity.now_classname);
		text_xx_num.setText("");
		text_xx_time.setText("");
	//	text_school.setText(BaseActivity.now_schoolname);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		l_qun=(LinearLayout)rootView.findViewById(R.id.xx_l_qun);
		
		if(!BaseActivity.now_bzrname.equals("")||!BaseActivity.now_bzrname.equals(null)){			
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
					chat_qun(BaseActivity.now_clientToken,list_people, ConversationType.Group, new
				              AVIMConversationCreatedCallback
			                  () {
			                @Override
			                public void done(AVIMConversation conversation, AVException e) {
			                  if (filterException(e)) {			                	   
			                	   chatqunActivity.startActivity(rootView.getContext(), conversation.getConversationId(),text_class.getText().toString());
			                  }
			                }
			              });					
				}
			});
		}
		return rootView;
	}
	
	
	 private List<AVIMTypedMessage> filterMessages(List<AVIMMessage> messages) {
		    List<AVIMTypedMessage> typedMessages = new ArrayList<AVIMTypedMessage>();
		    for (AVIMMessage message : messages) {
		      if (message instanceof AVIMTypedMessage) {
		        typedMessages.add((AVIMTypedMessage) message);
		      }
		    }
		    return typedMessages;
		  }

	private void chat_qun(String channelid,List<String> clientIds, final ConversationType type, final
			  AVIMConversationCreatedCallback
			      callback) {
			  //  final AVIMClient imClient = Application.getIMClient();
				  final AVIMClient imClient =AVIMClient.getInstance(channelid);
				  callback.done(imClient.getConversation(BaseActivity.now_channelid), null);
			   /*
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
			          if (list == null || list.size() == 0) {//没有找到相应的群聊，就创建群聊
			           // Map<String, Object> attributes = new HashMap<String, Object>();
			            //attributes.put(ConversationType.KEY_ATTRIBUTE_TYPE, type.getValue());
			            //imClient.createConversation(queryClientIds, attributes, callback);
			        	  toast("没有找到相应的班级聊天组！！");
			          } else {
			            callback.done(list.get(0), null);
			          }
			        }
			        
			        //callback.
			        
			      }
			    });
			    */
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
