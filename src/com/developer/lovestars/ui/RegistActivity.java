package com.developer.lovestars.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseActivity;

public class RegistActivity extends BaseActivity implements OnClickListener {

	private TextView obtainCode;
	private static final String APPKEY = "16c2d6e59b146";
	private static final String APPSECRET = "2ef5d26f7415993fa1873e27908180a4";
//	private EventHandler eh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("注册");

		SMSSDK.initSDK(this, APPKEY, APPSECRET);
//		eh = new EventHandler() {
//
//			@Override
//			public void afterEvent(int event, int result, Object data) {
//
//				if (result == SMSSDK.RESULT_COMPLETE) {
//					// 回调完成
//					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//						// 提交验证码成功
//					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//						// 获取验证码成功
//					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//						// 返回支持发送验证码的国家列表
//					}
//				} else {
//					((Throwable) data).printStackTrace();
//				}
//			}
//		};
//		SMSSDK.registerEventHandler(eh); // 注册短信回调
//
		init();
	}

	private void init() {
		obtainCode = (TextView) findViewById(R.id.tv_obtain_code);
		obtainCode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_obtain_code:
			SMSSDK.getVerificationCode("86","18392185563");
			Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();

			break;
		default:
			break;
		}
	}
	
//	@Override
//	protected void onDestroy() {
//		SMSSDK.unregisterEventHandler(eh);
//		super.onDestroy();
//	}
}
