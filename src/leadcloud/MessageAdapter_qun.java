package leadcloud;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.cmcc.attendance.activity.BaseActivity;
import com.huison.tools.Chuli;
import com.huison.tools.ImageTools;
import com.lianlin.R;
import com.plistview.Message_student;

/**
 * Created by zhangxiaobo on 15/4/27.
 */
public class MessageAdapter_qun extends BaseAdapter {
  private Context context;
  //List<AVIMTypedMessage> messageList = new LinkedList<AVIMTypedMessage>();
  List<AVIMTextMessage> messageList = new LinkedList<AVIMTextMessage>();
  private String selfId;
  private String fromname;
  

  public MessageAdapter_qun(Context context, String selfId,String fromname) {
    this.context = context;
    this.selfId = selfId;
    this.fromname=fromname;
  }

  public void setMessageList(List<AVIMTextMessage> messageList) {
    this.messageList = messageList;
  }

  public List<AVIMTextMessage> getMessageList() {
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
    //AVIMTypedMessage message = messageList.get(position);
    AVIMTextMessage message = (AVIMTextMessage) messageList.get(position);
    String text;
   /*
    Thread t3 = new Thread() {
     	 @Override
     	public void run() {  
        	try{
        		bitmapavatal = ImageTools.GetLocalOrNetBitmap(BaseActivity.now_userimgheadurl);
        	}
        	catch(Exception e)
        	{
        		Log.d("image upload ", e.toString());
        	}
        } 
    };
    t3.start(); 
    */
    
    
    if (AVIMReservedMessageType.getAVIMReservedMessageType(message.getMessageType()) == AVIMReservedMessageType.TextMessageType) {
        AVIMTextMessage textMessage = (AVIMTextMessage) message;
        text = textMessage.getText();
      } else {
        text = message.getContent();
      }
   // Log.v("来源：",message.getFrom()+" 自己："+selfId+" 信息："+text);
    
    if (message.getFrom().trim().equals(selfId.trim())) {
    	
    	if (convertView == null) {
    		Log.v("设置了自己1","1");
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx2, null);
            holder1 = new ViewHolder();
            holder1.messageText = (TextView) convertView.findViewById(R.id.list_xx2_text_content);
            holder1.sender = (TextView) convertView.findViewById(R.id.list_xx2_text_name);
            holder1.time = (TextView) convertView.findViewById(R.id.list_xx2_text_time);
            holder1.id = (TextView) convertView.findViewById(R.id.list_xx2_text_id);
            holder1.avatalurl = (ImageView) convertView.findViewById(R.id.list_xx2_image);
            convertView.setTag(holder1);
        	
          
        } else {
        	Log.v("设置了自己2","1");
         // holder1 = (ViewHolder) convertView.getTag();
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx2, null);
            holder1 = new ViewHolder();
            holder1.messageText = (TextView) convertView.findViewById(R.id.list_xx2_text_content);
            holder1.sender = (TextView) convertView.findViewById(R.id.list_xx2_text_name);
            holder1.time = (TextView) convertView.findViewById(R.id.list_xx2_text_time);
            holder1.id = (TextView) convertView.findViewById(R.id.list_xx2_text_id);
          holder1.avatalurl = (ImageView) convertView.findViewById(R.id.list_xx2_image);
            convertView.setTag(holder1);
        }
    } else {
    	
    	if (convertView == null) {
    		Log.v("设置了他人1","1");
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx, null);
            holder2 = new ViewHolder();
            holder2.messageText = (TextView) convertView.findViewById(R.id.list_xx_text_content);
            holder2.sender = (TextView) convertView.findViewById(R.id.list_xx_text_name);
            holder2.time = (TextView) convertView.findViewById(R.id.list_xx_text_time);
            holder2.id = (TextView) convertView.findViewById(R.id.list_xx_text_id);
          holder2.avatalurl = (ImageView) convertView.findViewById(R.id.list_xx_image);
            convertView.setTag(holder2);
        	
          
        } else {
        	Log.v("设置了他人2","1");
          //holder2 = (ViewHolder) convertView.getTag();
        	convertView = LayoutInflater.from(context).inflate(R.layout.list_xx, null);
            holder2 = new ViewHolder();
            holder2.messageText = (TextView) convertView.findViewById(R.id.list_xx_text_content);
            holder2.sender = (TextView) convertView.findViewById(R.id.list_xx_text_name);
            holder2.time = (TextView) convertView.findViewById(R.id.list_xx_text_time);
            holder2.id = (TextView) convertView.findViewById(R.id.list_xx_text_id);
            holder2.avatalurl = (ImageView) convertView.findViewById(R.id.list_xx_image);
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
  
      holder1.messageText.setText(text);
      holder1.sender.setText("我");
      holder1.time.setText(""+Chuli.getNormalTime(message.getTimestamp()));   
      try{
	      if(BaseActivity.bitmapavatal!=null){
	    	  //Bitmap tempAvatal = ImageTools.GetLocalOrNetBitmap(BaseActivity.now_userimgheadurl);
	    	  //holder1.avatalurl.setImageBitmap(tempAvatal);//.setImageResource(bitmapavatal);
	    	  holder1.avatalurl.setImageBitmap(BaseActivity.bitmapavatal);//.setImageResource(bitmapavatal);
	    	  //Bitmap bitmapAvatal = (Bitmap) message.getAttrs().get("avatal_url");
	    	  //holder1.avatalurl.setImageBitmap(bitmapAvatal);
	      }
      }
      catch(Exception ee)
      {
    	  Log.v("处理头像  ", ee.toString());
      }
      holder1.id.setText(""+message.getMessageId());
   
    } else {
    	
    	//遍历学生列表匹配学生名字  设置姓名
    	try{
    	 JSONArray p2=new JSONArray(BaseActivity.now_studentlist);	
         JSONObject p3;
         for(int i=0;i<p2.length();i++){
        	 p3=p2.getJSONObject(i);
         
        	 String st=p3.getString("student");
        	 JSONObject p4=new JSONObject(st);
        	 
        	 //Log.v("当前ID：",message.getFrom()+":"+p4.getString("client_token"));
        	 if(message.getFrom().equals(p4.getString("client_token"))){
        		 fromname=p4.getString("name");
        		 break;
        	 }
        	
        	

         }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    
    	
    	      holder2.messageText.setText(text);
    	      holder2.sender.setText(fromname);
    	      holder2.time.setText(""+Chuli.getNormalTime(message.getTimestamp()));
    	      try{
	    	      //if(BaseActivity.bitmapavatal!=null){
	    	    	  //holder2.avatalurl.setImageBitmap(BaseActivity.bitmapavatal);
	    	    	  String avatalUrl = message.getAttrs().get("avatar_url").toString();
	    	    	  Bitmap tempAvatal = ImageTools.GetLocalOrNetBitmap(avatalUrl);
	    	    	  holder2.avatalurl.setImageBitmap(tempAvatal);
	    	    	  //holder2.avatalurl.setImageBitmap();
	    	      //}
    	      }
    	      catch(Exception ee){
    	    	  Log.v("头像   ", ee.toString());
    	      }
    	      holder2.id.setText(""+message.getMessageId());
    }
    return convertView;
  }

  public void addMessage(AVIMTextMessage message) {
    messageList.add(message);
    notifyDataSetChanged();
  }

  private class ViewHolder {
	ImageView avatalurl;
    TextView messageText;
    TextView sender;
    TextView time;
    TextView id;
  }
}
