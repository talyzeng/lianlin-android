package com.cmcc.attendance.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.cmcc.attendance.greendao.DaoMaster;
import com.cmcc.attendance.greendao.DaoMaster.DevOpenHelper;
import com.cmcc.attendance.greendao.DaoSession;

public class AttendanceApplication extends Application {
	private static AttendanceApplication mInstance;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mInstance == null)
			mInstance = this;

	}

	public static Context getAppContext() {
		return mInstance;
	}

	public static Resources getAppResources() {
		return getAppResources();
	}

	/**
	 * 取得DaoMaster
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster() {
		if (daoMaster == null) {
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(mInstance, "Attendance.db",null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	/**
	 * 取得DaoSession
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession() {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster();
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
}
