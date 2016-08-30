package com.developer.lovestars.pager;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BasePager;

import android.content.Context;
import android.view.View;

public class NewsPager extends BasePager {

	private String newsTitle;

	public NewsPager(Context context, String newsTabTitle) {
		super(context);
		this.newsTitle = newsTabTitle;
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.list_newspager, null);
		return view;
	}

	@Override
	public void initData() {
		super.initData();
	}
}
