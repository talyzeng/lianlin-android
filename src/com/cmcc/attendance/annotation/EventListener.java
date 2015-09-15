
package com.cmcc.attendance.annotation;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnHoverListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.cmcc.attendance.exceptions.ViewException;

@SuppressLint("NewApi") public class EventListener 
	   implements OnClickListener, 
	   			  OnLongClickListener,
	   			  OnItemClickListener,
	   			  OnItemSelectedListener,
	   			  OnItemLongClickListener,
	   			  OnHoverListener,
	   			  OnDragListener {

	private Object handler;
	
	private String clickMethod;
	private String longClickMethod;
	private String itemClickMethod;
	private String itemSelectMethod;
	private String nothingSelectedMethod;
	private String itemLongClickMehtod;
	private String hoverClickMethod;
	private String dragMethod;
	
	public EventListener(Object handler) {
		this.handler = handler;
	}
	
	public EventListener click(String method){
		this.clickMethod = method;
		return this;
	}
	
	public EventListener longClick(String method){
		this.longClickMethod = method;
		return this;
	}
	
	public EventListener itemLongClick(String method){
		this.itemLongClickMehtod = method;
		return this;
	}
	
	public EventListener itemClick(String method){
		this.itemClickMethod = method;
		return this;
	}
	
	public EventListener itemHover(String method){
		this.hoverClickMethod = method;
		return this;
	}
	
	public EventListener itemDrag(String method){
		this.dragMethod = method;
		return this;
	}
	
	public EventListener select(String method){
		this.itemSelectMethod = method;
		return this;
	}
	
	public EventListener noSelect(String method){
		this.nothingSelectedMethod = method;
		return this;
	}
	
	public boolean onLongClick(View v) {
		return invokeLongClickMethod(handler,longClickMethod,v);
	}
	
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		return invokeItemLongClickMethod(handler,itemLongClickMehtod,arg0,arg1,arg2,arg3);
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		
		invokeItemSelectMethod(handler,itemSelectMethod,arg0,arg1,arg2,arg3);
	}
	
	public void onNothingSelected(AdapterView<?> arg0) {
		invokeNoSelectMethod(handler,nothingSelectedMethod,arg0);
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		invokeItemClickMethod(handler,itemClickMethod,arg0,arg1,arg2,arg3);
	}
	
	public void onClick(View v) {
		invokeClickMethod(handler, clickMethod, v);
	}
	
	public boolean onHover(View v, MotionEvent event) {
		return invokeItemHoverMethod(handler,hoverClickMethod,v,event);
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		return invokeItemDragMethod(handler,dragMethod,v,event);
	}
	
	
	private boolean invokeItemDragMethod(Object handler2, String dragMethod2,
			Object... params) {
		if(handler2 == null) return false;
		Method method = null;
		try{   
			method = handler2.getClass().getDeclaredMethod(dragMethod2,View.class,DragEvent.class);
			if(method!=null){
				Object obj = method.invoke(handler2, params);
				return obj==null?false:Boolean.valueOf(obj.toString());	
			}
			else
				throw new ViewException("no such method:"+dragMethod2);
		}catch(Exception e){
			Log.e("hover","exception",e);
			e.printStackTrace();
		}
		
		return false;
	}

	private boolean invokeItemHoverMethod(Object handler2,
			String hoverClickMethod2,  Object... params) {
		if(handler2 == null) return false;
		Method method = null;
		try{   
			method = handler2.getClass().getDeclaredMethod(hoverClickMethod2,View.class,MotionEvent.class);
			if(method!=null){
				Object obj = method.invoke(handler2, params);
				return obj==null?false:Boolean.valueOf(obj.toString());	
			}
			else
				throw new ViewException("no such method:"+hoverClickMethod2);
		}catch(Exception e){
			Log.e("hover","exception",e);
			e.printStackTrace();
		}
		
		return false;
	}

	private static Object invokeClickMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return false;
		Method method = null;
		try{   
			method = handler.getClass().getDeclaredMethod(methodName,View.class);
			if(method!=null)
				return method.invoke(handler, params);	
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	private static boolean invokeLongClickMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return false;
		Method method = null;
		try{   
			//public boolean onLongClick(View v)
			method = handler.getClass().getDeclaredMethod(methodName,View.class);
			if(method!=null){
				Object obj = method.invoke(handler, params);
				return obj==null?false:Boolean.valueOf(obj.toString());	
			}
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	
	
	private static Object invokeItemClickMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return null;
		Method method = null;
		try{   
			///onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			method = handler.getClass().getDeclaredMethod(methodName,AdapterView.class,View.class,int.class,long.class);
			if(method!=null)
				return method.invoke(handler, params);	
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private static boolean invokeItemLongClickMethod(Object handler, String methodName,  Object... params){
		if(handler == null) throw new ViewException("invokeItemLongClickMethod: handler is null :");
		Method method = null;
		try{   
			///onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			method = handler.getClass().getDeclaredMethod(methodName,AdapterView.class,View.class,int.class,long.class);
			if(method!=null){
				Object obj = method.invoke(handler, params);
				return Boolean.valueOf(obj==null?false:Boolean.valueOf(obj.toString()));	
			}
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	private static Object invokeItemSelectMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return null;
		Method method = null;
		try{   
			///onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			method = handler.getClass().getDeclaredMethod(methodName,AdapterView.class,View.class,int.class,long.class);
			if(method!=null)
				return method.invoke(handler, params);	
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Object invokeNoSelectMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return null;
		Method method = null;
		try{   
			//onNothingSelected(AdapterView<?> arg0)
			method = handler.getClass().getDeclaredMethod(methodName,AdapterView.class);
			if(method!=null)
				return method.invoke(handler, params);	
			else
				throw new ViewException("no such method:"+methodName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
