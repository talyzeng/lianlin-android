package com.cmcc.attendance.activity;

import android.os.Bundle;
import android.view.View;
import com.lianlin.R;

public class RegisterActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	//	getmRightView().setVisibility(View.GONE);
		setTitle(getString(R.string.string_register_account));
		rootView.setBackgroundResource(R.color.color_home_bg);
	}
}
