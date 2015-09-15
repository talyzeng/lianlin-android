package com.cmcc.attendance.utils;

import java.util.ArrayList;

import android.content.Context;

public class ImageUtil {
	public static ArrayList<Integer> names2Ids(int resourceId,Context mContext){
		ArrayList<Integer> list = null;
		String[] ss = mContext.getResources().getStringArray(resourceId);
		if(ss.length>0){
			list = new ArrayList<Integer>();
			for(int i=0; i<ss.length; i++){
				int indentify = mContext.getResources().getIdentifier(getImageName(ss[i]),"drawable","com.cmcc.attendance.activity");  //R绫绘墍鍦ㄧ殑鍖呭悕
				list.add(indentify);
			}
		}
		return list;
	}
	
	private static String getImageName(String str){
		String newStr = "";
		if(str!=null){
			newStr = str.substring(str.lastIndexOf("/")+1, str.indexOf("."));
		}
		return newStr;
	}
}
