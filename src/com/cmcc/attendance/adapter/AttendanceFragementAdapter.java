package com.cmcc.attendance.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianlin.R;
import com.cmcc.attendance.bean.AttendanceBean;
import com.cmcc.attendance.bean.BaseBean;
import com.cmcc.attendance.utils.DensityUtil;

public class AttendanceFragementAdapter extends RootAdapter {
	public AttendanceFragementAdapter(List<BaseBean> list, Context context) {
		super(list, context);
	}


	@Override
	protected void bindView(View convertView, BaseBean obj) {
		//AbsListView.LayoutParams param = new AbsListView.LayoutParams(DensityUtil.px2dip(mContext, LayoutParams.FILL_PARENT),DensityUtil.px2dip(mContext, 100));
		//convertView.setLayoutParams(param);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_item_title);
		ImageView icon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
		AttendanceBean attBean = (AttendanceBean) obj;
		textView.setText(attBean.getAttendanceName());
		icon.setImageResource(attBean.getAttendanceType());
	}


	@Override
	protected View newView(LayoutInflater mInflater) {
		return mInflater.inflate(R.layout.item_attendance_fragement,null);
	}

}
