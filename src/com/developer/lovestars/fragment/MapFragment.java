package com.developer.lovestars.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.utils.UiUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends BaseFragment {

	private TextureMapView mMapView;
	private BaiduMap mBaiduMap;

	// 定位相关
	private LocationClient mLocClient;
	private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker;
	public MyLocationListenner myListener = new MyLocationListenner();

	// UI相关
	boolean isFirstLoc = true; // 是否首次定位

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		mMapView = (TextureMapView) view.findViewById(R.id.mapView);
		mBaiduMap = mMapView.getMap();
		
		//初始化地图定位
		initLocation();
		
		return view;
	}

	private void initLocation() {
		// // 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		//
		// // 卫星地图
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		//
		// // 空白地图,
		// //
		// 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

		// 开启交通图
		// mBaiduMap.setTrafficEnabled(true);
		// 开启热力图
		// mBaiduMap.setBaiduHeatMapEnabled(true);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(UiUtils.getContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的结果包含手机的方向
		mLocClient.setLocOption(option);
		mLocClient.start();

		mCurrentMode = LocationMode.NORMAL;// 正常
		// mCurrentMode = LocationMode.COMPASS;//罗盘
		// mCurrentMode = LocationMode.FOLLOWING;//跟随

		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.location_map);
		MyLocationConfiguration config = new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker);
		mBaiduMap.setMyLocationConfigeration(config);
		// 当不需要定位图层时关闭定位图层
		// mBaiduMap.setMyLocationEnabled(false);

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}

			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);
			Log.d("locData", "locData = " + locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory
						.newMapStatus(builder.build()));
			}
		}
	}

	@Override
	public void onDestroy() {
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
		super.onPause();
	}
}