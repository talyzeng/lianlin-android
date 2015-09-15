package leadcloud;

import org.json.JSONObject;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.cmcc.attendance.activity.xxFragement;

	
	// 处理新消息到达事件
public class CustomMsgHandler extends AVIMMessageHandler {
	  @Override
	  public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
	    // 请按自己需求改写
		  Log.v("收到消息","kkk");
		  String msgContent1 = message.getContent();
		   Log.v(conversation.getConversationId() + " 收到一条新消息：" , message.getFrom()+":"+msgContent1);
		  try{
	    String msgContent = message.getContent();
	    
	    	JSONObject p=new JSONObject(msgContent);
	    	String content=p.getString("_lctext");
	    	Boolean isCF=false;
	    	int now_cf_i=0;
	    	for(int i=0;i<xxFragement.lt_tj.size();i++){
	    		Log.v("对比",xxFragement.lt_tj.get(i).getTextId()+":"+message.getFrom());
	    		if(xxFragement.lt_tj.get(i).getTextName().equals(message.getFrom())){
	    			isCF=true;
	    			now_cf_i=i;
	    			break;
	    		}
	    	}
	    	
	    	if(isCF){
	    		xxFragement.setListHad(now_cf_i,content, ""+message.getReceiptTimestamp());
	    	    
	    	}else{
	    	   xxFragement.setList(message.getFrom(),message.getConversationId(), message.getFrom(), content, ""+message.getReceiptTimestamp(),"one");
	    	}
	    	   Log.v(conversation.getConversationId() + " 收到一条新消息：" , message.getFrom()+":"+msgContent);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	 
		
	  }

	  @Override
	  public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
	    // 请按自己需求改写
	    Log.v("发往对话 " + conversation.getConversationId() + " 的消息 ", message.getMessageId() +" 已被接收");
	  }
	}
