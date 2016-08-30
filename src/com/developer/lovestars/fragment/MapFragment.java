package com.developer.lovestars.fragment;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.utils.UiUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(UiUtils.getContext(), R.layout.fragment_map,
				null);
		return view;
	}
}
