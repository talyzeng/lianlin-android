package com.baidu;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ScrollView;
 
public class LazyScrollView extends ScrollView implements OnGestureListener{
	
	private GestureDetector mGestureDetector;
	
	private static final String tag="LazyScrollView";
	private Handler handler;
	private View view;
	public LazyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public LazyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public LazyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	//这个获得总的高度
	@Override
	public int computeVerticalScrollRange(){
		return super.computeHorizontalScrollRange();
	}
	@Override
	public int computeVerticalScrollOffset(){
		return super.computeVerticalScrollOffset();
	}
	private void init(){
		
		mGestureDetector = new GestureDetector(this);
		
		this.setOnTouchListener(onTouchListener);
		handler=new Handler(){
        	@Override
			public void handleMessage(Message msg) {
				// process incoming messages here
				super.handleMessage(msg);
				switch(msg.what){
				case 1:
					if(view.getMeasuredHeight() <= getScrollY() + getHeight()+250) {
						if(onScrollListener!=null){
							onScrollListener.onBottom();
						}
						
					}else if(getScrollY()==0){
						if(onScrollListener!=null){
							onScrollListener.onTop();
						}
					}
					else{
						if(onScrollListener!=null){
							onScrollListener.onScroll();
						}
					}
					break;
				default:
					break;
				}
			}
        };
		
	}
	
	  OnTouchListener onTouchListener=new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_UP:
					if(view!=null&&onScrollListener!=null){
						handler.sendMessageDelayed(handler.obtainMessage(1), 200);
					}
					break;

				default:
					break;
				}
				return false;
			}
	    	
	    };
	    
	    /**
	     * 获得参考的View，主要是为了获得它的MeasuredHeight，然后和滚动条的ScrollY+getHeight作比较。
	     */
	    public void getView(){
	    	this.view=getChildAt(0);
	    	if(view!=null){
	    		init();
	    	}
	    }
	    
	    /**
	     * 定义接口
	     * @author admin
	     *
	     */
	    public interface OnScrollListener{
	    	void onBottom();
	    	void onTop();
	    	void onScroll();
	    }
	    private OnScrollListener onScrollListener;
	    public void setOnScrollListener(OnScrollListener onScrollListener){
	    	this.onScrollListener=onScrollListener;
	    }
	    
	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent e) {
	        if (e.getAction() == MotionEvent.ACTION_MOVE&&Integer.parseInt(Build.VERSION.SDK) > 16) {
	    if(getScrollY() == 0){
	    System.out.println("ontouch2");
	return true;
	}
	        }
	        return super.onInterceptTouchEvent(e);
	    }
	    
	    
	    
	    
	    
	    
	    
	    

	    
	    
	    	    
	    	    
	    	@Override
	    	public boolean onTouchEvent(MotionEvent event) {
	    	/**
	    	* 此处是关键，onTouchEvent是ScrollView的手指动作处理事件
	    	* 在这里面捕获事件后传递给GestureDector的onTouchEvent来处理
	    	* 从而实现onFling, onScroll等事件的处理
	    	*/
	    	mGestureDetector.onTouchEvent(event);	
	    	return super.onTouchEvent(event);
	    	}
	    	    
	    	    //////////////////////// 以下为 onGestureListener 的方法 //////////////////////
	        public boolean canScrollVertically(int direction) {
	            return true;
	        }
	    	
	    	public boolean onDown(MotionEvent e) {
	    	return false;
	    	}

	    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    	        if (velocityY < 0 ) { //手指向上，屏幕向上滑动
	    	        	handler.sendMessageDelayed(handler.obtainMessage(1), 100); 
	    	        } else if (velocityY > 0) {  //手指向下，屏幕向下滑动
	    	        	handler.sendMessageDelayed(handler.obtainMessage(2), 200); 
	    	        } 
	    	return false;
	    	}

	    	public void onLongPress(MotionEvent e) {
	    	}

	    	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	    	return false;
	    	}

	    	public void onShowPress(MotionEvent e) {
	    	}

	    	public boolean onSingleTapUp(MotionEvent e) {
	    	return false;
	    	}
	    	    
}
