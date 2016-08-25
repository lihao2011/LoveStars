package com.developer.lovestars;

import com.developer.lovestars.utils.CacheUtils;
import com.developer.lovestars.utils.UiUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class SplashActivity extends Activity implements OnClickListener {

	public static final String IS_APP_FIRST_OPEN = "is_app_first_open";// 是否应用第一次打开
	private boolean isAppFirstOpen;
	private TextView tv_jump;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		final View splashView = View.inflate(this, R.layout.activity_splash,
				null);
		setContentView(splashView);

		// 渐变展示启动屏
		AlphaAnimation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(3000);
		splashView.startAnimation(alpha);
		alpha.setAnimationListener(new SplashAnimationListener());

		isAppFirstOpen = CacheUtils.getBoolean(UiUtils.getContext(),
				IS_APP_FIRST_OPEN, true);

		tv_jump = (TextView) findViewById(R.id.tv_jump);
		tv_jump.setOnClickListener(this);
	}

	class SplashAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
			if (!isAppFirstOpen) {
				tv_jump.setClickable(true);
				tv_jump.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onAnimationEnd(Animation animation) {

			if (isAppFirstOpen) {
				startActivity(new Intent(SplashActivity.this,
						GuideActivity.class));// 应用第一次打开，跳到引导界面
			} else {
				tv_jump.setClickable(true);
				tv_jump.setVisibility(View.VISIBLE);
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));// 应用不是第一次打开，跳到主界面
			}
			finish();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
		}
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(SplashActivity.this, MainActivity.class));// 点击“跳过”，跳到主界面
		finish();
	}
}
