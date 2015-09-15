package leadcloud;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.huison.tools.Chuli;
import com.lianlin.R;

/**
 * Created by zhangxiaobo on 15/4/27.
 */
public class MessageAdapter extends BaseAdapter {
  private Context context;
  List<AVIMTypedMessage> messageList = new LinkedList<AVIMTypedMessage>();
  private String selfId;
  private String fromname;

  public MessageAdapter(Context context, String selfId,String fromname) {
    this.context = context;
    this.selfId = selfId;
    this.fromname=fromname;
  }

  public void setMessageList(List<AVIMTypedMessage> messageList) {
    this.messageList = messageList;
  }

  public List<AVIMTypedMessage> getMessageList() {
    return messageList;
  }

  @Override
  public int getCount() {
    return messageList.size();
  }

  @Override
  public Object getItem(int position) {
    return messageList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder1=null;
    ViewHolder holder2=null;
    AVIMTypedMessage message = messageList.get(position);
    String text;
    
    if (AVIMReservedMessageType.getAVIMReservedMessageType(message.getMessageType()) == AVIMReservedMessageType.TextMessageType) {
        AVIMTextMessage textMessage = (AVIMTextMessage) message;
        text = textMessage.getText();
      } else {
        text = message.getContent();
      }
    Log.v("来源：",message.getFrom()+" 自己："+selfId+" 信息："+text);
    
    if (message.getFrom().trim().equals(selfId.trim())) {
    	
    	if (convertView == null) {
    		Log.v("设置了自己1","1");
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx2, null);
            holder1 = new ViewHolder();
            holder1.message = (TextView) convertView.findViewById(R.id.list_xx2_text_content);
            holder1.sender = (TextView) convertView.findViewById(R.id.list_xx2_text_name);
            holder1.time = (TextView) convertView.findViewById(R.id.list_xx2_text_time);
            holder1.id = (TextView) convertView.findViewById(R.id.list_xx2_text_id);
            convertView.setTag(holder1);
        	
          
        } else {
        	Log.v("设置了自己2","1");
         // holder1 = (ViewHolder) convertView.getTag();
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx2, null);
            holder1 = new ViewHolder();
            holder1.message = (TextView) convertView.findViewById(R.id.list_xx2_text_content);
            holder1.sender = (TextView) convertView.findViewById(R.id.list_xx2_text_name);
            holder1.time = (TextView) convertView.findViewById(R.id.list_xx2_text_time);
            holder1.id = (TextView) convertView.findViewById(R.id.list_xx2_text_id);
            convertView.setTag(holder1);
        }
    } else {
    	
    	if (convertView == null) {
    		Log.v("设置了他人1","1");
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx, null);
            holder2 = new ViewHolder();
            holder2.message = (TextView) convertView.findViewById(R.id.list_xx_text_content);
            holder2.sender = (TextView) convertView.findViewById(R.id.list_xx_text_name);
            holder2.time = (TextView) convertView.findViewById(R.id.list_xx_text_time);
            holder2.id = (TextView) convertView.findViewById(R.id.list_xx_text_id);
            convertView.setTag(holder2);
        	
          
        } else {
        	Log.v("设置了他人2","1");
          //holder2 = (ViewHolder) convertView.getTag();
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx, null);
            holder2 = new ViewHolder();
            holder2.message = (TextView) convertView.findViewById(R.id.list_xx_text_content);
            holder2.sender = (TextView) convertView.findViewById(R.id.list_xx_text_name);
            holder2.time = (TextView) convertView.findViewById(R.id.list_xx_text_time);
            holder2.id = (TextView) convertView.findViewById(R.id.list_xx_text_id);
            convertView.setTag(holder2);
        }
    }
    
   
    if (AVIMReservedMessageType.getAVIMReservedMessageType(message.getMessageType()) == AVIMReservedMessageType.TextMessageType) {
        AVIMTextMessage textMessage = (AVIMTextMessage) message;
      
        
        try{
                 text = textMessage.getText().substring(0,textMessage.getText().indexOf("NAME"));
        }catch(Exception e){
        	 text = textMessage.getText();
        }
      } else {
    		  try{
        text = message.getContent().substring(0,message.getContent().indexOf("NAME"));
       
    	  }catch(Exception e){
    		  text = message.getContent();
    		  
         }
      }
    
    
   
    
    if (message.getFrom().equals(selfId)) {
  
      holder1.message.setText(text);
      holder1.sender.setText("我");
      holder1.time.setText(""+Chuli.getNormalTime(message.getTimestamp()));
      
      holder1.id.setText(""+message.getMessageId());
   
    } else {
    	
    	      holder2.message.setText(text);
    	      holder2.sender.setText(fromname);
    	      holder2.time.setText(""+Chuli.getNormalTime(message.getTimestamp()));
    	      
    	      holder2.id.setText(""+message.getMessageId());
    }
    return convertView;
  }

  public void addMessage(AVIMTextMessage message) {
    messageList.add(message);
    notifyDataSetChanged();
  }

  private class ViewHolder {
    TextView message;
    TextView sender;
    TextView time;
    TextView id;
  }
}
