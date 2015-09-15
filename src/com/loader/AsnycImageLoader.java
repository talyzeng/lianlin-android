package com.loader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

/** 
* User: Tom 
* Date: 13-5-13 
* Time: 下午8:07 
*/ 
public class AsnycImageLoader { 
 
//定义一个HashMap进行存放缓存的Image key为String Value为一个弱引用的一个资源文件 
// 图片 为了方便JAVA的回收 
private Map<String, SoftReference<Drawable>> imageCache = null; 
public AsnycImageLoader() { 
imageCache = new HashMap<String, SoftReference<Drawable>>(); 
} 
 
/** 
* 加载图片 
* <p>imageurl为下载资源的URL， 
* ImageCallback当缓存中不存在相关图片时时行网络下载 
* 在多线程下要使用Handler进行操作UI 利用回调接口的方式进行更新UI 
* </p> 
* @param imageUrl 
* @param callback 
* @return 
*/ 
public Drawable loadDrawable(final String imageUrl, final ImageCallback callback) { 
//进行判断ImageCache中是否存在缓存图片 
if (imageCache.containsKey(imageUrl)) { 
SoftReference<Drawable> softReference = imageCache.get(imageUrl); 
if (softReference.get() != null) { 
return softReference.get(); 
} 
} 
//定义操作UI的Handler 
  final Handler handler = new Handler() { 
   @Override 
   public void handleMessage(Message msg) { 
     callback.imageLoaded((Drawable) msg.obj); 
} 
}; 
 
  new Thread(new Runnable() { 
    @Override 
    public void run() { 
    	
     Drawable drawable = loadImageFromUrl(imageUrl); 
     if(drawable!=null){
    	 
     
    imageCache.put(imageUrl, new SoftReference<Drawable>(drawable)); 
    Message message = handler.obtainMessage(0, drawable); 
      handler.sendMessage(message); 
     }else{
    	 //图片空
     }
    } 
   }).start(); 
return null; 
} 
 
//根据URL地址进行获取资源 
protected Drawable loadImageFromUrl(String imageUrl) { 

	
	
	try {
		URL url;
		url = new URL(imageUrl);
	
	InputStream is = url.openStream();
	Drawable a=Drawable.createFromStream(is, "src"); 
	
	
     return a; 
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
} 
 
//回调接口 
public interface ImageCallback { 
public abstract void imageLoaded(Drawable drawable); 
} 
} 