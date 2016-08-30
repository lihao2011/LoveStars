package com.developer.lovestars.base;

import android.content.Context;
import android.view.View;

public abstract class BasePager {

	protected Context mContext;
	public View rootView;

	public BasePager(Context context) {
		this.mContext = context;
		rootView = initView();
	}

	protected abstract View initView();

	public void initData() {

	}
}
