package com.lunbo2;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery {
    int childcount;
	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		timer.schedule(task, 3000, 3000);
	
		}

		public void Destroy(){
			timer.cancel();
		}
		

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}
	
	  Boolean isLeft=true;
	  Boolean isRight=false;
	private static final int timerAnimation = 1;
	 private final Handler mHandler = new Handler()
	 {
	  public void handleMessage(android.os.Message msg)
	  {
		
	   switch (msg.what)
	   {
	   case timerAnimation:
	    int position = getSelectedItemPosition();
	       if (position >= (getCount() - 1))
	    {
	    	   isRight=true;
	    	   isLeft=false;
	    }
	       
	       if(position==0){
	    	   isLeft=true;
	    	   isRight=false;
	       }
	    
	     if(isLeft==false){
	     onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
	     }
	   
	     if(isRight==false){
	     onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
	     }
	    break;

	   default:
	    break;
	   }
	  };
	 };

	 private final Timer timer = new Timer();
	 private final TimerTask task = new TimerTask()
	 {
	  public void run()
	  {
	   mHandler.sendEmptyMessage(timerAnimation);
	  }
	 };

    public void setCount(Integer c){
    	this.childcount=c;
    }
	 
	 public int getCount( )

	 {

	  return this.childcount;

	 }

}
