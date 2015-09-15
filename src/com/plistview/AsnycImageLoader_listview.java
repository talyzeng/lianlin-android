package com.plistview;

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
* Time: ����8:07 
*/ 
public class AsnycImageLoader_listview { 
 
//����һ��HashMap���д�Ż����Image keyΪString ValueΪһ�������õ�һ����Դ�ļ� 
// ͼƬ Ϊ�˷���JAVA�Ļ��� 
private Map<String, SoftReference<Drawable>> imageCache = null; 
public AsnycImageLoader_listview() { 
imageCache = new HashMap<String, SoftReference<Drawable>>(); 
} 
 
/** 
* ����ͼƬ 
* <p>imageurlΪ������Դ��URL�� 
* ImageCallback�������в��������ͼƬʱʱ���������� 
* �ڶ��߳���Ҫʹ��Handler���в���UI ���ûص��ӿڵķ�ʽ���и���UI 
* </p> 
* @param imageUrl 
* @param callback 
* @return 
*/ 
public Drawable loadDrawable(final String imageUrl, final ImageCallback callback) { 
//�����ж�ImageCache���Ƿ���ڻ���ͼƬ 
if (imageCache.containsKey(imageUrl)) { 
SoftReference<Drawable> softReference = imageCache.get(imageUrl); 
if (softReference.get() != null) { 
return softReference.get(); 
} 
} 
//�������UI��Handler 
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
    	 //ͼƬ��
     }
    } 
   }).start(); 
return null; 
} 
 
//���URL��ַ���л�ȡ��Դ 
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
 
//�ص��ӿ� 
public interface ImageCallback { 
public abstract void imageLoaded(Drawable drawable); 
} 
} 