package com.huison.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPatch;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.attendance.activity.BaseActivity;
import com.lianlin.R;
//import com.nostra13.universalimageloader.utils.L;


public class Chuli {

	//http://aigou.fshjjz.com/App/AppPost.aspx?action=GetPuTongProductDetail&PuTongProductId=1

	public static String yuming="http://api.staging.ll100.com/";

	
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog c_pg(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;

	}
	
	
	
	
	
	
	//创建一个http客户端    (GET)
/*	 HttpClient client=new DefaultHttpClient();  //创建一个GET请求 
	 HttpGet httpGet=new HttpGet(Chuli.yuming+"/login_mo/");  //向服务器发送请求并获取服务器返回的结果  
	 HttpResponse response=client.execute(httpGet);  //返回的结果可能放到InputStream，http Header中等。  
	 InputStream inputStream=response.getEntity().getContent();  
	 Header[] headers=response.getAllHeaders();      */
	
	//创建一个http客户端  (POST)
	/*HttpClient client=new DefaultHttpClient();  //创建一个POST请求  
	HttpPost httpPost=new HttpPost("http://www.store.com/product");  //组装数据放到HttpEntity中发送到服务器  
	final List dataList = new ArrayList();  dataList.add(new BasicNameValuePair("productName", "cat"));  
	dataList.add(new BasicNameValuePair("price", "14.87"));  
	HttpEntity entity = new UrlEncodedFormEntity(dataList, "UTF-8");  
	httpPost.setEntity(entity);  //向服务器发送POST请求并获取服务器返回的结果，可能是增加成功返回商品ID，或者失败等信息  
	HttpResponse response=client.execute(httpPost);  */
	
	
	//创建一个http客户端  (PUT)
	/*HttpClient client=new DefaultHttpClient();  //创建一个PUT请求  
	HttpPut httpPut=new HttpPut("http://www.store.com/product/1234");  //组装数据放到HttpEntity中发送到服务器  
	final List dataList = new ArrayList();  
	dataList.add(new BasicNameValuePair("price", "11.99"));  
	HttpEntity entity = new UrlEncodedFormEntity(dataList, "UTF-8");  
	httpPut.setEntity(entity);  //向服务器发送PUT请求并获取服务器返回的结果，可能是修改成功，或者失败等信息  
	HttpResponse response=client.execute(httpPut);   
	*/
	
	//创建一个http客户端 (DELETE)
/*	HttpClient client=new DefaultHttpClient();  //创建一个DELETE请求  
	HttpDelete httpDelete=new HttpDelete("http://www.store.com/product/1234");  //向服务器发送DELETE请求并获取服务器返回的结果，可能是删除成功，或者失败等信息 
	HttpResponse response=client.execute(httpDelete);  */
	
	
	
	public static Bitmap compressImageFromFile(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;//  
        float ww = 480f;//  
        int be = 1;  
        if (w > h && w > ww) {  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置采样率  
          
        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设  
        newOpts.inPurgeable = true;// 同时设置才会有效  
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  
          
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);   
        return bitmap;  
    }

        public static void compressBmpToFile(Bitmap bmp,File file){  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        int options = 90;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        while (baos.toByteArray().length / 1024 > 100) {   
            baos.reset();  
            options -= 10;  
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        }  
        try {  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

	
	//图片压缩
	public static Bitmap comp(Bitmap image) {   
	       
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();          
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);   
	    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出     
	        baos.reset();//重置baos即清空baos   
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中   
	    }   
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());   
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();   
	    //开始读入图片，此时把options.inJustDecodeBounds 设回true了   
	    newOpts.inJustDecodeBounds = true;   
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);   
	    newOpts.inJustDecodeBounds = false;   
	    int w = newOpts.outWidth;   
	    int h = newOpts.outHeight;   
	    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为   
	    float hh = 1200f;//这里设置高度为800f   
	    float ww = 720f;//这里设置宽度为480f   
	    
    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可   
	    int be = 1;//be=1表示不缩放   
	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放   
	        be = (int) (newOpts.outWidth / ww);   
	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放   
	        be = (int) (newOpts.outHeight / hh);   
	    }   
	    if (be <= 0)   
	        be = 1;   
	    newOpts.inSampleSize = be;//设置缩放比例   
	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了   
	    isBm = new ByteArrayInputStream(baos.toByteArray());   
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);   
	    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩   
	}  

	public static Bitmap compressImage(Bitmap image) {   
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中   
        int options = 100;   
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩          
            baos.reset();//重置baos即清空baos   
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中   
            options -= 10;//每次都减少10   
        }   
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中   
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片   
        return bitmap;   
    }  

	
	public static String md5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {

        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    /**
     * ECB解密,不要IV
     * @param key 密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data)
            throws Exception {

        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, deskey);

        byte[] bOut = cipher.doFinal(data);

        return bOut;

    }

    /**
     * CBC加密
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {

        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    /**
     * CBC解密
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {

        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);

        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] bOut = cipher.doFinal(data);

        return bOut;

    }
	
	public static ArrayList<String> jssj(String time1,String time2){
		Log.v("time1",time1);
		Log.v("time2",time2);
		ArrayList<String> result=new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try
		{
		Date d1 = df.parse(time1);
		Date d2 = df.parse(time2);
		long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		result.add(String.valueOf(days));
		result.add(String.valueOf(hours));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static String bmptoString(Bitmap bitmap){
		//��Bitmapת�����ַ�
		String string=null;
		ByteArrayOutputStream bStream=new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG,100,bStream);
		byte[]bytes=bStream.toByteArray();
		string=Base64.encodeToString(bytes,Base64.DEFAULT);
		return string;
		}
	
	
	//根据long型的数据获取时间值   
	public static String getNormalTime(long value)  
	{  
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;  
	    String time = format.format(new Date(value)) ;  
	    return time;  
	}  

	
		public static Bitmap drawableToBitmap(Drawable drawable) {       

	        Bitmap bitmap = Bitmap.createBitmap(

	                                        drawable.getIntrinsicWidth(),

	                                        drawable.getIntrinsicHeight(),

	                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

	                                                        : Bitmap.Config.RGB_565);

	                                   
	        Canvas canvas = new Canvas(bitmap);

	        //canvas.setBitmap(bitmap);

	        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

	        drawable.draw(canvas);

	        return bitmap;

	}

		public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();
	        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, w, h);
	        final RectF rectF = new RectF(rect);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	        return output;
	    }
		
	public static double distanceByLngLat(double lng1, double lat1,
			double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	// 从网络地址转换为bitmap对象图片
	public static Bitmap returnBMP(String url) {
		try {
			Log.v("����returnBMP��ַ", url);

			URL myFileUrl = null;
			Bitmap bitmap = null;
			try {
				myFileUrl = new URL(url);
			} catch (MalformedURLException e) {

				// e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}

			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	
	//����ѹ��ͼƬ
	public static void copyfile(File fromFile, File toFile, Boolean rewrite) {

		if (!fromFile.exists()) {
			return;
		}

		if (!fromFile.isFile()) {
			return;
		}
		if (!fromFile.canRead()) {
			return;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}

		Bitmap temp = null;
		try {

			temp = readBMP.createImageThumbnail(fromFile.getPath(), 800);

			// FileInputStream fosfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);
			//
			// byte[] bt = new byte[1024];
			// int c;
			// while((c=fosfrom.read(bt)) > 0){
			// fosto.write(bt,0,c);
			// }
			// //�ر����롢�����
			// fosfrom.close();
			if (temp != null) {
				temp.compress(CompressFormat.JPEG, 85, fosto);
				temp.recycle();
				temp = null;
			}
			fosto.flush();
			fosto.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	// ����BITMAP
	public static Bitmap zoomBMP2(Bitmap bitmap, int width, int height, int md) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight() - 1;

		Matrix matrix = new Matrix();
		// float scaleWidth = ((float) width / w);
		// float scaleHeight = ((float) height / h);
		// matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width * md,
				(width / (w / h)) * md, matrix, true);
		return newbmp;
	}

	// ����BITMAP
	public static Bitmap zoomBMP(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight() - 1;

		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleHeight, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	
		//�Ƚ�ʱ���С
		 public static Boolean BJDX(String DATE1,String DATE2 ){
		    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		        try { 
		            Date dt1 = df.parse(DATE1); 
		            Date dt2 = df.parse(DATE2); 
		            if (dt1.getTime() > dt2.getTime()) { 
		                System.out.println("dt1 ��dt2ǰ"); 
		                return false; 
		            } else if (dt1.getTime() < dt2.getTime()) { 
		                System.out.println("dt1��dt2��"); 
		                return true; 
		            } else { 
		            	//���ʱ��������
		                return true; 
		            } 
		        } catch (Exception exception) { 
		            exception.printStackTrace(); 
		        }
				return true; 
		 	
		    }
	
		
		 public static String httpPatch3(String path, List<NameValuePair> params,String token) {
  
			 String result="";
			 return result;
		
			 

			    }
		 
		 public static String httpPatch2(String path, List<NameValuePair> params,String token) {

				DefaultHttpClient httpClient = null;

			    HttpPatch httpPost=null;

				HttpEntity httpEntity;

				HttpResponse httpResponse;

				

			        String ret = "none";

			       try {

			            httpPost = new org.apache.http.client.methods.HttpPatch(path);
			              httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			              
			            httpPost.setEntity(httpEntity);
			            httpPost.addHeader("Authorization", "Bearer "+token);    
			        
			            //第一次一般是还未被赋值，若有值则将SessionId发给服务器
		                     try{
		                    	 Log.v("sessionid:","kk "+BaseActivity.sessionid);
		                     }catch(Exception e){
		                    	 e.printStackTrace();
		                     }
			        /*    if(null != BaseActivity.sessionid){

			                httpPost.setHeader("Cookie", "PHPSESSID=" + BaseActivity.sessionid);

			            } */

			            httpClient = new DefaultHttpClient();
			          
			        } catch (UnsupportedEncodingException e) {

			            e.printStackTrace();

			        }

			        try {

			            httpResponse = httpClient.execute(httpPost);
			            httpResponse.addHeader("Authorization", "Bearer "+token);    
			               if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			                HttpEntity entity = httpResponse.getEntity();

			                ret = EntityUtils.toString(entity);

			                org.apache.http.client.CookieStore mCookieStore = httpClient.getCookieStore();

			                List<Cookie> cookies = mCookieStore.getCookies();

			                for (int i = 0; i < cookies.size(); i++) {

			                     //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值

			                     if ("PHPSESSID".equals(cookies.get(i).getName())) {

			                    	 BaseActivity.sessionid = cookies.get(i).getValue();

			                        break;

			                    }



			                }

			            }

			        } catch (ClientProtocolException e) {

			            e.printStackTrace();

			        } catch (IOException e) {

			            e.printStackTrace();

			        }

			        return ret;

			    }

		 
		 public static String HttpPatch(String url,String token) {  
			  String rev ="err";
			    try {  
			        HttpClient httpclient = new DefaultHttpClient();  
			    	HttpPatch get= null;
			        //HttpPatch get=new org.apache.http.client.methods.HttpPatch(url);
			        //添加http头信息     
			    	 get=new HttpPatch(url);
			        get.addHeader("Authorization", "Bearer "+token);  
			       
			        HttpResponse response;  
			        response = httpclient.execute(get);  
			        int code = response.getStatusLine().getStatusCode();  
			        //检验状态码，如果成功接收数据    
			        if (code == 200) {  
			        	Log.v("返回1：",response.getEntity().toString());
			            rev = EntityUtils.toString(response.getEntity());          
			             
			        } else{ 
			        	Log.v("返回2：",response.toString());
			        }
			    } catch (Exception e) {       
			    }
				return rev;  
			}  

		 
		 public static String HttpGetData(String url,String token) {  
			  String rev ="";
			    try {  
			        HttpClient httpclient = new DefaultHttpClient();  
			       
			        HttpGet get = new HttpGet(url);  
			        //添加http头信息     
			        get.addHeader("Authorization", "Bearer "+token);  
			       
			        HttpResponse response;  
			        response = httpclient.execute(get);  
			        int code = response.getStatusLine().getStatusCode();  
			        //检验状态码，如果成功接收数据    
			        if (code == 200) {  
			        	Log.v("返回1：",response.getEntity().toString());
			            rev = EntityUtils.toString(response.getEntity());          
			             
			        } else{ 
			        	Log.v("返回2：",response.toString());
			        }
			    } catch (Exception e) {       
			    }
				return rev;  
			}  

		 
		 public static String getHTML(String path) throws Exception {  
		        URL url = new URL(path);  
		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		        conn.setRequestMethod("GET");  
		        conn.setConnectTimeout(5 * 1000);  
		        InputStream inStream = conn.getInputStream();//通过输入流获取html数据   
		        byte[] data = readInputStream(inStream);//得到html的二进制数据   
		        String html = new String(data, "gb2312");  
		        return html;  
		    }  

		 public static byte[] readInputStream(InputStream inStream) throws Exception{  
		        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		        byte[] buffer = new byte[1024];  
		        int len = 0;  
		        while( (len=inStream.read(buffer)) != -1 ){  
		            outStream.write(buffer, 0, len);  
		        }  
		        inStream.close();  
		        return outStream.toByteArray();  
		    }  

	
	

	public static String postData(String path, List<NameValuePair> params) {

		DefaultHttpClient httpClient = null;

		HttpPost httpPost = null;

		HttpEntity httpEntity;

		HttpResponse httpResponse;

		

	        String ret = "none";

	       try {

	            httpPost = new HttpPost( path);
	              httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

	            httpPost.setEntity(httpEntity);

	        
	            //第一次一般是还未被赋值，若有值则将SessionId发给服务器
                     try{
                    	 Log.v("sessionid:","kk "+BaseActivity.sessionid);
                     }catch(Exception e){
                    	 e.printStackTrace();
                     }
	            if(null != BaseActivity.sessionid){

	                httpPost.setHeader("Cookie", "PHPSESSID=" + BaseActivity.sessionid);

	            } 

	            httpClient = new DefaultHttpClient();

	        } catch (UnsupportedEncodingException e) {

	            e.printStackTrace();

	        }

	        try {

	            httpResponse = httpClient.execute(httpPost);
	               if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

	                HttpEntity entity = httpResponse.getEntity();

	                ret = EntityUtils.toString(entity);

	                org.apache.http.client.CookieStore mCookieStore = httpClient.getCookieStore();

	                List<Cookie> cookies = mCookieStore.getCookies();

	                for (int i = 0; i < cookies.size(); i++) {

	                     //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值

	                     if ("PHPSESSID".equals(cookies.get(i).getName())) {

	                    	 BaseActivity.sessionid = cookies.get(i).getValue();

	                        break;

	                    }



	                }

	            }

	        } catch (ClientProtocolException e) {

	            e.printStackTrace();

	        } catch (IOException e) {

	            e.printStackTrace();

	        }

	        return ret;

	    }


	public static String postData3(String path, List<NameValuePair> params,String token) {

		DefaultHttpClient httpClient = null;

		HttpPost httpPost = null;

		HttpEntity httpEntity;

		HttpResponse httpResponse;

		

	        String ret = "none";

	       try {

	            httpPost = new HttpPost( path);
	            httpPost.addHeader("Authorization", "Bearer "+token);    
	            httpPost.addHeader("x-http-method-override", "PATCH");  
	            httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

	            httpPost.setEntity(httpEntity);

	        
	            //第一次一般是还未被赋值，若有值则将SessionId发给服务器
                     try{
                    	 Log.v("sessionid:","kk "+BaseActivity.sessionid);
                     }catch(Exception e){
                    	 e.printStackTrace();
                     }
	            if(null != BaseActivity.sessionid){

	                httpPost.setHeader("Cookie", "PHPSESSID=" + BaseActivity.sessionid);

	            } 

	            httpClient = new DefaultHttpClient();
	              
	        } catch (UnsupportedEncodingException e) {

	            e.printStackTrace();

	        }

	        try {

	            httpResponse = httpClient.execute(httpPost);
	            httpResponse.addHeader("Authorization", "Bearer "+token);    
	            httpResponse.addHeader("x-http-method-override", "PATCH");  
	 	       
	            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

	                HttpEntity entity = httpResponse.getEntity();
	                
	                ret = EntityUtils.toString(entity);

	                org.apache.http.client.CookieStore mCookieStore = httpClient.getCookieStore();

	                List<Cookie> cookies = mCookieStore.getCookies();

	                for (int i = 0; i < cookies.size(); i++) {

	                     //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值

	                     if ("PHPSESSID".equals(cookies.get(i).getName())) {

	                    	 BaseActivity.sessionid = cookies.get(i).getValue();

	                        break;

	                    }



	                }

	            }

	        } catch (ClientProtocolException e) {

	            e.printStackTrace();

	        } catch (IOException e) {

	            e.printStackTrace();

	        }

	        return ret;

	    }

	
//POST�ӿ�
	
	public static String postData2(String www,List <NameValuePair> params){
		
		 String strResult="";
		try{
	//	List <NameValuePair> params=new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("type","1"));
			URI url= URI.create(www);
	  //post��ݵ�Url
	 
	    HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
	    HttpParams httpParameters = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);  
	    HttpConnectionParams.setSoTimeout(httpParameters, 20000);  
	    HttpClient httpClient = new DefaultHttpClient(httpParameters);
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setEntity(entity);
	    
	    
	 // header头加入session
	    httpPost.setHeader("Cookie", "PHPSESSID="+BaseActivity.sessionid);
	    
	    HttpResponse response = httpClient.execute(httpPost, localContext);
	    if(response.getStatusLine().getStatusCode()==200){

	        strResult=EntityUtils.toString(response.getEntity(),"UTF-8");
	       Log.v("ffffff",strResult);
	   return strResult;
	       
	       }

	    
	    
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return strResult;
	}
	
	public static String getHtml(String url) {
		String uriAPI = url;

		// String uriAPI =
		// "http://ciriis2013.cn937.xpidc.net/i.aspx?type=editMember&memberID=12&fullName=1111111&address=22222222&postcode=33&mobile=44&remark=";

		Log.v("url", uriAPI);
		String strResult = "";
		/* ����HTTP Get���� */
		HttpGet httpRequest = new HttpGet(uriAPI);
		try {
			/* �������󲢵ȴ���Ӧ */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* ��״̬��Ϊ200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* �� */
				strResult = EntityUtils.toString(httpResponse.getEntity());
				/* ȥû���õ��ַ� */
				strResult = strResult.replace("(/r/n|/r|/n|/n/r)", "");

			} else {
				Log.v("���Error Response: ", httpResponse.getStatusLine()
						.toString());
			}
		} catch (ClientProtocolException e) {
			Log.v("����", e.getMessage().toString());
			e.printStackTrace();

		} catch (IOException e) {

			Log.v("����", "err:"+e.getMessage().toString());
			e.printStackTrace();
		} catch (Exception e) {

			Log.v("����", "err:"+e.getMessage().toString());
			e.printStackTrace();
		}
		return strResult;
	}


	/**
	 * ����* �����ļ� ����* @param toSaveString ����* @param filePath ����
	 */
	public static void saveFile(String toSaveString, String filePath) {
		try {
			File saveFile = new File(filePath);
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			}

			FileOutputStream outStream = new FileOutputStream(saveFile);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�ļ�����
	 * 
	 * @param filePath
	 * @return �ļ�����
	 */
	public static String readFile(String filePath) {
		String str = "";
		try {
			File readFile = new File(filePath);
			if (!readFile.exists()) {
				return null;
			}
			FileInputStream inStream = new FileInputStream(readFile);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			str = stream.toString();
			stream.close();
			inStream.close();
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	// �����������
	private final static double EARTH_RADIUS = 6378137.0;

	public static double gps2m(String jd1,String wd1,String jd2,String wd2) {
		
		
		Double lat_a=Double.parseDouble(jd1);
		Double lng_a=Double.parseDouble(wd1);
		
		Double lat_b=Double.parseDouble(jd2);
		Double lng_b=Double.parseDouble(wd2);
		
		
		
		
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
		+ Math.cos(radLat1) * Math.cos(radLat2)
		* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
		}

	
    public static Drawable zoomDrawable(Drawable drawable,int width,int height)
    {
    	
    
    	
              int width1 = drawable.getIntrinsicWidth();
              int height1= drawable.getIntrinsicHeight();
              Bitmap oldbmp = drawableToBitmap(drawable); // drawableת����bitmap
              Matrix matrix = new Matrix();   // ��������ͼƬ�õ�Matrix����
           
         
          	
      		if (width1 == width
      				&& height1 == height) {
      		//	return true;
      		}

      		System.out.println("s ->w"+width+"p ->w"+width1+"/n"+"s ->h"+height + "p ->h"+height1);
      		float scaleFactor;
      		if ( width1<width
      				||  height1<height ) {//ĳһ����С����Ļ�ı� �ͷŴ�
      			// �Ŵ�
      			System.out.println("�Ŵ�");
      			scaleFactor = Math.min(
      					(float) width / (float) width1,
      					(float) height/ (float) height1);
      		} else {//�����߶�������Ļ�ı� ����СͼƬ
      			// ��С
      			System.out.println("��С");
      			scaleFactor = Math.max(
      					(float) width / (float) width1,
      					(float) height  / (float) height1);
      		}
      		System.out.println("scaleFactor:" + scaleFactor);
                  
      				matrix.postScale(scaleFactor, scaleFactor);
              Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // �����µ�bitmap���������Ƕ�ԭbitmap�����ź��ͼ
              return new BitmapDrawable(newbmp);       // ��bitmapת����drawable������
    }
    
    /** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   public static void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 

	 public static final String NAMESPACE ="http://tempuri.org/"; 
	// WebService地址 
	 public static String URL ="http://121.9.230.130:9029/OpenAPIV1.asmx"; 
	//private static final String METHOD_NAME ="Login"; 
	 public static String SOAP_ACTION ="http://tempuri.org/"; 

   public static String getWebServiec(SoapObject request,String method){
	   String a=""; 
	   try { 
        /*	SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME); 
            request.addProperty("Name",ed_username.getText().toString()); 
	            request.addProperty("Pass",ed_userpwd.getText().toString());
	            request.addProperty("LoginType","0");*/
        	AndroidHttpTransport ht =new AndroidHttpTransport(URL); 
        	ht.debug =true; 

        	SoapSerializationEnvelope envelope =new SoapSerializationEnvelope( 
        	SoapEnvelope.VER11); 

        	envelope.bodyOut = request; 
        	envelope.dotNet =true; 
        	envelope.setOutputSoapObject(request); 

        	ht.call(SOAP_ACTION+method, envelope); 

        	Object object = (Object)envelope.getResponse();
            a=object.toString();
        //	SoapObject soapObject =( SoapObject) envelope.getResponse();
        	Log.v("返回","kk  "+object.toString());
        	/*SoapObject result = (SoapObject) envelope.bodyIn; 
        	detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult"); 

        	parseWeather(detail); */

        	return a; 
        	} catch (Exception e) { 
        	e.printStackTrace(); 	
        	return a; 
        	} 
   }
   
   /**
   *
   * 查询当前日期前(后)x天的日期
   *
   * @param date 当前日期
   * @param day 天数（如果day数为负数,说明是此日期前的天数）
   * @return yyyy-MM-dd
   */
  public static String beforNumDay(Date date, int day) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(Calendar.DAY_OF_YEAR, day);
      return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

   
}
