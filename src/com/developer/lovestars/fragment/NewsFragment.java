package com.developer.lovestars.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.bean.TitleJsonBean;
import com.developer.lovestars.bean.TitleJsonBean.Data_json;
import com.developer.lovestars.pager.NewsPager;
import com.developer.lovestars.utils.CreateJsonObject;
import com.developer.lovestars.utils.JsonUtils;
import com.developer.lovestars.utils.UiUtils;
import com.developer.lovestars.widget.PagerSlidingTabStrip;
import com.google.gson.Gson;

public class NewsFragment extends BaseFragment {

	private PagerSlidingTabStrip newsPagerTab;
	private ViewPager newsViewPager;
	private List<NewsPager> newsPagerList;
	private List<Data_json> data_json;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(UiUtils.getContext(), R.layout.fragment_news, null);
		newsPagerTab = (PagerSlidingTabStrip) view
				.findViewById(R.id.news_pagerSlidingTabStrip);
		newsViewPager = (ViewPager) view.findViewById(R.id.news_viewPager);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

//		JSONObject createJson = new JsonUtils().readTitle();
		JSONObject createJson = new CreateJsonObject().createJson();
		String createString = createJson.toString();
		Gson gson = new Gson();
		TitleJsonBean titleJsonBean = gson.fromJson(createString, TitleJsonBean.class);
		data_json = titleJsonBean.data_json;
		
		addTab();

		newsPagerList = new ArrayList<NewsPager>();
		for (int i = 0; i < data_json.size(); i++) {
			newsPagerList.add(new NewsPager(UiUtils.getContext(),
					data_json.get(i)));
		}

		NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter();
		newsViewPager.setAdapter(newsPagerAdapter);
		newsViewPager.setOffscreenPageLimit(1);
		newsPagerTab.setViewPager(newsViewPager);
	}

	private void addTab() {

		for (int i = 0; i < data_json.size(); i++) {
			View view = View.inflate(getActivity(), R.layout.item_pagertab,
					null);
			TextView textView = (TextView) view
					.findViewById(R.id.tv_pagertab_item);
			textView.setText(data_json.get(i).title);
			newsPagerTab.addTab(view);
		}
	}

	class NewsPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return data_json.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			NewsPager newsPager = newsPagerList.get(position);
			container.addView(newsPager.rootView);
			newsPager.initData();// 更新布局
			return newsPager.rootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	@Override
	public void onDestroyView() {
		if (view != null) {  
	        ((ViewGroup) view.getParent()).removeView(view);  
	    }  
		super.onDestroyView();
	}
}