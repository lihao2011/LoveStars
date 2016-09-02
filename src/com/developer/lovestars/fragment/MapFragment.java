package com.developer.lovestars.fragment;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;
import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.utils.UiUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends BaseFragment {

	SupportMapFragment mMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(UiUtils.getContext(), R.layout.fragment_map,
				null);
		MapStatus.Builder builder = new MapStatus.Builder();
		BaiduMapOptions bo = new BaiduMapOptions().mapStatus(builder.build())
				.compassEnabled(true).zoomControlsEnabled(true);
		mMap = SupportMapFragment.newInstance(bo);
		FragmentManager manager = getFragmentManager();
		manager.beginTransaction().add(R.id.map, mMap, "map_fragment").commit();
		return view;
	}

	@Override
	public void onResume() {
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		super.onResume();
	}

	@Override
	public void onPause() {
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		super.onDestroy();
	}
}