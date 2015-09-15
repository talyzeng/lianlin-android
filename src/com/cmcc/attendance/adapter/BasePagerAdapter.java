package com.cmcc.attendance.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cmcc.attendance.activity.BaseFragement;

public class BasePagerAdapter extends FragmentPagerAdapter {
	private List<BaseFragement> mList;
	public BasePagerAdapter(FragmentManager fm,List<BaseFragement> list,Context context) {
		super(fm);
		mList = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return mList!=null?mList.get(arg0):null;
	}

	@Override
	public int getCount() {
		return mList!=null?mList.size():0;
	}
	
	

}
