package com.plistview;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianlin.R;



public class list_textview extends LinearLayout{ 
     

     private TextView  text_text1,text_id;
     LinearLayout l1;
     
 
     
    public list_textview(Context context) { 
        super(context); 
       
    } 
    public list_textview(final Context context, AttributeSet attrs) { 
        super(context, attrs); 
        
        // TODO Auto-generated constructor stub 
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.list_textview, this); 

        l1=(LinearLayout)findViewById(R.id.list_textview_l1);
      /*  l1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("有触发","kb2");
				AttendanceApplyActivity.handleClick(((list_textview)v).getText1().toString());
				AttendanceApplyActivity.now_ctype=text_id.getText().toString();
			}
		});*/
        text_id=(TextView)findViewById(R.id.list_textview_text_id);  
        text_text1=(TextView)findViewById(R.id.list_textview_text_text1);  
        text_text1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("有触发","kb2");
				/*AttendanceApplyActivity.handleClick(((TextView)v).getText().toString());
				AttendanceApplyActivity.now_ctype=text_id.getText().toString();*/
			}
		});
        
        
        
    
    }
 
    public void setTextId(String a){
    	text_id.setText(a);
    }
    public void setTextText1(String a){
    	text_text1.setText(a);
    }
   
    public String getText1(){
    	return text_text1.toString();
    }
    
    public String getTextId(){
    	return text_id.getText().toString();
    }
     
   
} 

