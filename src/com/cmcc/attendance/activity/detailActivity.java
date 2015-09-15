package com.cmcc.attendance.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.huison.tools.Chuli;
import com.lianlin.R;

public class detailActivity extends Activity {
	public static String id,toptitle;
	TextView text_name,text_title;
	String title;
	ImageView btn_return;
//	static ImageView imgtop;
	LinearLayout btn_jrgwc,btn_sc,btn_fx;
	
	private String url,contentUrl;
    private MediaPlayer mMediaPlayer;
    private int state = IDLE;
    private static final int PLAYING = 0;
    private static final int PAUSE = 1;
    private static final int STOP = 2;
    private static final int IDLE = 3;

    public static final int UPDATE = 2;
  
    private boolean iffirst = false;  

    private Timer mTimer;    
    private TimerTask mTimerTask;   
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突    
    


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        seekbar = (SeekBar) findViewById(R.id.detail_seekbar1);  
        seekbar.setOnSeekBarChangeListener(new MySeekbar()); 
        play();
        btn_return=(ImageView)findViewById(R.id.detail_btn_return);
        btn_return.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        });
        
     
        text_title=(TextView)findViewById(R.id.detail_text_toptitle);
        text_title.setText(toptitle);
        
        
        Intent intent = getIntent();
        Bundle bd = intent.getBundleExtra("bd");// 根据bundle的key得到对应的对象
        url=bd.getString("url");
        contentUrl = bd.getString("contentUrl");
       
/*        
        r_fx=(RelativeLayout)findViewById(R.id.detail_r_fx);
        r_fx.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 sendWxUrl(1);
				
			}
        	
        });*/
        
      //WebView
        WebView browser=(WebView)findViewById(R.id.Toweb); 
        try
        {
        browser.loadUrl(Chuli.yuming + url);
        //browser.loadUrl("http://www.baidu.com");  
          
        //设置可自由缩放网页  
        browser.getSettings().setSupportZoom(true);  
        browser.getSettings().setBuiltInZoomControls(true);  
          
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，  
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象         
        browser.setWebViewClient(new WebViewClient() {  
            public boolean shouldOverrideUrlLoading(WebView view, String url)  
            {   
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边  
                view.loadUrl(url);  
                        return true;  
            }         
             });
        }catch(Exception ee){
        	Log.v("载入homeworkpaper错误  ", ee.toString());
        }
        
	 }
    

    private void play() {
    	try {
    	if (mMediaPlayer == null || state == STOP) {
    	// 创建MediaPlayer对象并设置Listener
    		
    		
    		Intent intent = getIntent();
            Bundle bd = intent.getBundleExtra("bd");// 根据bundle的key得到对应的对象
            url=bd.getString("url");
            contentUrl = bd.getString("contentUrl");	
    		
	    	//mMediaPlayer = MediaPlayer.create(this, R.raw.test); 
	    	Uri listenFile = null;
	    	listenFile = Uri.parse(Chuli.yuming + contentUrl);	    	
	    	mMediaPlayer = MediaPlayer.create(this, listenFile);//.create(this, R.raw.test);
	    	mMediaPlayer.setOnPreparedListener(preListener);
	    	 Log.v("总进度","k"+mMediaPlayer.getDuration());
	    	seekbar.setMax(mMediaPlayer.getDuration());//设置进度条   
	        //----------定时器记录播放进度---------//     
	        mTimer = new Timer();    
	        mTimerTask = new TimerTask() {    
            @Override    
            public void run() {     
            	try{
                if(isChanging==true) {   
                    return;    
                }  
               
                seekbar.setProgress(mMediaPlayer.getCurrentPosition());  
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            }    
        };   
        mTimer.schedule(mTimerTask, 0, 10);   
        iffirst=true; 
        
    	} else {
    	// 复用MediaPlayer对象
    	mMediaPlayer.reset();
    	}
    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	}
 // MediaPlayer进入prepared状态开始播放
    private OnPreparedListener preListener = new OnPreparedListener() {
    public void onPrepared(MediaPlayer arg0) {
    mMediaPlayer.start();
    
    state = PLAYING;
    }

    };

    //进度条处理   
    class MySeekbar implements OnSeekBarChangeListener {  
        public void onProgressChanged(SeekBar seekBar, int progress,  
                boolean fromUser) {  
        	
        }  
  
        public void onStartTrackingTouch(SeekBar seekBar) {  
        	  isChanging=true;    

        }  
  
        public void onStopTrackingTouch(SeekBar seekBar) {  
          
        	  mMediaPlayer.seekTo(seekbar.getProgress());  
        	  isChanging=false;    

        }  
  
    }  

    SeekBar seekbar;
    
   

    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	try{
    	mMediaPlayer.release();
    	mTimer.cancel();
    	mTimerTask.cancel();
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
	//go back
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        WebView browser=(WebView)findViewById(R.id.Toweb);  
        // Check if the key event was the Back button and if there's history  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {  
            browser.goBack();  
            return true;  
        }  
      //  return true;  
        // If it wasn't the Back key or there's no web page history, bubble up to the default  
        // system behavior (probably exit the activity)  
        return super.onKeyDown(keyCode, event);  
    } 
	 
	
		
		  
	
}