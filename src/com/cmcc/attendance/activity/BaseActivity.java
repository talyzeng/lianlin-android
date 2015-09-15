package com.cmcc.attendance.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cmcc.attendance.annotation.EventListener;
import com.cmcc.attendance.annotation.Select;
import com.cmcc.attendance.annotation.ViewInject;
import com.cmcc.attendance.utils.PrintLog;
import com.huison.tools.Chuli;
import com.lianlin.R;
import com.openfile.CallbackBundle;
import com.openfile.OpenFileDialog;

public abstract class BaseActivity extends FragmentActivity {
	 public static ArrayList<String> now_pics_chosed=new ArrayList<String>();
	 
	public static BaseActivity instance;
	//购物车的
	public static String now_addgwc_id;
	//购物车
		public static ArrayList<String> gwc_id=new ArrayList<String>();
		public static ArrayList<String> gwc_name=new ArrayList<String>();
		public static ArrayList<Drawable> gwc_img=new ArrayList<Drawable>();
		public static ArrayList<String> gwc_price=new ArrayList<String>();//现价
		
		public static ArrayList<Integer> gwc_num=new ArrayList<Integer>();
	
	//购物车的
	
	public static String TAG = BaseActivity.class.getSimpleName();
	private boolean isShowActionBar = true;
	public LinearLayout rootView;
	private TextView mTextView;
	private TextView mLeftView;
	private TextView mRightView;
	
	public static int pm_width;
	
	// 二级下拉菜单
	public static ArrayList<String> type1_name=new ArrayList<String>(); 

	public static ArrayList<String> type1_id=new ArrayList<String>(); 
	public static ArrayList<String> type1_json=new ArrayList<String>(); 
	public static int now_chose_type1_count;
	public static String now_chose_type2_id;
	
	public static  Bitmap bitmapavatal; //处理头像对象
	
	public static ArrayList<String> market_name=new ArrayList<String>(); 
	public static ArrayList<String> market_id=new ArrayList<String>(); 

	public static ArrayList<String> sx_name=new ArrayList<String>(); 
	public static ArrayList<String> sx_id=new ArrayList<String>(); 

	
	// 二级下拉菜单
	
	
	// 百度地图的东西
			public static String gps_jd = "";
			public static String gps_wd = "";

			public static double d_gps_jd;
			public static double d_gps_wd;

			public static String gps_address = "";
			public static String provinceNow = "广东";
		
			public static String now_qd_type;//判断当前签到类型(1 二维码签到  2 位置签到)
            public static String now_ewm;//扫描出来的二维码
			 public static Integer now_x,now_y;
			 
			 public static String sessionid = null;
			 public static String now_city;
			 
			 public static String now_userid ;
			 public static String now_userpwd ;
			 public static String now_usertype ;
			 public static String now_userloginname ;
			 public static String now_userimgheadurl ;
			 public static String now_usermoney ;
			 public static String now_wxtype;
			 public static Drawable now_userhead;
			 
			 public static String now_schoolname;
			 public static String now_classname;
			 public static String now_bzrname;
			 public static String now_myname;
			 public static String now_myphone;
				
			 public static String now_studentlist;
			 public static String now_teacherlist;
						
			 public static String now_channelid;//班级聊天conversationID
			 
			 
			 public static String now_clientToken;//个人聊天ID
			
			 public static String now_loginToken;
				
			 public static String now_xgmmurl;
			 
			 public static String now_user_endpoint;
				
			 //缓存首页猜你喜欢的数据
			 public static String data_cnxh;
			 
			 protected boolean filterException(Exception e) {
				    if (e != null) {
				      e.printStackTrace();
				      toast(e.getMessage());
				      return false;
				    } else {
				      return true;
				    }
				  }

				  protected void toast(String str) {
				    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
				  }

			 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instance=this;
		
		data_cnxh="";
		now_qd_type="";
		now_chose_type2_id="";
	/*	now_userid="";
		now_userpwd="";
		now_userloginname="";*/
		/*now_schoolname="";
		now_classname="";
		now_bzrname="";*/
		 
		
		   WindowManager wm = this.getWindowManager(); 
		      
		      pm_width = wm.getDefaultDisplay().getWidth(); 
		      
		TAG = getClass().getSimpleName();
		PrintLog.d(TAG, "onCreate()");
	}
	
	public void setContentView(int layoutResID) {
	/*	if(!isShowActionBar){*/
			super.setContentView(layoutResID);
		/*}else{
			super.setContentView(R.layout.activity_base);
			rootView = (LinearLayout) findViewById(R.id.ll_rootview);
			mLeftView = (TextView) findViewById(R.id.tv_left_view);
			mRightView = (TextView) findViewById(R.id.tv_right_view);
			mLeftView.setOnClickListener(actionBarClick);
			mRightView.setOnClickListener(actionBarClick);
			View view = LayoutInflater.from(this).inflate(layoutResID, null);
			rootView.removeAllViews();
			rootView.addView(view);
		}*/
		initView();
		
		
		
		
	
		
	
	}
	
	
	
	
	
	/*public void setTitle(String title){
		if(!TextUtils.isEmpty(title)){
			Log.v("有设置标题",title);
			mTextView.setText(title);
		}
	};*/
	
	private OnClickListener actionBarClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==mRightView){
				onClickRightBtn(v);
			}else if(v==mLeftView){
				onClickLeftBtn(v);
			}
		}
	};


	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}

	private void initView(){
		Field[] fields = getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if(viewInject!=null){
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(this,findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String clickMethod = viewInject.click();
					if(!TextUtils.isEmpty(clickMethod))
						setViewClickListener(field,clickMethod);
					
					String longClickMethod = viewInject.longClick();
					if(!TextUtils.isEmpty(longClickMethod))
						setViewLongClickListener(field,longClickMethod);
					
					String itemClickMethod = viewInject.itemClick();
					if(!TextUtils.isEmpty(itemClickMethod))
						setItemClickListener(field,itemClickMethod);
					
					String itemLongClickMethod = viewInject.itemLongClick();
					if(!TextUtils.isEmpty(itemLongClickMethod))
						setItemLongClickListener(field,itemLongClickMethod);
					
					String itemHoverClickMethod = viewInject.hover();
					
					String itemonDragMethod = viewInject.OnDrag();
					if(!TextUtils.isEmpty(itemonDragMethod)){
						setItemOnDragListener(field,itemHoverClickMethod);
					}
					
					Select select = viewInject.select();
					if(!TextUtils.isEmpty(select.selected()))
						setViewSelectListener(field,select.selected(),select.noSelected());
					
				}
			}
		}
	}
	
	
	private void setItemOnDragListener(Field field, String itemDragMethod) {
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnDragListener(new EventListener(this).itemDrag(itemDragMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void setViewClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnClickListener(new EventListener(this).click(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setViewLongClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnLongClickListener(new EventListener(this).longClick(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemClickListener(new EventListener(this).itemClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemLongClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemLongClickListener(new EventListener(this).itemLongClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void setViewSelectListener(Field field,String select,String noSelect){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((AbsListView)obj).setOnItemSelectedListener(new EventListener(this).select(select).noSelect(noSelect));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onClickLeftBtn(View view) {

	}

	public void onClickRightBtn(View view) {

	}

	public TextView getmTextView() {
		return mTextView;
	}

	public TextView getmLeftView() {
		return mLeftView;
	}

	public TextView getmRightView() {
		return mRightView;
	}

	public void setShowActionBar(boolean isShowActionBar) {
		this.isShowActionBar = isShowActionBar;
	}
	
	
	
	 static int openfileDialogId = 0;  
	 protected Dialog onCreateDialog(int id) {  
	        if(id==openfileDialogId){  
	            Map<String, Integer> images = new HashMap<String, Integer>();  
	            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹   
	            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标   
	            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标   
	            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标   
	            images.put("wav", R.drawable.filedialog_wavfile);   //wav文件图标   
	            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
	            Dialog dialog = OpenFileDialog.createDialog(id, BaseActivity.this, "请选择MP3文件", new CallbackBundle() {  
	                @Override  
	                public void callback(Bundle bundle) {  
	                    String filepath = bundle.getString("path");  
	                    Log.v("选择的文件",filepath);
	                    
	                    String newPath=Environment.getExternalStorageDirectory()+"/guibinmp3/"+getFileName(filepath)+".mp3";
	                    
	                    Chuli.copyFile(filepath, newPath);
	                   
	                  //  setTitle(filepath); // 把文件路径显示在标题上   
	                }  
	            },   
	            ".mp3;",  
	            images);  
	            return dialog;  
	        }  
	        return null;  
	    }  
	 
	 public String getFileName(String pathandname){
		 int start=pathandname.lastIndexOf("/");
		    int end=pathandname.lastIndexOf(".");
		    if (start!=-1 && end!=-1) {
		        return pathandname.substring(start+1, end); 
		    }
		    else {
		        return null;
		    }
		}

	 
	 
	 
	
	     
}
