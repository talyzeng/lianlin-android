package com.cmcc.attendance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import leadcloud.ConversationType;
import leadcloud.MessageAdapter_qun;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.gps.MyApplication;
import com.kj.list_xx;
import com.lianlin.R;

@SuppressLint("NewApi")
public class chatqunActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {

	 private static final String EXTRA_CONVERSATION_ID = "conversation_id";
	  private static final String TAG = chatqunActivity.class.getSimpleName();
	  static final int PAGE_SIZE = 10;

	  private AVIMConversation conversation;
	  MessageAdapter_qun adapter;

	  private ListView listView;
	  private ChatHandler handler;

	  private AtomicBoolean isLoadingMessages = new AtomicBoolean(false);
	
	
	AVIMMessage message;
	public static String title;
	ImageView btn_return;
	Button btn_send;
	TextView text_title;
    EditText ed_send;
	

		  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
    
        ed_send=(EditText)findViewById(R.id.chat_ed_send);
        
  	    text_title=(TextView)findViewById(R.id.chat_text_title);
        text_title.setText(title);
        
      
        btn_return=(ImageView)findViewById(R.id.chat_btn_return);
        btn_return.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        });
        
        btn_send=(Button)findViewById(R.id.chat_btn_send);
        btn_send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(ed_send.getText().toString()!=""){
				sendText();
				}
				
			}
        	
        });
        
        
        listView = (ListView) findViewById(R.id.listview);
        adapter = new MessageAdapter_qun(chatqunActivity.this, MyApplication.getClientIdFromPre(),title);
        listView.setOnScrollListener(this);
        listView.setAdapter(adapter);

        listView.setDivider(null);
        // get argument
        final String conversationId = getIntent().getStringExtra(EXTRA_CONVERSATION_ID);
       
        
        //Log.v( "当前conversationId:" , conversationId);

        // register callback
        handler = new ChatHandler(adapter);
        MessageHandler.setActivityMessageHandler(handler);

        conversation = MyApplication.getIMClient().getConversation(conversationId);

       // findViewById(R.id.send).setOnClickListener(this);

        loadMessagesWhenInit();
     
	}
	
	
	/* public void handle_send() {
		
		 Log.v("有发送","1");
	      	 Thread t3 = new Thread() {
	      	 @Override
	      	public void run() {
	      		    
	             try{
	            	message = new AVIMMessage();
	                 message.setContent("hello!");
	                 conversation.sendMessage(message, new AVIMConversationCallback() {
	                   @Override
	                   public void done(AVException e) {
	                     if (null != e) {
	                       // 出错了。。。
	                       e.printStackTrace();
	                     } else {
	                       Log.v("发送成功","msgId="+message.getMessageId());
	                     }
	                   }
	                 });
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	             
	      	
	      	 }
	      	 };
	      	 t3.start();
	      	 }*/
	  	
	 private List<AVIMTextMessage> filterMessages(List<AVIMMessage> messages) {
		    List<AVIMTextMessage> typedMessages = new ArrayList<AVIMTextMessage>();
		    for (AVIMMessage message : messages) {
		      if (message instanceof AVIMTextMessage) {
		        typedMessages.add((AVIMTextMessage) message);
		      }
		    }
		    return typedMessages;
		  }

		  private void loadMessagesWhenInit() {
		    if (isLoadingMessages.get()) {
		      return;
		    }
		    isLoadingMessages.set(true);
		    conversation.queryMessages(PAGE_SIZE, new AVIMMessagesQueryCallback() {
		      @Override
		      public void done(List<AVIMMessage> messages, AVException e) {
		        if (filterException(e)) {
		        	
		        	Log.v("历史消息1",messages.toString());
		        	
		          List<AVIMTextMessage> typedMessages = filterMessages(messages);
		          
		          for(int i=0;i<typedMessages.size();i++){
		        	  Log.v("历史输出:",typedMessages.get(i).getContent());
		          }
		          
		          adapter.setMessageList(typedMessages);
		          adapter.notifyDataSetChanged();
		          scrollToLast();
		        }
		        isLoadingMessages.set(false);
		      }
		    });
		  }

		  private void loadOldMessages() {
			  try{
		    if (isLoadingMessages.get()|| adapter.getMessageList().size() < PAGE_SIZE) {
		      return;
		    } else {
		      isLoadingMessages.set(true);
		      AVIMTypedMessage firstMsg = adapter.getMessageList().get(0);
		      long time = firstMsg.getTimestamp();
		      conversation.queryMessages(null, time, PAGE_SIZE, new AVIMMessagesQueryCallback() {
		        @Override
		        public void done(List<AVIMMessage> list, AVException e) {
		          if (filterException(e)) {
		            List<AVIMTextMessage> typedMessages = filterMessages(list);
		            if (typedMessages.size() == 0) {
		              toast("失败！");
		            } else {
		            	Log.v("历史消息2",typedMessages.toString());
			        	
		              List<AVIMTextMessage> newMessages = new ArrayList<AVIMTextMessage>();
		              newMessages.addAll(typedMessages);
		              newMessages.addAll(adapter.getMessageList());
		              adapter.setMessageList(newMessages);
		              adapter.notifyDataSetChanged();
		              listView.setSelection(typedMessages.size() - 1);
		            }
		          }
		          isLoadingMessages.set(false);
		        }
		      });
		    }
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		  }

		  private void scrollToLast() {
		    listView.smoothScrollToPosition(listView.getCount() - 1);
		  }

		  @Override
		  protected void onDestroy() {
		    super.onDestroy();
		    AVIMMessageManager.unregisterMessageHandler(AVIMTypedMessage.class, handler);
		  }

		  public static void startActivity(Context context, String conversationId,String who) {
			  //Log.v("标题	","k:"+who);
			chatqunActivity.title=who;
		    Intent intent = new Intent(context, chatqunActivity.class);
		    intent.putExtra(EXTRA_CONVERSATION_ID, conversationId);
		    context.startActivity(intent);
		  }

		

		  public void sendText() {
		    final AVIMTextMessage message = new AVIMTextMessage();
		   
		    message.setText(ed_send.getText().toString());
		    //message.setContent(ed_send.getText().toString());
		    //conversation.setAttribute("name", BaseActivity.now_myname);//会话属性
		    //conversation.setAttribute("avatar_url", BaseActivity.now_userimgheadurl);
		    //conversation.setAttribute("avatar_url", BaseActivity.bitmapavatal);//会话属性
		    Map<String, Object> attributesMessage = new HashMap<String, Object>();
		    attributesMessage.put("name", BaseActivity.now_myname);
		    //attributesMessage.put("avatar_url", BaseActivity.bitmapavatal);
		    attributesMessage.put("avatar_url", BaseActivity.now_userimgheadurl);
		    message.setAttrs(attributesMessage);
		    
		    try{
		    //conversation.sendMessage(message,AVIMConversation.RECEIPT_MESSAGE_FLAG,new AVIMConversationCallback() {
		    	conversation.sendMessage(message,new AVIMConversationCallback() {
			      @Override
			      public void done(AVException e) {
			        if (null != e) {
			          e.printStackTrace();
			        } else {
			          adapter.addMessage(message);
			          finishSend();
			        }
			      }
			    });
		    }
		    catch(Exception ee){
		    	Log.v("发送消息  错误  ", ee.toString());
		    }
		  }

		  public void finishSend() {

			xxFragement.setQun("我："+ed_send.getText().toString());
		    ed_send.setText(null);
		    scrollToLast();
		  }

		  @Override
		  public void onScrollStateChanged(AbsListView view, int scrollState) {
		    if (scrollState == SCROLL_STATE_IDLE) {
		      if (view.getChildCount() > 0) {
		        View first = view.getChildAt(0);
		        if (first != null && view.getFirstVisiblePosition() == 0 && first.getTop() == 0) {
		          loadOldMessages();
		        }
		      }
		    }
		  }

		  @Override
		  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		  }

		  public class ChatHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {
		    private MessageAdapter_qun adapter;

		    public ChatHandler(MessageAdapter_qun adapter) {
		      this.adapter = adapter;
		    }

		    @Override
		    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
		      if (!(message instanceof AVIMTextMessage)) {
		        return;
		      }
		      if (conversation.getConversationId().equals(chatqunActivity.this.conversation.getConversationId())) {
		        adapter.addMessage((AVIMTextMessage) message);
		        scrollToLast();
		      }
		    }
		  }

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	
}