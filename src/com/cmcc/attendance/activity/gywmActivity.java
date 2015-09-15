package com.cmcc.attendance.activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lianlin.R;

public class gywmActivity extends BaseActivity {
	
	ImageView btn_back;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		    btn_back=(ImageView)findViewById(R.id.about_btn_return);
	        btn_back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
				 finish();
					
				}
	        	
	        });
	}
}
