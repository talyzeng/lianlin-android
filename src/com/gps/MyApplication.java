package com.gps;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.cmcc.attendance.activity.MessageHandler;

@SuppressLint("NewApi")
public class MyApplication extends android.app.Application {
	 public static final String KEY_CLIENT_ID = "client_id";
	 public static final String ACCESS_TOKEN = "accessToken";
	 public static final String REFRESH_TOKEN = "refreshToken";
	 public static SharedPreferences preferences;
    public void onCreate(){
    	super.onCreate(); 
      AVOSCloud.initialize(this,"in0r63iso1kql5eqf8ls8irz7uciwo162z4za40o83disz5v","in0r63iso1kql5eqf8ls8irz7uciwo162z4za40o83disz5v");
      preferences = PreferenceManager.getDefaultSharedPreferences(this);
      AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
      
     
    }
    
    String accessToken = "";
    String refreshToken = "";
    public static String getaccessToken() {
        return preferences.getString(ACCESS_TOKEN, "");
      }
    public static void setaccessToken(String id) {
        preferences.edit().putString(ACCESS_TOKEN, id).apply();
      }
    
    public static String getrefreshToken() {
        return preferences.getString(REFRESH_TOKEN, "");
      }
    public static void setrefreshToken(String id) {
        preferences.edit().putString(REFRESH_TOKEN, id).apply();
      }
    
    public static String getClientIdFromPre() {
        return preferences.getString(KEY_CLIENT_ID, "");
      }

      public static void setClientIdToPre(String id) {
        preferences.edit().putString(KEY_CLIENT_ID, id).apply();
      }

      public static AVIMClient getIMClient() {
        return AVIMClient.getInstance(getClientIdFromPre());
      }
}
 