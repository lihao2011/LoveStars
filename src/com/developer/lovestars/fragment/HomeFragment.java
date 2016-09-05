package com.developer.lovestars.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.ui.RegistActivity;
import com.developer.lovestars.utils.UiUtils;

public class HomeFragment extends BaseFragment implements OnClickListener {

	private TextView homeRegist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(UiUtils.getContext(), R.layout.fragment_home,
				null);
		
		homeRegist = (TextView) view.findViewById(R.id.tv_home_regist);
		
		homeRegist.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_home_regist:
			Intent intent = new Intent(UiUtils.getContext(), RegistActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
