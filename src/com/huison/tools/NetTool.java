package com.huison.tools;

import java.io.ByteArrayOutputStream;  
 
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
 
 
public class NetTool {  
    /**  
     * ���url��������  
     * */ 
      
    public static String getHtml(String path,String encoding) throws Exception {  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setConnectTimeout(390 * 1000);  
        // �𳬹�390�롣  
        System.out.println(conn.getResponseCode());  
        if(conn.getResponseCode()==200){  
            InputStream inputStream=conn.getInputStream();  
            byte[] data=readStream(inputStream);  
            return new String(data,encoding);  
        }  
        return null;  
    }  
      
/**  
 * ��ȡָ��·���������ݡ�  
 *   
 * **/ 
    public static byte[] getImage(String urlpath) throws Exception {  
        URL url = new URL(urlpath);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setConnectTimeout(6 * 1000);  
        // �𳬹�10�롣  
        if(conn.getResponseCode()==200){  
            InputStream inputStream=conn.getInputStream();  
            return readStream(inputStream);  
        }  
        return null;  
    }  
      
    /**  
     * ��ȡ����   
     * ������  
     *   
     * */ 
    public static byte[] readStream(InputStream inStream) throws Exception {  
        ByteArrayOutputStream outstream=new ByteArrayOutputStream();  
        byte[] buffer=new byte[1024];  
        int len=-1;  
        while((len=inStream.read(buffer)) !=-1){  
            outstream.write(buffer, 0, len);  
        }  
        outstream.close();  
        inStream.close();  
          
    return outstream.toByteArray();  
}  
}  
