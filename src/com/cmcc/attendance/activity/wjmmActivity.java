package com.cmcc.attendance.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lianlin.R;
import com.huison.tools.Chuli;

public class wjmmActivity extends Activity {

	Button btn_back;
	 Dialog pg;
	String zc_code,zc_msg;
	
	EditText ed_name,ed_pwd1,ed_pwd2,ed_yzm;
	
	Button btn_xg,btn_fsyzm;
	String now_type;
	RadioGroup radiogroup1;  
    RadioButton radio_sj,radio_mj;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wjmm);
     
        btn_back=(Button)findViewById(R.id.wjmm_btn_return);
        btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			 finish();
				
			}
        	
        });
        
		ed_name=(EditText)findViewById(R.id.wjmm_ed_name);
		ed_yzm=(EditText)findViewById(R.id.wjmm_ed_yzm);
		ed_pwd1=(EditText)findViewById(R.id.wjmm_ed_pwd1);
		ed_pwd2=(EditText)findViewById(R.id.wjmm_ed_pwd2);
		
		btn_xg=(Button)findViewById(R.id.wjmm_btn_xg);
		btn_xg.setOnClickListener(new OnClickListener(){
		

			@Override
			public void onClick(View arg0) {
				
				if(!ed_name.getText().toString().equals("")&&
						!ed_pwd1.getText().toString().equals("")&&
						!ed_pwd2.getText().toString().equals("")&&
						!ed_yzm.getText().toString().equals("")){
					
				if(ed_pwd1.getText().toString().equals(ed_pwd2.getText().toString())){
				//	handle_getwjmm();
					}else{
						 new AlertDialog.Builder(wjmmActivity.this).setTitle("提示").setMessage("两次输入密码不一致！")
			            	.setNegativeButton("确定",null).show(); 
					}
				}else{
				 new AlertDialog.Builder(wjmmActivity.this).setTitle("提示").setMessage("请输入完整资料！")
	            	.setNegativeButton("确定",null).show(); 
			}
			}
		});
	
		btn_fsyzm=(Button)findViewById(R.id.wjmm_btn_fsyzm);
		btn_fsyzm.setOnClickListener(new OnClickListener(){
		

			@Override
			public void onClick(View arg0) {
				if(!ed_name.getText().toString().equals("")){
			
				}else{
					 new AlertDialog.Builder(wjmmActivity.this).setTitle("提示").setMessage("请输入手机号码！")
		            	.setNegativeButton("确定",null).show(); 
				}
				
			}
		});
		
	}
  
 
   
  
    //异步获取首页图片
    final Handler cwjHandler = new Handler();
    final Runnable mUpdateResults_success = new Runnable() {
    public void run() {
    	pg.dismiss();
    	 new AlertDialog.Builder(wjmmActivity.this).setTitle("提示").setMessage("重设密码成功，请重新登录！")
        	.setNegativeButton("确定",new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
					
				}
			}).show(); 
    }};
}