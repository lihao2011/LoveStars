package com.developer.lovestars.utils;

import java.util.HashMap;

import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.fragment.ChatFragment;
import com.developer.lovestars.fragment.HomeFragment;
import com.developer.lovestars.fragment.MapFragment;
import com.developer.lovestars.fragment.NewsFragment;
import com.developer.lovestars.fragment.NotesFragment;

public class FragmentFactory {

	// 通过一个HashMap，将已经创建出来的fragment进行保存，以便下次使用
	private static HashMap<Integer, BaseFragment> savedFragment = new HashMap<Integer, BaseFragment>();

	public static BaseFragment getFragment(int position) {
		BaseFragment fragment = savedFragment.get(position);
		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new NewsFragment();
				break;
			case 1:
				fragment = new ChatFragment();
				break;
			case 2:
				fragment = new NotesFragment();
				break;
			case 3:
				fragment = new MapFragment();
				break;
			case 4:
				fragment = new HomeFragment();
				break;
			}
			savedFragment.put(position, fragment);
		}
		return fragment;
	}
}
