package com.cmcc.attendance.activity;


import android.os.Bundle;
import android.view.View;
import com.lianlin.R;

public class MoreActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		//getmRightView().setVisibility(View.GONE);
		setTitle(getString(R.string.string_more_title));
	}
}
