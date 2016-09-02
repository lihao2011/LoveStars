package com.developer.lovestars.fragment;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
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

//	private MapView mMapView;
	SupportMapFragment mMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(UiUtils.getContext());
		View view = View.inflate(UiUtils.getContext(), R.layout.fragment_map,
				null);
		MapStatus.Builder builder = new MapStatus.Builder();
		BaiduMapOptions bo = new BaiduMapOptions().mapStatus(builder.build())
				.compassEnabled(true).zoomControlsEnabled(true);
		mMap = SupportMapFragment.newInstance(bo);
		FragmentManager manager = getFragmentManager();
		manager.beginTransaction().add(R.id.map, mMap, "map_fragment").commit();
//		mMapView = mMap.getMapView();
//		BaiduMap mBaiduMap = mMapView.getMap();
		return view;
	}

	@Override
	public void onResume() {
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
//		mMapView.onDestroy();
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
	}
}