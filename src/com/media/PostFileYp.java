package com.media;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class PostFileYp {
	   //上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定
    public static String post(String actionUrl, Map<String, String> params,
            Map<String, File> files) throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();        
        
        conn.setReadTimeout(5 * 1000); 
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); 
        conn.setRequestMethod("POST");  //Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);

        
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }    
        
        DataOutputStream outStream = new DataOutputStream(conn
                .getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {

                 
            	
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1
                        .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
                
                
            }
        
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
      
        String resultData="";
        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"GBK"));
        String inputLine=null;
        while((inputLine=reader.readLine())!=null){
         resultData+=inputLine+"\n";
        }
        reader.close();
        
        conn.disconnect();
        
        if(resultData!=""){
       //  Log.v("返回结果：",resultData);
        }else{
         System.out.println("Sorry,the content is null");
        }
        
        
        // 得到响应码
     /*   int res = conn.getResponseCode(); 
        InputStream in = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(in);
        
        BufferedReader bufReader = new BufferedReader(isReader);
        
        String line = null;
        String data = "OK";
        while((line = bufReader.readLine())==null)
            data += line;
        
        if (res == 200) {
            int ch;
            StringBuilder sb2 = new StringBuilder();
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
            Log.v("返回1:",sb2.toString());
        }
        outStream.close();
        conn.disconnect();*/
        return  resultData;
    }

}