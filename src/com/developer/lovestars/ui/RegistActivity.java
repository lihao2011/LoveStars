package com.developer.lovestars.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseActivity;

public class RegistActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("注册");
	}
}
