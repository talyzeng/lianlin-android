package com.cmcc.attendance.activity;


import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;


import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.circleimghead.CircularImage;
import com.huison.tools.Chuli;
import com.huison.tools.Tools;
import com.lianlin.R;
import com.loader.AsnycImageLoader;


import com.huison.tools.ImageTools;

public class userFragement extends BaseFragement {	
	 Dialog pg;
	 String dl_msg;
	 private static final int SCALE = 5;//取图像设置参数
	 private String[] items = new String[] { "选择本地图片", "拍照" };
		/* 头像名称 */
		private static final String IMAGE_FILE_NAME = "headimg.png";

		/* 请求码 */
		private static final int IMAGE_REQUEST_CODE = 0;
		private static final int CAMERA_REQUEST_CODE = 1;
		private static final int RESULT_REQUEST_CODE = 2;
		String headurl;
		 CircularImage imghead;
	   Button btn_logout;
		 TextView text_name,text_class,text_school,text_username,text_phone;
		 LinearLayout l_tx,l_sjh,l_mm,l_xm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
	}	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);	
		loader2=new AsnycImageLoader();		
		rootView = inflater.inflate(R.layout.fragement_user, container, false);
		setContentView(rootView);	
		 imghead=(com.circleimghead.CircularImage)rootView.findViewById(R.id.user_imghead);
		 imghead.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					showDialog();					
				}	        	
	     });	        
		 try{
		    	Log.v("头像路径","k:"+BaseActivity.now_userimgheadurl);
		    	loadImageHead(BaseActivity.now_userimgheadurl);
		 }catch(Exception e){
		    	e.printStackTrace();
		 }   
		    
		 btn_logout=(Button)rootView.findViewById(R.id.user_btn_logout);
		 
		 /////////////////////////////////////////////////
		 //处理退出登录
		 
		 btn_logout.setOnClickListener(new OnClickListener() {				
			 @Override
			 public void onClick(View arg0) {
					  new AlertDialog.Builder(rootView.getContext()).setTitle("是否要退出？")
					     .setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Intent it=new Intent(rootView.getContext(),LoginActivity.class);
								startActivity(it);
								 AVIMClient.getInstance(BaseActivity.now_clientToken).close(new AVIMClientCallback() {
								        @Override
								        public void done(AVIMClient avimClient, AVException e) {
								          if (e == null) {
								            Log.d(TAG, "退出连接");
								          } else {
								            Toast.makeText(rootView.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
								          }
								        }
								      });
								 AVIMClient.getInstance(BaseActivity.now_channelid).close(new AVIMClientCallback() {
								        @Override
								        public void done(AVIMClient avimClient, AVException e) {
								          if (e == null) {
								            Log.d(TAG, "退出连接");
								          } else {
								            Toast.makeText(rootView.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
								          }
								        }
								      });
								System.exit(0);
							}
						})
					     .setNegativeButton("取消", null).show();
				}
			});
		    ////////////////////////////////
		 
		 //处理头像
		    l_tx=(LinearLayout)rootView.findViewById(R.id.user_l_tx);
		    l_tx.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					showDialog();
					
				}
			});
		    /////////////////
		    
		    //处理姓名
		    
		    l_xm=(LinearLayout)rootView.findViewById(R.id.user_l_xm);
		    l_xm.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					/*
                     final EditText ed_text=new EditText(rootView.getContext());
					  ed_text.setText(BaseActivity.now_myname);
					   new AlertDialog.Builder(rootView.getContext()).setTitle("请输入姓名").setView(ed_text)
					     .setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								handle_xgname(ed_text.getText().toString());
							}
						})
					     .setNegativeButton("取消", null).show();		*/
					   
					   Intent it=new Intent(rootView.getContext(),xgxmActivity.class);
					   startActivity(it);
				}
			});
		    ///////////////////////////////
		    
		    
		    //处理手机号
		    l_sjh=(LinearLayout)rootView.findViewById(R.id.user_l_sjh);
		    //l_sjh.get
		    l_sjh.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
                    /* 
					final EditText ed_text=new EditText(rootView.getContext());
					  ed_text.setText(BaseActivity.now_myphone);
					   new AlertDialog.Builder(rootView.getContext()).setTitle("请输入手机号").setView(ed_text)
					     .setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								handle_xgphone(ed_text.getText().toString());
							}
						})
					     .setNegativeButton("取消", null).show();*/
					
					//暂时不处理
					//Intent it=new Intent(rootView.getContext(),xgsjhActivity.class);
					   //startActivity(it);
					
				}
			});
		    ///////////////////////////
		    
		    //处理密码
		    l_mm=(LinearLayout)rootView.findViewById(R.id.user_l_mm);
		    l_mm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {					
							   Intent it=new Intent(rootView.getContext(),xgmmActivity.class);
							   startActivity(it);
				}
			});		    
		    
		    
		    
		    text_name=(TextView)rootView.findViewById(R.id.user_text_name);
		    text_username=(TextView)rootView.findViewById(R.id.user_text_username);
		    text_phone=(TextView)rootView.findViewById(R.id.user_text_userphone);
			
		    text_school=(TextView)rootView.findViewById(R.id.user_text_school);
		    text_class=(TextView)rootView.findViewById(R.id.user_text_class);
		    
		    if(BaseActivity.now_myphone.contains("null")){
		    	text_phone.setText("");
		    }else{
		    	text_phone.setText(BaseActivity.now_myphone);
		    }
		    
		    
		    text_name.setText(BaseActivity.now_myname);
		    text_username.setText(BaseActivity.now_myname);
		    text_school.setText(BaseActivity.now_schoolname);
		    text_class.setText(BaseActivity.now_classname);
		    //text_phone.setText(BaseActivity.now_myphone);
		    
		return rootView;
	}
	 /**********************************************头像***************************************************/
	//设置头像
	private void showDialog() {
		new AlertDialog.Builder(rootView.getContext())
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:///从本地读取图片
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1://从照相机获取图片
							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Tools.hasSdcard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}   
	
	

	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String imagePath = "";
		//结果码不等于取消时候
		if (resultCode != HomeActivity.RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE://本地选取头像
				//startPhotoZoom(data.getData());
				ContentResolver resolver = this.getActivity().getContentResolver();
				//鐓х墖鐨勫師濮嬭祫婧愬湴鍧�
				Uri originalUri = data.getData(); 				
				//获取图片真实地址				
				String[] proj = { MediaStore.Images.Media.DATA };				 
				Cursor actualimagecursor =   resolver.query(originalUri,proj,null,null,null);
				int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				imagePath = actualimagecursor.getString(actual_image_column_index);
				//结束
				//String imagePath = originalUri.getHost();
	            try {
	            	//浣跨敤ContentProvider閫氳繃URI鑾峰彇鍘熷鍥剧墖
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						//涓洪槻姝㈠師濮嬪浘鐗囪繃澶у鑷村唴瀛樻孩鍑猴紝杩欓噷鍏堢缉灏忓師鍥炬樉绀猴紝鐒跺悗閲婃斁鍘熷Bitmap鍗犵敤鐨勫唴瀛�
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
						//閲婃斁鍘熷鍥剧墖鍗犵敤鐨勫唴瀛橈紝闃叉out of memory寮傚父鍙戠敓
						photo.recycle();						
						imghead.setImageBitmap(smallBitmap);	
						//postPic(imagePath);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}  
				break;
				
			case CAMERA_REQUEST_CODE://用照相机获取头像
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()+"/"
									+ IMAGE_FILE_NAME);
					//startPhotoZoom(Uri.fromFile(tempFile));
					//灏嗕繚瀛樺湪鏈湴鐨勫浘鐗囧彇鍑哄苟缂╁皬鍚庢樉绀哄湪鐣岄潰涓�
					Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+IMAGE_FILE_NAME);
					Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
					//鐢变簬Bitmap鍐呭瓨鍗犵敤杈冨ぇ锛岃繖閲岄渶瑕佸洖鏀跺唴瀛橈紝鍚﹀垯浼氭姤out of memory寮傚父
					bitmap.recycle();
					
					//灏嗗鐞嗚繃鐨勫浘鐗囨樉绀哄湪鐣岄潰涓婏紝骞朵繚瀛樺埌鏈湴
					imghead.setImageBitmap(newBitmap);
					ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), IMAGE_FILE_NAME);//String.valueOf(System.currentTimeMillis())
					imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+IMAGE_FILE_NAME;
					//postPic(imagePath);
					break;
				} else {
					Toast.makeText(rootView.getContext(), "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					//getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	private void upload(String actionUrl, String token,String imageurl) {
			try {			
				HttpClient httpclient = new DefaultHttpClient();
			      //设置通信协议版本
			    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			       
			      //File path= Environment.getExternalStorageDirectory(); //取得SD卡的路径
			       
			      //String pathToOurFile = path.getPath()+File.separator+"ak.txt"; //uploadfile
			      //String urlServer = "http://192.168.1.88/test/upload.php"; 
			       //Chuli.yuming+BaseActivity.now_user_endpoint, BaseActivity.now_userid,
			    HttpPatch httppatch = new HttpPatch(Chuli.yuming+BaseActivity.now_user_endpoint);
			    final File file = new File(imageurl);
			   
			      @SuppressWarnings("deprecation")
				final MultipartEntity mpEntity = new MultipartEntity(); //文件传输
			      //ContentBody cbFile = new FileBody();
			      try{
			    	  ContentBody cbFile = new FileBody(file); 	
			    	  mpEntity.addPart("userfile", cbFile); 
			      }catch(Exception e){
			    	  Log.v("文件创建错误", e.toString());
			      }
			      //org.apache.http.entity.ContentType
			      //mpEntity.addPart("userfile", cbFile); // <input type="file" name="userfile" />  对应的
			      //ContentBody requestToken = new StringBody(BaseActivity.now_userid);
			      //mpEntity.addPart("authorization",requestToken);
			      httppatch.addHeader("authorization", "bearer " + BaseActivity.now_userid);
			      httppatch.setEntity(mpEntity);
			      //System.out.println("executing request " + httppatch.getRequestLine());
			      try{
			    	  HttpResponse response = httpclient.execute(httppatch);
			    	  HttpEntity resEntity = response.getEntity();
			    	//System.out.println(response.getStatusLine());//通信Ok
				      Log.v("通信没问题", response.getStatusLine().toString());
				      String json="";
				      String path="";
				      if (resEntity != null) {
				        //System.out.println(EntityUtils.toString(resEntity,"utf-8"));
				        json=EntityUtils.toString(resEntity,"utf-8");
				        JSONObject p=null;
				        try{
				            p=new JSONObject(json);
				            path=(String) p.get("path");
				        }catch(Exception e){
				            e.printStackTrace();
				        }
				      }
				      if (resEntity != null) {
				        resEntity.consumeContent();
				      }
				      Log.v("文件上传成功！ 路径：", path);
			      }catch(Exception e){
			    	  Log.v("文件上传错误", e.toString());
			      }
			      	   
			      			   
			      httpclient.getConnectionManager().shutdown();	
	         	//Log.v("上传文件成功", response.getEntity().toString());	
				
			} catch (Exception e) {
				Log.v("上传图片失败！", e.toString());
				e.printStackTrace();
			//	pg.dismiss();
			}			
	}


	public void postPic(String imagePath) {
		pg = ProgressDialog.show(rootView.getContext(), "", "正在保存头像...", true, true);
		pg=Chuli.c_pg(rootView.getContext(),"正在保存头像...");
        pg.show();
        final String picPath = imagePath;
		Thread t3= new Thread() {
		@Override
		public void run() {
				
				try{
					//String p1 = Environment.getExternalStorageDirectory() + "/" + picName;
					
					//Log.v("图片地址：",Environment.getExternalStorageDirectory() + "/" + picName);
					//Log.v("上传URL",Chuli.yuming+BaseActivity.now_user_endpoint);
					
					//toast(Chuli.yuming+BaseActivity.now_user_endpoint);
					//upload(Chuli.yuming+"users/"+BaseActivity.now_userid, BaseActivity.now_userid, p1);// 正面图片
					
					upload(Chuli.yuming+BaseActivity.now_user_endpoint, BaseActivity.now_userid, picPath);// 正面图片
					
					//String reImage = postImage(p1,Chuli.yuming+BaseActivity.now_user_endpoint,BaseActivity.now_userid);
					//Log.v("返回内容", reImage);
					cwjHandler.post(mUpdateResults_pic_success);
			   
				}catch(Exception e){
					e.printStackTrace();
					}
			 }
			};
				t3.start();										  	      	 
		}	
	private static AsnycImageLoader loader2 = null; 
	
	void loadImageHead(String url) { 

		//Log.v("图片地址：",url);
		
		Drawable cacheImage = loader2.loadDrawable(url, new AsnycImageLoader.ImageCallback() { 
		@Override 
		public void imageLoaded(Drawable drawable) { 
			try{			
					BaseActivity.now_userhead=drawable;		
	   // Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
		
		//imghead_chose.setDrawingCacheEnabled(true);
		//Bitmap phto = imghead_chose.getDrawingCache();
		//Bitmap bmp2=getRoundedCornerBitmap(montage(bmp,Chuli.drawableToBitmap(drawable,130,130),0,0));
		
					imghead.setImageDrawable(drawable);
		
			}catch(Exception e){
				e.printStackTrace();
				}
			} 
		}); 
		if (cacheImage != null) {
			BaseActivity.now_userhead=cacheImage;			
			//  Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
				
				//imghead_chose.setDrawingCacheEnabled(true);
				//Bitmap phto = imghead_chose.getDrawingCache();
			//	Bitmap bmp2=getRoundedCornerBitmap(montage(bmp,Chuli.drawableToBitmap(cacheImage,130,130),0,0));
				
			imghead.setImageDrawable(cacheImage);			
		} 

	} 

	final Handler cwjHandler = new Handler();
	final Runnable mUpdateResults_pic_success = new Runnable() {
	    public void run() {
	    	pg.dismiss();	    
	    	try{
	    	//Bitmap bit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/headimg.png");   
	    	//BitmapDrawable bd= new BitmapDrawable(getResources(), bit);
	    	//BaseActivity.now_userhead=bd;
	    	//imghead.setImageDrawable(BaseActivity.now_userhead);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }};
	/**********************************************头像***************************************************/
	    
	    
/*    
		 public void handle_xgphone(final String phone) {
			  pg=Chuli.c_pg(rootView.getContext(), "正在修改...");
	          pg.show();
		      	 Thread t3 = new Thread() {
		      	 @Override
		      	public void run() {		      		    
		             try{		            
		            		List <NameValuePair> params=new ArrayList<NameValuePair>();
			 				params.add(new BasicNameValuePair("sms_confirmation_answer",phone)); 
			 				
			 		        String a=Chuli.HttpPatch(Chuli.yuming+BaseActivity.now_user_endpoint+"/phone?sms_confirmation_answer="+phone, BaseActivity.now_userid);  
			 		       
			   		        Log.v("修改返回：","kk "+a);
		   		        	cwjHandler.post(mUpdateResults_success);
		   		            BaseActivity.now_myphone=phone;
		                  
		                } catch (Exception e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		      	 	}
		      	 };
		      	 t3.start();
		      	 }
		  	
		 
		 public void handle_xgname(final String name) {
			  pg=Chuli.c_pg(rootView.getContext(), "正在修改...");
	          pg.show();
		      	 Thread t3 = new Thread() {
		      	 @Override
		      	 public void run() {		      		    
		             try{		            
		            		List <NameValuePair> params=new ArrayList<NameValuePair>();
			 				params.add(new BasicNameValuePair("name",name)); 
			 				//params.add(new BasicNameValuePair("avatar","头像")); 
			 		        //String a=Chuli.HttpPatch(Chuli.yuming+"/v1/users/"+BaseActivity.now_userid+"?name="+name, BaseActivity.now_userid);  
			 				String a=Chuli.HttpPatch(Chuli.yuming+BaseActivity.now_user_endpoint+"?name="+name, BaseActivity.now_userid);
			 		        Log.v("修改用户信息返回：","kk "+a);			   		        
		   		        	cwjHandler.post(mUpdateResults_success);		   		        
		   		        	BaseActivity.now_myname=name;
		                } catch (Exception e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }	             
		      	
		      	 	}
		      	 };
		      	 t3.start();
		      	 }
		  	
		 
		
			 
		   final Runnable mUpdateResults_success = new Runnable() {
			    public void run() {
			    	pg.dismiss();		    	
			    	text_phone.setText(BaseActivity.now_myphone);
				    text_name.setText(BaseActivity.now_myname);			    
			    }
	 		};
*/	 		
	 protected void toast(String str) {
	 	Toast.makeText(rootView.getContext(), str, Toast.LENGTH_SHORT).show();
	 }
	
}
