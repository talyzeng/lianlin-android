package com.cmcc.attendance.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public MyListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public MyListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
  
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        if(ev.getAction() == MotionEvent.ACTION_MOVE){     
            return true;   
        }   
        return super.dispatchTouchEvent(ev);  
    }  
}