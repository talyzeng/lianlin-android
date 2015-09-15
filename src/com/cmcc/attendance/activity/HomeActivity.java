package com.cmcc.attendance.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.cmcc.attendance.adapter.HomePagerAdapter;
import com.cmcc.attendance.annotation.ViewInject;
import com.kj.MyViewPager;
import com.lianlin.R;
import com.loader.AsnycImageLoader;

public class HomeActivity extends BaseActivity implements OnPageChangeListener {
	@ViewInject(id = R.id.home_viewpage)
	public static MyViewPager mViewPage;
	@ViewInject(id = R.id.rdoBtn_tab_home, click = "onClickTab")
	private static RadioButton mHome;
	@ViewInject(id = R.id.rdoBtn_tab_attendance, click = "onClickTab")
	public static RadioButton mAttendance;
	@ViewInject(id = R.id.rdoBtn_tab_account, click = "onClickTab")
	private static RadioButton mAccount;
	@ViewInject(id = R.id.rdoBtn_tab_me, click = "onClickTab")
	private static RadioButton mMe;	
	public static TextView text_num;	
	private HomePagerAdapter homePagerAdapter;
	private List<BaseFragement> mFragments = new ArrayList<BaseFragement>();
	public static Handler handler;
    public static HomeActivity _instance;
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if ((System.currentTimeMillis() - mExitTime) > 2000) {
                            Object mHelperUtils;
                            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                            mExitTime = System.currentTimeMillis();
                    } else {
                            finish();
                          //  AttendanceMusic.player.release();
                    }
                    return true;
            }
            return super.onKeyDown(keyCode, event);
    }
	   
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	//关闭已经存在的聊天连接
    	AVIMClient.getInstance(BaseActivity.now_clientToken).close(new AVIMClientCallback() {
	        @Override
	        public void done(AVIMClient avimClient, AVException e) {
	          if (e == null) {
	            Log.d(TAG, "退出连接");
	          } else {
	            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	          }
	        }
	      });
	    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//getmLeftView().setVisibility(View.GONE);
	/*	
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		*/
		loader=new AsnycImageLoader();
        text_num=(TextView)findViewById(R.id.bottom_text_xxnum);
	   _instance=this;
		init();
		//handle_getList();
	}
	public static void setTextNum(String num){
    	text_num.setText(num);
    }
	private void init() {
	//	setTitle(getString(R.string.string_home_title));
		mFragments.add(new syFragement());//作业页面
		mFragments.add(new xxFragement());//消息页面
		mFragments.add(new bjFragement());//班级页面
		mFragments.add(new userFragement());//个人页面
		//mViewPage.setOnPageChangeListener(this);
		homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),mFragments, HomeActivity.this);
		mViewPage.setAdapter(homePagerAdapter);
		mViewPage.setScanScroll(false);
		mViewPage.setOffscreenPageLimit(3);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	
	}

	@Override
	public void onPageSelected(int key) {
		//mViewPage.setScanScroll(false);
		resetTab();
		switch (key) {
		case 0:
			mHome.setChecked(true);
			break;
			
		case 1:
			mAttendance.setChecked(true);
			break;

		case 2:
			mAccount.setChecked(true);
			break;
			
		case 3:
			mMe.setChecked(true);
			break;
		}
	}
	
	public static void resetTab()
	{
		mHome.setChecked(false);
		mAttendance.setChecked(false);
		mAccount.setChecked(false);
		mMe.setChecked(false);
	}

	public void onClickTab(View view) {
		mViewPage.setScanScroll(true);
		resetTab();
		if(view == mHome){
			mHome.setChecked(true);
			mViewPage.setCurrentItem(0,true); 
			
			
		}else if(view == mAttendance){
			mAttendance.setChecked(true);
			mViewPage.setCurrentItem(1,true); 
		}else if(view == mAccount){
			mAccount.setChecked(true);
			mViewPage.setCurrentItem(2,true); 
			Log.v("有运行切换","2");  
		}else if(view == mMe){
			mMe.setChecked(true);
			mViewPage.setCurrentItem(3,true); 
		}
	}
	private AsnycImageLoader loader = null; 
	//处理用户头像
	void loadIMG(String url) { 
		Log.v("图片地址1：",url);
		Drawable cacheImage = loader.loadDrawable(url, new AsnycImageLoader.ImageCallback() { 
		@Override 
		public void imageLoaded(Drawable drawable) { 
		try{
			BaseActivity.now_userhead=drawable;//用户头像地址
			}catch(Exception e){
		e.printStackTrace();}
		} 
		});
		
		if (cacheImage != null) { 
			
		try{
			BaseActivity.now_userhead=cacheImage; 			
			}catch(Exception e){
		e.printStackTrace();}
		} 
	} 
	/*
	 Dialog pg;
	 String dl_msg;
	 
	 public void handle_getList() {
		  pg=Chuli.c_pg(HomeActivity.this, "正在获取...");
          pg.show();
	      	 Thread t3 = new Thread() {
	      	 @Override
	      	public void run() {
	      		    
	             try{
	            	String a= Chuli.HttpGetData("https://api.ll100.com/v1/users/me",BaseActivity.now_userid);
	            	Log.v("返回1：",a);
	   		        
	            	JSONObject p=new JSONObject(a);
	            	String lj=p.getString("student_workathoners_endpoint");
	            	
	            	BaseActivity.now_clientToken=p.getString("client_token");
	             String b= Chuli.HttpGetData("https://api.ll100.com/"+lj,BaseActivity.now_userid);
		 		        Log.v("作业列表返回：","kk "+b);
	   		        	cwjHandler.post(mUpdateResults_success);
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
		    }
 		};*/
}
