package com.kj;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.attendance.activity.detailActivity;
import com.cmcc.attendance.utils.*;
import com.lianlin.R;

public class listitem_zy2 extends LinearLayout { //作业列表类
 
    private ImageView img;
    private TextView  text_id; 
    private TextView  text_title1,text_title2,text_state,text_type;
    Button btn_cklj;
    RelativeLayout r1,r2;
    public static  String url="";
	public static  String contentUrl="";
	public static String workType="";
   
    LinearLayout l1; 
    
    public listitem_zy2(Context context) { 
        super(context); 
       
    } 
    public listitem_zy2(final Context context, AttributeSet attrs) { 
        super(context, attrs); 
        
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.listitem_zy2, this); 
 
        img=(ImageView)findViewById(R.id.listitem_zy2_img);
        
        text_title1=(TextView)findViewById(R.id.listitem_zy2_text_title1);
        text_title2=(TextView)findViewById(R.id.listitem_zy2_text_title2);
        text_state=(TextView)findViewById(R.id.listitem_zy2_text_state);
        text_id=(TextView)findViewById(R.id.listitem_zy2_text_id);
        
        r1=(RelativeLayout)findViewById(R.id.listitem_zy2_r1);
        r2=(RelativeLayout)findViewById(R.id.listitem_zy2_r2);
        
        l1=(LinearLayout)findViewById(R.id.listitem_zy2_l);//总体view接口
        
        
        l1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				/*
				 * 
				 * 在线做作业部分
				 * 
				 * 
				 * */
				if(url!=""&&contentUrl!="")
				{
					detailActivity.toptitle=text_title1.getText().toString();
					
					Intent it=new Intent(context,detailActivity.class);
					
					 Bundle bundle=new Bundle();
					    bundle.putString("url", url);
					    bundle.putString("contentUrl", contentUrl);
					    bundle.putString("workType", workType);
					    it.putExtra("bd", bundle);
					    //it.putExtras(bundle);       // it.putExtra(“test”, "shuju”);
					
					context.startActivity(it);				
					//在线做作业部分
					//ToastUtil.showToast(getContext(),"暂时不支持在手机上完成该类型的作业！", Toast.LENGTH_SHORT);
					//Toast.makeText(context, "暂时不支持在手机上完成该类型的作业！", Toast.LENGTH_SHORT);
					//Toast.makeText(context, "暂时不支持在手机上完成该类型的作业！", Toast.LENGTH_SHORT).show();
				}else{
					//没有作业内容
					Toast.makeText(context, "没有作业内容！", Toast.LENGTH_SHORT).show();
				}
		
			}
		});
        
        
    }
    
    public void setR1() {   //设置已完成图标
      r1.setVisibility(View.VISIBLE);
      r2.setVisibility(View.INVISIBLE);
      
    }
    
    public void setR2() {   //设置未完成图标
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.VISIBLE);
        
      }
    
    
    public void setIMG(int res) {   
        //img.setBackgroundResource(res);
    	img.setImageResource(res);
    }
    public void setTextTitle1(String text) {   //设置作业标题
        //text_title1.setText(text);  
    	if(text.length()>18){
    		text_title1.setText(String.valueOf(text.toCharArray(), 0, 18) + "...");
    	}else{
    		text_title1.setText(text);
    	}
    }   
    public void setTextTitle2(String text) {   //设置作业内容标题2
        //text_title2.setText(text);   
    	if(text.length()>18){
    		text_title2.setText(String.valueOf(text.toCharArray(), 0, 18) + "...");
    	}else{
    		text_title2.setText(text);
    	}
    }  
    public void setTextState(String text) {   
        text_state.setText(text);   
    }  
    public void setTextID(String text) {   
        text_id.setText(text);      
    } 
    public String getTextID(){
    	return text_id.getText().toString();
    }
    
		 	
    
   
    /*

private void sendMsg(int flag)
{
    Message msg = new Message();
    msg.what = flag;
    syActivity.handler_ui.sendMessage(msg);
}  


public void setChose(){
	text_title.setTextColor(0xffd83456);
	imgchose.setVisibility(View.VISIBLE);
}
public void setUnChose(){
	text_title.setTextColor(0xffcbcbcb);
	imgchose.setVisibility(View.INVISIBLE);
}
*/
} 

