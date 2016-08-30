package com.developer.lovestars.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.pager.NewsPager;
import com.developer.lovestars.utils.UiUtils;
import com.developer.lovestars.widget.PagerSlidingTabStrip;

public class NewsFragment extends BaseFragment {

	private PagerSlidingTabStrip newsPagerTab;
	private ViewPager newsViewPager;
	private String[] newsTabTitle;
	private List<NewsPager> newsPagerList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = UiUtils.inflateView(R.layout.fragment_news);
		newsPagerTab = (PagerSlidingTabStrip) view
				.findViewById(R.id.news_pagerSlidingTabStrip);
		newsViewPager = (ViewPager) view.findViewById(R.id.news_viewPager);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		newsTabTitle = UiUtils.getStringArray(R.array.news_TabTitle);

		addTab();

		newsPagerList = new ArrayList<NewsPager>();
		for (int i = 0; i < newsTabTitle.length; i++) {
			newsPagerList.add(new NewsPager(UiUtils.getContext(),newsTabTitle[i]));
		}

		NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter();
		newsViewPager.setAdapter(newsPagerAdapter);
		newsPagerTab.setViewPager(newsViewPager);
	}

	private void addTab() {

		for (int i = 0; i < newsTabTitle.length; i++) {
			View view = View.inflate(getActivity(), R.layout.item_pagertab,
					null);
			TextView textView = (TextView) view
					.findViewById(R.id.tv_pagertab_item);
			textView.setText(newsTabTitle[i]);
			newsPagerTab.addTab(view);
		}
	}

	class NewsPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return newsTabTitle.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			NewsPager newsPager = newsPagerList.get(position);
			container.addView(newsPager.rootView);
			newsPager.initData();// 更新布局
			return newsPager.rootView;
			//-----------
//			TextView textView = new TextView(UiUtils.getContext());
//			textView.setText(newsTabTitle[position]);
//			container.addView(textView);
//			return textView;
		}
	}
}