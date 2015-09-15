package com.cmcc.attendance.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lianlin.R;
import com.huison.tools.Chuli;

public class zhuce_userActivity extends Activity {

	public static Handler handler_ui;
	
	ImageView btn_back;
	ProgressDialog pg;
	String zc_code,zc_msg;
	
	EditText ed_name,ed_pwd1,ed_pwd2,ed_nc,ed_yzm;
	Button btn_zc,btn_fsyzm;
	String now_type;
	
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
     
         
     
        btn_back=(ImageView)findViewById(R.id.zhuce_btn_return);
        btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			 finish();
				
			}
        	
        });
        
        
        btn_fsyzm=(Button)findViewById(R.id.zhuce_btn_fsyzm);
        btn_fsyzm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//handle_fsyzm();
			}
        	
        });
        
    	ed_yzm=(EditText)findViewById(R.id.zhuce_ed_yzm);
		ed_name=(EditText)findViewById(R.id.zhuce_ed_name);
		ed_pwd1=(EditText)findViewById(R.id.zhuce_ed_pwd1);
		ed_pwd2=(EditText)findViewById(R.id.zhuce_ed_pwd2);
		
		btn_zc=(Button)findViewById(R.id.zhuce_btn_zhuce);
		btn_zc.setOnClickListener(new OnClickListener(){
		

			@Override
			public void onClick(View arg0) {
				
				if(!ed_pwd1.getText().toString().equals("")&&!ed_name.getText().toString().equals("")&&!ed_pwd1.getText().toString().equals("")){ 
				
				 if(ed_pwd1.getText().toString().equals(ed_pwd2.getText().toString())){
					 //handle_getzhuce();
				 }else{
					 new AlertDialog.Builder(zhuce_userActivity.this).setTitle("提示").setMessage("两次密码输入不一致！")
	 		         	.setNegativeButton("确定",null).show(); 
				 }
					
				
			    }else{
			    	 new AlertDialog.Builder(zhuce_userActivity.this).setTitle("提示").setMessage("请先填写完整注册信息！")
	 		         	.setNegativeButton("确定",null).show(); 
			    }
			}
		});
	
	 
	    
		
		
		
	}
  
    
    
        
      
}