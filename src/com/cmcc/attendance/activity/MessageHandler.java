package com.cmcc.attendance.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.cmcc.attendance.activity.xxFragement;
import com.gps.MyApplication;
import com.huison.tools.Chuli;

/**
 * Created by zhangxiaobo on 15/4/20.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {
  private static AVIMTypedMessageHandler<AVIMTypedMessage> activityMessageHandler;
  private Context context;
  private String TAG = MessageHandler.this.getClass().getSimpleName();

  public MessageHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    Log.v("消息已到达对方" ,message.getContent());
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    if (client.getClientId().equals(MyApplication.getClientIdFromPre())) {
      if (activityMessageHandler != null) {
        // 正在聊天时，分发消息，刷新界面
        activityMessageHandler.onMessage(message, conversation, client);
      } else {
        // 没有打开聊天界面，这里简单地 Toast 一下。实际中可以刷新最近消息页面，增加小红点
        if (message instanceof AVIMTextMessage) {
          AVIMTextMessage textMessage = (AVIMTextMessage) message;
          
          Log.v("收到新消息" ,"新消息 " + message.getFrom() + " : " + textMessage.getText());
          //Toast.makeText(context, "新消息 " + message.getFrom() + " : " + textMessage.getText(), Toast.LENGTH_SHORT).show();
          
      /*    String msgContent1 = message.getContent();
   	   Log.v(conversation.getConversationId() + " 收到一条新消息：" , message.getFrom()+":"+msgContent1);*/
   	   
   	   
   
        }
        
      }
    } else {
      client.close(null);
    }
    
	  try{
		    String msgContent = message.getContent();
		    Log.v("msgContent",msgContent);
		    
		    if(conversation.getAttribute("name").toString().equals("")){
		  //  if(msgContent.indexOf("name")==-1){
		    	
		    	//群消息	
		    	JSONObject p=new JSONObject(msgContent);
		    	
		    	String ct=p.getString("_lctext");
		    	Log.v("群总内容：",ct);
		    	String content=ct;
		    
		    	
		    	String msgFromName=conversation.getAttribute("name").toString();
		    	
		    	
		    	String msgFromClass="六年级一班";
		    	Log.v("当前MSGFROMNAME",msgFromName);
		  
		    	xxFragement.setQun( msgFromName+":"+content);
		    	
		    }else{
		     //个人消息
		    	JSONObject p=new JSONObject(msgContent);
		    	String content=p.getString("_lctext");
		    	String msgfromname=conversation.getAttribute("name").toString();
		    	
		    	Boolean isCF=false;
		    	int now_cf_i=0;
		    	for(int i=0;i<xxFragement.lt_tj.size();i++){
		    		Log.v("对比",xxFragement.lt_tj.get(i).getTextId()+":"+message.getFrom());
		    		if(xxFragement.lt_tj.get(i).getTextName().equals(msgfromname)){
		    			isCF=true;
		    			now_cf_i=i;
		    			break;
		    		}
		    	}
		    	
		    	if(isCF){
		    		xxFragement.setListHad(now_cf_i,content, ""+message.getReceiptTimestamp());
		    	    
		    	}else{
		    	   xxFragement.setList(message.getFrom(),message.getConversationId(), msgfromname, content, ""+message.getReceiptTimestamp(),"one");
		    	}
		    	   Log.v(conversation.getConversationId() + " 收到个人新消息：" , message.getFrom()+":"+conversation.getAttribute("name"));
		    	
		    }   
		    	   
		    	   
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
  
  }

  public static AVIMTypedMessageHandler<AVIMTypedMessage> getActivityMessageHandler() {
    return activityMessageHandler;
  }

  public static void setActivityMessageHandler(AVIMTypedMessageHandler<AVIMTypedMessage> activityMessageHandler) {
    MessageHandler.activityMessageHandler = activityMessageHandler;
  }
  
  
  
  
  
  
}
