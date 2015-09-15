package com.cmcc.attendance.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gps.MyApplication;
import com.huison.tools.Chuli;
import com.huison.tools.ImageTools;
import com.lianlin.R;

public class LoginActivity extends Activity {
	/*@ViewInject(id=R.id.btn_login,click="onClickLogin")
	private Button mBtnLogin;*/
	
	SharedPreferences mPrefs ;
	
	//储存全局变量
	
	String accesstoken ="";
	String refreshtoken = "";
	
	LinearLayout l_zhdl;
	
	ImageView btn_zc,btn_zhlogin,btn_wxlogin;
	Button btn_login;
	String now_userid ;
	String now_userloginname ;
	String now_userpwd ;
	
	EditText ed_username,ed_userpwd,ed_yzm;
	TextView text_bbh,btn_wjmm,btn_zhuce;
	//CircularImage imghead;
	CheckBox c1;
	/** 
	 * 返回当前程序版本名 
	  */  
	int versioncode;
	
	 public String getAppVersionName(Context context) {  
	     String versionName = "";  
	    try {  
	        // ---get the package info---  
	         PackageManager pm = context.getPackageManager();  
	         PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	         versionName = pi.versionName;  
	        versioncode = pi.versionCode;
	        if (versionName == null || versionName.length() <= 0) {  
	             return "";  
	         }  
	    } catch (Exception e) {  
	         Log.e("VersionInfo", "Exception", e);  
	     }  
	    return versionName;  
	 }  

	
	/*private int A=0;
	 
	
	void test1(){
		A=1;
	}
	
	void test2(){
		int B=A;
		B=2;
		Log.v("A",""+A);
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//setShowActionBar(false);
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		setContentView(R.layout.activity_login);
		try{
			accesstoken = MyApplication.getaccessToken();
			refreshtoken = MyApplication.getrefreshToken();
		}
		catch(Exception e)
		{
			Log.d("application ", e.toString());
		}
		
		/*
		if((accesstoken!= null)and(refreshtoken!=null)
		{
			if
		}
		*/
	/*	test1();
		test2();*/
		
		
		try{
		mPrefs = getSharedPreferences("xlydata", 0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		c1=(CheckBox)findViewById(R.id.login_check1);
		/*c1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					
				}else{
					d
				}
				
			}
		});*/
		
	    l_zhdl=(LinearLayout)findViewById(R.id.login_l_zhdl);		
		ed_username=(EditText)findViewById(R.id.login_edttxt_username);
		ed_userpwd=(EditText)findViewById(R.id.login_edttxt_password);
		ed_yzm=(EditText)findViewById(R.id.login_edttxt_yzm);		
		btn_login=(Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				if(ed_username.getText().toString()!=""&&ed_userpwd.getText().toString()!=""){
					handle_login();
					}else{	
						/*
						 * 处理一次性登录
						 * 
						 * 
						 * */
						if((accesstoken!="")&&(refreshtoken!="")){
							Thread t4 = new Thread() {
						      	 @Override
						      	public void run() {	      		    
						             try{	            
						            		List <NameValuePair> params=new ArrayList<NameValuePair>();
							 				params.add(new BasicNameValuePair("grant_type",refreshtoken)); 
							 				params.add(new BasicNameValuePair("refresh_token",refreshtoken));
							 				params.add(new BasicNameValuePair("token",accesstoken));
							 			
							 		        String a=Chuli.postData(Chuli.yuming+"/oauth/token/", params);  
							 		       
							   		        //Log.v("登陆返回：","kk "+a);
						   		        	
							   		     if(a.equals("none")){
							   		        	dl_msg="账号密码已过期！";
							   		            cwjHandler.post(mUpdateResults_fail);
							   		        }else{
							   		        	JSONObject p=new JSONObject(a);						   		      
						   			         try{
						   			        	 //获取个人信息
						   			        	 String z=Chuli.HttpGetData(Chuli.yuming+"/v1/users/me",p.getString("access_token"));
						   			        	 //Log.v("我的信息返回",z);
						   			        	 
						   			        	 JSONObject e=new JSONObject(z); //个人信息jason对象

						   			        	ed_username.setText(e.getString("username"));
						   			        	ed_userpwd.setText(e.getString(""));
						   			        	ed_yzm.setText(e.getString("access_token"));						   			        	
						   			            }catch(Exception e){
						   			            	e.printStackTrace();
						   			            }
						                   
							   		        }
						                } catch (Exception e) {
						                    // TODO Auto-generated catch block
						                    e.printStackTrace();
						                }			             
						      	
						      	 }
						      	 };
						      	 t4.start();
						      	handle_login();
						      	//t4.destroy();
							}
						else{
				    	 new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("请输入完整信息！")
				         	.setNegativeButton("确定",null).show(); 
						}
						//new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("请输入完整信息！")
			         	//.setNegativeButton("确定",null).show(); 
					}
			}
			
		});
		
		btn_zhuce=(TextView)findViewById(R.id.btn_zhuce);
		btn_zhuce.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			  
				Intent it=new Intent(LoginActivity.this,zhuce_userActivity.class);
				startActivity(it);
				
			}
			
		});
		
	/*	btn_wjmm=(TextView)findViewById(R.id.btn_wjmm);
		btn_wjmm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			  
				Intent it=new Intent(LoginActivity.this,zhuce_userActivity.class);
				startActivity(it);
				
			}
			
		});
		*/
		
	
	//	 imghead = (CircularImage)findViewById(R.id.login_imghead);  
	/*	 
		 try{
		 Bitmap bit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/mlmo/headimg/headimg.png");
		
		 imghead.setImageBitmap(bit);
		 }catch(Exception e){
			 e.printStackTrace();
		 }*/
	/*	 try{
		   SharedPreferences sharedata = getSharedPreferences("MyData", 0);   
           String isjzmm=sharedata.getString("isJZMM", "false");
		    if(isjzmm.equals("true")){
		        c1.setChecked(true);
		    	ed_username.setText(sharedata.getString("userName", ""));
		    	ed_userpwd.setText(sharedata.getString("userPwd", ""));
		    	
		    }else{
		    	c1.setChecked(false);
		    	ed_username.setText("");
		    	ed_userpwd.setText("");
		    	
		  
		    }
		
		 }catch(Exception e){
			 e.printStackTrace();
		 }*/
		   
		/*ed_username.setText("aimee1115");
		ed_userpwd.setText("123456");
		
		ed_username.setText("王露露");
		ed_userpwd.setText("123456");
		ed_yzm.setText("856512");*/
	}
	
	public void onClickLogin(View v){
		if(!ed_username.getText().toString().equals("")&&!ed_userpwd.getText().toString().equals("")){
		handle_login();
		}else{

	    	 new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("请输入完整信息！")
	         	.setNegativeButton("确定",null).show(); 
	    	
		}
	}
	
	/*public void onClickRegisterSJ(View view){
		Intent intent = new Intent(LoginActivity.this,zhuce_sjActivity.class);
		startActivity(intent);
	
	}*/
	
	
	 Dialog pg;
	 String dl_msg;
	 
	 public void handle_login() {
		  pg=Chuli.c_pg(LoginActivity.this, "正在登录...");
          pg.show();
	      Thread t3 = new Thread() {
	      @Override
	      public void run() {	      		    
	         try{	            
	            List <NameValuePair> params=new ArrayList<NameValuePair>();
		 		params.add(new BasicNameValuePair("username",ed_username.getText().toString())); 
		 		params.add(new BasicNameValuePair("password",ed_userpwd.getText().toString()));
		 		params.add(new BasicNameValuePair("authcode",ed_yzm.getText().toString()));
		 			
		 		String a=Chuli.postData(Chuli.yuming+"v1/access_token/", params);  
		 		       
		   		Log.v("登陆返回：","kk "+a);
	   		        	
		   		if(a.equals("none")){
		   		    dl_msg="账号或密码错误！";
		   		    cwjHandler.post(mUpdateResults_fail);
		   		}else{
	   		        JSONObject p=new JSONObject(a);
	   		      
	   			    now_userloginname=ed_username.getText().toString();
	   			    now_userpwd=ed_userpwd.getText().toString();
	   			    now_userid=p.getString("access_token");
	   			    MyApplication.setaccessToken(p.getString("access_token"));
	   			    MyApplication.setrefreshToken(p.getString("refresh_token"));
	   			        	//MyApplication.setaccessToken(id)
	   			        	
	   			        	 
	   			        	
	   			            BaseActivity.now_userid=now_userid; 
	   			            BaseActivity.now_userpwd=now_userpwd;
	   			            BaseActivity.now_userloginname=now_userloginname;
	   			         try{
	   			        	 //获取个人信息
	   			        	 String z=Chuli.HttpGetData(Chuli.yuming+"/v1/users/me",BaseActivity.now_userid);
	   			        	 Log.v("我的信息返回",z);
	   			        	 
	   			        	 JSONObject e=new JSONObject(z); //个人信息jason对象
		   	            	
	   			        	//MyApplication.setaccessToken(e.getString("access_token"));
	   			        	//MyApplication.setrefreshToken(e.getString("refresh_token"));
	   			        	 
	   			        	 
	   			        	 BaseActivity.now_myname=e.getString("name");
	   			        	 BaseActivity.now_myphone=e.getString("phone");  
	   			        	 BaseActivity.now_xgmmurl=e.getString("password_endpoint");
	   			        	 BaseActivity.now_userimgheadurl=e.getString("avatar_url");
	   			        	 BaseActivity.now_clientToken=e.getString("client_token");//获取用户聊天的唯一标识
		   	            	 //BaseActivity.now_user_endpoint=e.getString("user_endpoint");//
	   			             BaseActivity.now_user_endpoint=e.getString("user_endpoint");
	   			             BaseActivity.bitmapavatal = ImageTools.GetLocalOrNetBitmap(e.getString("avatar_url"));
	   			        	 String cls=e.getString("clazz");
	   			             
	   			             JSONObject p2=new JSONObject(cls);//班级信息jason对象
	   			             
	   			             //Log.v("班级名称","bj:"+p2.getString("fullname"));
	   			             
	   			             JSONObject pk3=new JSONObject(cls);//班级信息jason对象
	   			             
	   			             String bjurl=pk3.getString("clazz_endpoint");
	   			             String ConversationUrl=pk3.getString("conversation_endpoint");
	   			             //根据学生信息获取学生对应班级信息
	   			             String zk=Chuli.HttpGetData(Chuli.yuming+bjurl,BaseActivity.now_userid);
	   			             //    Log.v("我的班级信息",zk);
		   			         JSONObject kc=new JSONObject(zk);//班级详情jason对象
		   			         
		   			         if(kc.getString("conversation_token")!=null){
		   			        	 BaseActivity.now_channelid=kc.getString("conversation_token");//获取班级聊天ID
		   			         }else{
		   			        	List <NameValuePair> paramConversation=new ArrayList<NameValuePair>();
		   			        	paramConversation.add(new BasicNameValuePair("Authorization",BaseActivity.now_userid)); 
				 				
		   			        	 String conversationResult=Chuli.postData(ConversationUrl, paramConversation);
		   			        	 JSONObject conversationJason=new JSONObject(conversationResult);
		   			        	BaseActivity.now_channelid=conversationJason.getString("token");
		   			         }
		   			         
		   			         //Log.v("我的channelid",BaseActivity.now_channelid);
		   			         
			   			      //班级详情里面的教师列表读取
		   			          String ze=Chuli.HttpGetData(Chuli.yuming+"/v1/clazzes/"+p2.getString("id")+"/teacherships", BaseActivity.now_userid);  		
					 		  //Log.v("ZE返回：",ze);   
		   			          ze=ze.substring(0,ze.length()-1);
		   			          ze=ze.substring(1,ze.length());
		   			          JSONObject qq=new JSONObject(ze);
		   			          String teacher=qq.getString("teacher");
			   			      JSONObject qq1=new JSONObject(teacher);
			   			      BaseActivity.now_bzrname=qq1.getString("name");//班主任名称			   			       
			   			      BaseActivity.now_classname=p2.getString("fullname");//班级名称
			   			      JSONObject p3=new JSONObject(p2.getString("school"));
			   			      BaseActivity.now_schoolname=p3.getString("name");//学校名称
			   			      //Log.v("学校名称","xx:"+p3.getString("name"));
			   			      //读取学生所在班级的信息
			   			      String b=Chuli.HttpGetData(Chuli.yuming+p2.getString("clazz_endpoint"), BaseActivity.now_userid);  		
			   			      JSONObject p4=new JSONObject(b);
			   			      //当前学生所在班级的学生列表地址
			   			      String studentlist=p4.getString("studentships_endpoint");
			   			      //当前学生所在班级的教师列表地址
			   			      String teacherlist=p4.getString("teacherships_endpoint");
			   			      //获取当前学生所在班级的学生列表
			   			      String c=Chuli.HttpGetData(Chuli.yuming+studentlist, BaseActivity.now_userid);  		
			   			      //      Log.v("测试返回学生列表：","kk "+c);
			   			      BaseActivity.now_studentlist=c;
			   			      //获取当前学生所在班级的教师列表
			   			      String d=Chuli.HttpGetData(Chuli.yuming+teacherlist, BaseActivity.now_userid);  		
			   			      //     Log.v("测试返回教师列表：","kk "+d);
			   			      BaseActivity.now_teacherlist=d;
	   			            }catch(Exception e){
	   			            	e.printStackTrace();
	   			            }
	   			       
			   		        
	   			      
	   			          //  BaseActivity.now_userimgheadurl=p.getString("用户头像");
	   		               // Log.v("记住密码","1");
	   		          
	   		      /*   if(c1.isChecked()){
	   		         SharedPreferences.Editor sharedata = getSharedPreferences("pudaodata", 0).edit();   
					 sharedata.putString("isJZMM","true");
					 sharedata.putString("userName",ed_username.getText().toString());   
					 sharedata.putString("userPwd",ed_userpwd.getText().toString());   
						
					 sharedata.commit();  
	   		         }else{
	   		        	SharedPreferences.Editor sharedata = getSharedPreferences("pudaodata", 0).edit();   
						 sharedata.putString("isJZMM","false");
						 sharedata.putString("userName","");   
						 sharedata.putString("userPwd","");   
							
						 sharedata.commit();  
	   		         }*/
	   			            
	   			      
			 		   /*  String c=Chuli.HttpGetData("https://api.ll100.com/v1/clazzes/353/conversation", BaseActivity.now_userid);  		
			 		      Log.v("测试返回b：","kk "+c);
			 		      
			 		     String d=Chuli.HttpGetData("https://api.ll100.com/v1/users/1712", BaseActivity.now_userid);  		
			 		      Log.v("测试返回c：","kk "+d);
			 		      
			 		     String e=Chuli.HttpGetData("https://api.ll100.com/v1/clazzes", BaseActivity.now_userid);  		
			 		      Log.v("测试返回d：","kk "+e);*/
			 		      
			 		      
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
	  	
	
	    final Handler cwjHandler = new Handler();
	    final Runnable mUpdateResults_success = new Runnable() {
		    public void run() {
		    	pg.dismiss();
		    	Intent intent = new Intent(LoginActivity.this,HomeActivity.class);//SIMActivity
					startActivity(intent);
					finish();
			    
		    }
 		};
 		  final Runnable mUpdateResults_fail = new Runnable() {
 			    public void run() {
 			    	pg.dismiss();
 			    	 new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage(dl_msg)
 		         	.setNegativeButton("确定",null).show(); 
 		    	
 			    
 			    }
 	 		};
 	 		
 	 
}
