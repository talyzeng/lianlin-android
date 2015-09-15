package com.cmcc.attendance.utils;


import android.util.Log;

public class PrintLog {
	private static final String TAG = "AttendanceSystem";
	private static boolean LOG_VERBOSE = true;
	private static boolean LOG_DEBUG = true;
	private static boolean LOG_INFO = true;
	private static boolean LOG_WARN = true;
	private static boolean LOG_ERROR = true;
	private static boolean LOG_SERROR=true;
	private static boolean LOG_SINFO=true;
	
	public static void v(String tag, String msg) {
		if (LOG_VERBOSE) {
			Log.v(TAG, "[" + tag + "]" + msg);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (LOG_VERBOSE) {
			Log.v(TAG, "[" + tag + "]" + msg, tr);
		}
	}
	
	public static void d(String tag, String msg) {
		if (LOG_DEBUG) {
			Log.d(TAG, "[" + tag + "]" + msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (LOG_DEBUG) {
			Log.d(TAG, "[" + tag + "]" + msg, tr);
		}
	}
    

	public static void i(String tag, String msg) {
		if (LOG_INFO) {
			Log.i(TAG, "[" + tag + "]" + msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (LOG_INFO) {
			Log.i(TAG, "[" + tag + "]" + msg, tr);
		}
	}

	public static void w(String tag, String msg) {
		if (LOG_WARN) {
			Log.w(TAG, "[" + tag + "]" + msg);
		}
	}

   
	public static void w(String tag, String msg, Throwable tr) {
		if (LOG_WARN) {
			Log.w(TAG, "[" + tag + "]" + msg, tr);
		}
	}

	public static void e(String tag, String msg) {
		if (LOG_ERROR) {
			Log.e(TAG, "[" + tag + "]" + msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (LOG_ERROR) {
			Log.e(TAG, "[" + tag + "]" + msg, tr);
		}
	}
	
	public static void se(String tag, String msg){
		if (LOG_SERROR) {
			System.err.println("[" + tag + "]" + msg);
		}
	}
	
	public static void si(String tag, String msg){
		if (LOG_SINFO) {
			System.out.println("[" + tag + "]" + msg);
		}
	}

}