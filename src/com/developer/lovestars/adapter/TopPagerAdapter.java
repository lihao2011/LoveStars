package com.developer.lovestars.adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.developer.lovestars.utils.BitmapCache;
import com.developer.lovestars.utils.UiUtils;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

public class TopPagerAdapter extends PagerAdapter {

	private List<String> mTopPicList;
	private ImageLoader mImageLoader;

	public TopPagerAdapter(List<String> topPicList) {
		this.mTopPicList = topPicList;
		RequestQueue mQueue = Volley.newRequestQueue(UiUtils.getContext());
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return mTopPicList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		NetworkImageView imageView = new NetworkImageView(UiUtils.getContext());
		container.addView(imageView);
		// 设置缩放类型
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setImageUrl(mTopPicList.get(position), mImageLoader);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
