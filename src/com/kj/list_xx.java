package com.kj;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianlin.R;



public class list_xx extends LinearLayout { 
     
	 Boolean isShow;
     private TextView  text_name,text_content,text_time,text_id; 
   
     LinearLayout l1,list1;
     
    public list_xx(Context context) { 
        super(context); 
       
    } 
    public list_xx(final Context context, AttributeSet attrs) { 
        super(context, attrs); 
        
        // TODO Auto-generated constructor stub 
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.list_xx, this); 
    
     
        text_id=(TextView)findViewById(R.id.list_xx_text_id);  
        text_content=(TextView)findViewById(R.id.list_xx_text_content);  
        text_time=(TextView)findViewById(R.id.list_xx_text_time);  
        text_name=(TextView)findViewById(R.id.list_xx_text_name);  
        
        
      
    }
        
       
   
    /**  
     * ������ʾ������  
     */   
  
    public void setTextID(String a){
    	text_id.setText(a);
    }
    
    public void setTextContent(String a){
    	text_content.setText(a);
    }
 
    public void setTextName(String a){
    	text_name.setText(a);
    }
    public void setTextTime(String a){
    	text_time.setText(a);
    }
} 

