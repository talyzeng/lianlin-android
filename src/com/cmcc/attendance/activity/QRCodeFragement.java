package com.cmcc.attendance.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lianlin.R;

public class QRCodeFragement extends BaseFragement {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragement_sy, container, false);
		setContentView(rootView);
		return rootView;
	}
}
