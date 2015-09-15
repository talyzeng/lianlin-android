package com.cmcc.attendance.activity;

import android.os.Bundle;
import android.view.View;
import com.lianlin.R;

public class PersonaInfoActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
	//	getmRightView().setVisibility(View.GONE);
		setTitle(getString(R.string.string_person_info_title));
		rootView.setBackgroundResource(R.color.color_home_bg);
		
	}
}
