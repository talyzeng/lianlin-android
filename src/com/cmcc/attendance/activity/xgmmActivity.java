package com.cmcc.attendance.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huison.tools.Chuli;
import com.lianlin.R;
//import com.nostra13.universalimageloader.utils.L;

public class xgmmActivity extends Activity {

	Button btn_back;
	 Dialog pg;
	String zc_code,zc_msg;
	
	EditText ed_oldpwd,ed_pwd1,ed_pwd2;
	
	Button btn_xg,btn_fsyzm;
	String now_type;
	RadioGroup radiogroup1;  
    RadioButton radio_sj,radio_mj;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xgmm);
     
        btn_back=(Button)findViewById(R.id.xgmm_btn_return);
        btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			 finish();
				
			}
        	
        });
        
		ed_oldpwd=(EditText)findViewById(R.id.xgmm_ed_oldpwd);
		ed_pwd1=(EditText)findViewById(R.id.xgmm_ed_pwd1);
		ed_pwd2=(EditText)findViewById(R.id.xgmm_ed_pwd2);
		
		btn_xg=(Button)findViewById(R.id.xgmm_btn_xg);
		btn_xg.setOnClickListener(new OnClickListener(){
		

			@Override
			public void onClick(View arg0) {
				
				if(!ed_oldpwd.getText().toString().equals("")&&
						!ed_pwd1.getText().toString().equals("")&&
						!ed_pwd2.getText().toString().equals("")){
					
				if(ed_pwd1.getText().toString().equals(ed_pwd2.getText().toString())){
					handle_xgmm(ed_pwd1.getText().toString(),ed_oldpwd.getText().toString());
					}else{
						 new AlertDialog.Builder(xgmmActivity.this).setTitle("提示").setMessage("两次输入密码不一致！")
			            	.setNegativeButton("确定",null).show(); 
					}
				}else{
				 new AlertDialog.Builder(xgmmActivity.this).setTitle("提示").setMessage("请输入完整资料！")
	            	.setNegativeButton("确定",null).show(); 
			}
			}
		});
	}
  
    public void handle_xgmm(final String nowpwd,final String oldpwd) {
		  pg=Chuli.c_pg(xgmmActivity.this, "正在修改...");
        pg.show();
	      	 Thread t3 = new Thread() {
	      	 @Override
	      	public void run() {
	      		    
	             try{
	            		List <NameValuePair> params=new ArrayList<NameValuePair>();
		 				params.add(new BasicNameValuePair("current_password",oldpwd)); 
		 				params.add(new BasicNameValuePair("password",nowpwd)); 
		 				params.add(new BasicNameValuePair("password_confirmation",nowpwd)); 		 			
		 				
		// String za=Chuli.HttpPatch(Chuli.yuming+BaseActivity.now_xgmmurl+"?current_password="+oldpwd+"&password="+nowpwd+"&password_confirmation="+nowpwd,BaseActivity.now_userid);
		 				 String za=Chuli.HttpPatch(Chuli.yuming+BaseActivity.now_xgmmurl+"?current_password="+oldpwd+"&password="+nowpwd+"&password_confirmation="+nowpwd,BaseActivity.now_userid);
		 				
		 				//Log.v("测试链接：",Chuli.yuming+BaseActivity.now_xgmmurl+"?current_password="+oldpwd+"&password="+nowpwd+"&password_confirmation="+nowpwd);
			 
	     // Log.v("测试BaseActivity.now_loginToken：",BaseActivity.now_userid);	    
		 // Log.v("测试返回：",za);
		 			
		 		      //  String a=Chuli.patch(Chuli.yuming+BaseActivity.now_xgmmurl, params,BaseActivity.now_clientToken);  
		 		 
		   		//     Log.v("修改用户密码返回：",a);
		              if(za.equals("err")){
		            		cwjHandler.post(mUpdateResults_fail);	  
		              }else{
	   		        	cwjHandler.post(mUpdateResults_success);
		              }
	                  
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	      	 }
	      	 };
	      	 t3.start();
	      	 }
   
  
    //异步获取首页图片
    final Handler cwjHandler = new Handler();
    final Runnable mUpdateResults_success = new Runnable() {
    public void run() {
    	pg.dismiss();
    	 new AlertDialog.Builder(xgmmActivity.this).setTitle("提示").setMessage("修改成功！")
        	.setNegativeButton("确定",new android.content.DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();					
				}
			}).show(); 
    }};
    
    final Runnable mUpdateResults_fail = new Runnable() {
        public void run() {
        	pg.dismiss();
        	 new AlertDialog.Builder(xgmmActivity.this).setTitle("提示").setMessage("修改失败！")
            	.setNegativeButton("确定",new android.content.DialogInterface.OnClickListener() {
    				
    				@Override
    				public void onClick(DialogInterface arg0, int arg1) {
    					finish();
    					
    				}
    			}).show(); 
        }};
}