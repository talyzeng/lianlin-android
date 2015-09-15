package com.cmcc.attendance.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.ImageView;
import android.widget.TextView;
import com.lianlin.R;

public class webActivity extends Activity {

	WebView wv1;
	public static String url,url2,title;
	TextView text_title,text_date,text_zannum;
	ImageView btn_return,btn_add;
	

//	window.active.tips(text)			// 提示( 非阻塞 )
//	window.active.alert(text)			// 提示( 阻塞 )
//	window.active.confirm(text)			// 确认( 阻塞 )
//	window.active.prompt(text,default)	// 对话( 阻塞 )

	@JavascriptInterface
	public void close() {
		finish();
	}
	@JavascriptInterface
	public void open(String url) {
	   wv1.loadUrl(url);
	}
	@JavascriptInterface
	public void tips(String text) {
		 new AlertDialog.Builder(webActivity.this).setTitle("提示").setMessage(text)
       	.setNegativeButton("确定",null).show(); 
  	
	}
	
	@JavascriptInterface
	public void alert(String text) {
		 new AlertDialog.Builder(webActivity.this).setTitle("提示").setMessage(text)
       	.setNegativeButton("确定",null).show(); 
  	
	}
	
	@JavascriptInterface
	public void confirm(String text) {
		 new AlertDialog.Builder(webActivity.this).setTitle("提示").setMessage(text)
       	.setNegativeButton("确定",null).show(); 
  	
	}
	 @SuppressLint({ "NewApi", "JavascriptInterface" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
    
  	 
  	    text_title=(TextView)findViewById(R.id.web_text_title);
        text_title.setText(title);
        
      
        btn_return=(ImageView)findViewById(R.id.web_btn_return);
        btn_return.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        });
        
      
        wv1=(WebView)findViewById(R.id.web_wv1);
     /*   wv1.getSettings().setLoadWithOverviewMode(true);
    
		wv1.getSettings().setTextZoom(110);
        
        wv1.getSettings().setUseWideViewPort(true);
        WebSettings settings = wv1.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        wv1.loadUrl(url);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setSupportZoom(false);
        wv1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv1.setHorizontalScrollBarEnabled(false);
        wv1.setHorizontalScrollbarOverlay(true);
        wv1.setInitialScale(100);
		// 设置Web视图
        wv1.setWebViewClient(getWebViewClient());
        wv1.setWebChromeClient(getWebChromeClient());
        wv1.addJavascriptInterface(this, "browser");
		// 加载需要显示的网页
        wv1.loadUrl(getIntent().getStringExtra(IntentDataTypes.URL.name()));*/
			
        
        wv1.setWebViewClient(getWebViewClient());
		wv1.setWebChromeClient(getWebChromeClient());
     // 设置WebView属性，能够执行Javascript脚本
        wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);  
        wv1.getSettings().setUseWideViewPort(true);//关键点  
		  
        wv1.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  
		      
        wv1.getSettings().setDisplayZoomControls(false);  
        wv1.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本  
        wv1.getSettings().setAllowFileAccess(true); // 允许访问文件  
        wv1.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮  
        wv1.getSettings().setSupportZoom(true); // 支持缩放  
		  
		  
		  
        wv1.getSettings().setLoadWithOverviewMode(true);  
		  
		DisplayMetrics metrics = new DisplayMetrics();  
		  getWindowManager().getDefaultDisplay().getMetrics(metrics);  
		  int mDensity = metrics.densityDpi;  
		  Log.d("maomao", "densityDpi = " + mDensity);  
		  if (mDensity == 240) {   
			  wv1.getSettings().setDefaultZoom(ZoomDensity.FAR);  
		  } else if (mDensity == 160) {  
			  wv1.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);  
		  } else if(mDensity == 120) {  
			  wv1.getSettings().setDefaultZoom(ZoomDensity.CLOSE);  
		  }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){  
			  wv1.getSettings().setDefaultZoom(ZoomDensity.FAR);   
		  }else if (mDensity == DisplayMetrics.DENSITY_TV){  
			  wv1.getSettings().setDefaultZoom(ZoomDensity.FAR);   
		  }else{  
			  wv1.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);  
		  }  
		  
		  
		/**  
		 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：  
		 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放  
		 */  
		  wv1.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);  
          wv1.loadUrl(url);
        //handle_getpl();
	 }
	 private WebViewClient getWebViewClient() {
			return new WebViewClient() {

				static final String TITLE_MASK = "daw_browser_title";
		//		final String UTIL_WEB_TITLE = WebTitles.实用工具.name();
				
				private String catchTitle(String url) {
					String lowerUrl = url.toLowerCase();
					try {
						if (lowerUrl.contains(TITLE_MASK)) {
							int index = lowerUrl.indexOf(TITLE_MASK);
							index += TITLE_MASK.length() + 1;
							String title = lowerUrl.substring(index);
							title = title.split("&")[0];
							title = URLDecoder.decode(title, "utf-8");
							return title;
						}
					} catch (UnsupportedEncodingException e) {
						Log.e("Web Activity", "catch title error", e);
					}
					return null;
				}

				private void updateTitle(String url) {
					String catchTitle = catchTitle(url);
					if (catchTitle != null) {
					//	titleTextView.setText(catchTitle);
					}
				}

				@Override
				public void onLoadResource(WebView view, String url) {
					super.onLoadResource(view, url);
					updateTitle(url);
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view,
						final String url) {
					
					// view.loadUrl(url);
					return false;
				}

				@Override
				public void onReceivedError(final WebView view,
						final int errorCode, final String description,
						final String failingUrl) {
					if (!webActivity.this.isFinishing()) {
						try {
							AlertDialog ad = new AlertDialog.Builder(
									webActivity.this)
									.setMessage("网络状态不佳，再试一次？")
									.setPositiveButton("再试一次",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													view.reload();
													Log.e("webview", "reload");
												}
											})
									.setNegativeButton("取消",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													if (view.canGoBack()) {
														view.goBack();
													} else {
														finish();
													}
													Log.e("webViewERROR:"
															+ errorCode, failingUrl
															+ ":" + description);
												}
											}).setCancelable(false).create();
							ad.setCanceledOnTouchOutside(false);
							ad.show();
						} catch (Exception e) {
						}
					}
				}
			};
		}

		private WebChromeClient getWebChromeClient() {
			return new WebChromeClient() {

				@Override
				public boolean onJsAlert(WebView view, String url, String message,
						JsResult result) {
				//	showMessageBox(message);
					result.confirm();
					return true;
				}

				@Override
				public void onProgressChanged(WebView view, int newProgress) {
				/*	if (pd == null) {
						showLoading();
					}
					if (newProgress < 100) {
						// updateLoading(newProgress);
						return;
					}
					if (newProgress >= 100) {
						hideLoading();
						return;
					}*/
				}
			};
		}
}