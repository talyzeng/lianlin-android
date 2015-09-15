package com.cmcc.attendance.activity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.cmcc.attendance.annotation.EventListener;
import com.cmcc.attendance.annotation.Select;
import com.cmcc.attendance.annotation.ViewInject;
import com.cmcc.attendance.utils.PrintLog;
import com.openfile.CallbackBundle;
import com.openfile.OpenFileDialog;

public class BaseFragement extends Fragment {
	public Context mContext;
	public static String TAG = BaseFragement.class.getSimpleName();
	public static View rootView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getSimpleName();
		mContext = getActivity();
		PrintLog.d(TAG, "onCreate()");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void setContentView(View view){
		rootView = view;
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
						field.set(this,rootView.findViewById(viewId));
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
					
					
					Select select = viewInject.select();
					if(!TextUtils.isEmpty(select.selected()))
						setViewSelectListener(field,select.selected(),select.noSelected());
					
				}
			}
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

	
	

}
