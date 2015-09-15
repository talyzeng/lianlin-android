package com.cmcc.attendance.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cmcc.attendance.bean.BaseBean;
import com.cmcc.attendance.utils.PrintLog;
/**
 * 
 * Title:       RootAdapter.java
 * Description: 所有适配器根基类
 * Copyright:   Copyright (c) 2011
 * Company:     Ptyt Teachnology
 * CreateTime:  2014-11-12
 * 
 * @author      yuguocheng
 * @version     Ver1.0
 * @since       Ver1.0
 */
public abstract class RootAdapter extends BaseAdapter {
	public static String TAG = RootAdapter.class.getSimpleName();
	private List<BaseBean> mList;
	public Context mContext;
	private LayoutInflater mInflater;
	
	public RootAdapter(List<BaseBean> list,Context context){
		TAG = getClass().getSimpleName();
		this.mList = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		PrintLog.d(TAG, "total count:"+mList.size());
		return mList!=null?mList.size():0;
	}

	@Override
	public Object getItem(int location) {
		return mList!=null?mList.get(location):null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = newView(mInflater);
		}
		bindView(convertView,(BaseBean) getItem(position));
		return convertView;
	}
	
	protected abstract  View newView(LayoutInflater mInflater);
	protected abstract  void bindView(View convertView,BaseBean obj);
}
