package com.developer.lovestars;

import java.util.ArrayList;

import com.developer.lovestars.utils.CacheUtils;
import com.developer.lovestars.utils.UiUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class GuideActivity extends Activity implements OnClickListener {

	private ViewPager vp_bg_guide;
	private LinearLayout ll_guide_points;
	private ImageView iv_guide_redPoint;
	private TextView tv_guide_start;
	private ArrayList<ImageView> imgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_guide);
		super.onCreate(savedInstanceState);

		init();
	}

	private void init() {
		vp_bg_guide = (ViewPager) findViewById(R.id.vp_bg_guide);
		ll_guide_points = (LinearLayout) findViewById(R.id.ll_guide_points);
		iv_guide_redPoint = (ImageView) findViewById(R.id.iv_guide_redPoint);
		tv_guide_start = (TextView) findViewById(R.id.tv_guide_start);

		// 准备数据
		initData();
		// 设置数据适配器
		vp_bg_guide.setAdapter(new GuideAdapter());
		// 监听ViewPager
		vp_bg_guide.setOnPageChangeListener(new GuideOnPageChangeListener());
		// 开始体验按钮添加监听事件
		tv_guide_start
				.setOnClickListener((android.view.View.OnClickListener) this);
	}

	class GuideOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// 红点移动距离=手指移动的距离/屏幕宽度 * 两个灰点的间距 = positionOffset * 两个灰点的间距
			int redPointX = (int) ((positionOffset + position) * UiUtils
					.dip2px(16));
			// 移动红点 通过设置左边距
			android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) iv_guide_redPoint
					.getLayoutParams();
			layoutParams.leftMargin = redPointX;
			iv_guide_redPoint.setLayoutParams(layoutParams);

		}

		@Override
		public void onPageSelected(int position) {
			// 当选中最后一页时，显示按钮
			if (position == imgs.size() - 1) {
				tv_guide_start.setVisibility(View.VISIBLE);
			} else {
				tv_guide_start.setVisibility(View.GONE);
			}

		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imgs.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = imgs.get(position);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	private void initData() {
		int[] imgIds = new int[] { R.drawable.bc_guide_1,
				R.drawable.bc_guide_2, R.drawable.bc_guide_3,
				R.drawable.bc_guide_4 };
		imgs = new ArrayList<ImageView>();
		for (int i = 0; i < imgIds.length; i++) {
			ImageView imageView = new ImageView(UiUtils.getContext());
			imageView.setBackgroundResource(imgIds[i]);
			imgs.add(imageView);

			// 创建灰点
			ImageView point = new ImageView(UiUtils.getContext());
			point.setBackgroundResource(R.drawable.guide_point_normal);
			int dip2px = UiUtils.dip2px(8);
			// 设置宽高
			LayoutParams params = new LayoutParams(dip2px, dip2px);
			if (i != 0) {
				params.leftMargin = dip2px;
			}
			point.setLayoutParams(params);

			ll_guide_points.addView(point);
		}
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
		// 保存应用是否打开过
		CacheUtils.putBoolean(getApplicationContext(),
				SplashActivity.IS_APP_FIRST_OPEN, false);
		finish();
	}
}
