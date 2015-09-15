package com.cmcc.attendance.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static ToastUtil toastUtil = null;
	private static Toast mToast;

	public static ToastUtil getToast() {
		if (null == toastUtil) {
			toastUtil = new ToastUtil();
		}
		return toastUtil;
	}

	public synchronized void showMessage(final Activity act, final String str,
			final int strId) {
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mToast == null) {
					mToast = Toast.makeText(act, "", Toast.LENGTH_SHORT);
				}
				if (strId == -1) {
					mToast.setText(str);
				} else {
					mToast.setText(strId);
				}
				mToast.show();
			}
		});
	}

	public synchronized void showMessage(final Context act, final String str,
			final int strId) {
		if (mToast == null) {
			mToast = Toast.makeText(act, "", Toast.LENGTH_SHORT);
		}
		if (strId == -1) {
			mToast.setText(str);
		} else {
			mToast.setText(strId);
		}
		mToast.show();
	}

	public static void showToast(final Context act, final String str,
			final int strId) {
		Toast toast = Toast.makeText(act, "", Toast.LENGTH_LONG);
		if (strId == -1) {
			toast.setText(str);
		} else {
			toast.setText(strId);
		}
		toast.show();
	}
}
