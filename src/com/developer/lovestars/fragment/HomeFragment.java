package com.developer.lovestars.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.qqlogin.Util;
import com.developer.lovestars.ui.RegistActivity;
import com.developer.lovestars.utils.UiUtils;
import com.developer.lovestars.widget.RoundImageView;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

@SuppressLint("HandlerLeak")
public class HomeFragment extends BaseFragment implements OnClickListener {

	private TextView homeRegist;
	private TextView userName;
	private RoundImageView userPic;
	private ImageView qqLogin;
	private TextView defaultUserName;
	private ImageView defaultUserPic;
	private View view;

	public static String mAppid = "222222";
	public static Tencent mTencent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(UiUtils.getContext(), R.layout.fragment_home, null);
		initView();

		if (mTencent == null) {
			mTencent = Tencent.createInstance(mAppid, UiUtils.getContext());
		}

		return view;
	}

	private void initView() {
		homeRegist = (TextView) view.findViewById(R.id.tv_home_regist);

		defaultUserName = (TextView) view
				.findViewById(R.id.tv_default_user_name);
		defaultUserPic = (ImageView) view
				.findViewById(R.id.iv_default_user_pic);
		userName = (TextView) view.findViewById(R.id.tv_user_name);
		userPic = (RoundImageView) view.findViewById(R.id.iv_user_pic);

		qqLogin = (ImageView) view.findViewById(R.id.iv_qq);

		homeRegist.setOnClickListener(this);
		qqLogin.setOnClickListener(this);

	}

	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread() {

						@Override
						public void run() {
							JSONObject json = (JSONObject) response;
							if (json.has("figureurl")) {
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json
											.getString("figureurl_qq_2"));
								} catch (JSONException e) {

								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}
					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			UserInfo mInfo = new UserInfo(UiUtils.getContext(),
					mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
			userName.setText("");
			userName.setVisibility(android.view.View.GONE);
			userPic.setVisibility(android.view.View.GONE);
			defaultUserName.setVisibility(android.view.View.VISIBLE);
			defaultUserPic.setVisibility(android.view.View.VISIBLE);
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				Log.d("lihao", "" + response.toString());
				if (response.has("nickname")) {
					try {
						
						defaultUserName.setVisibility(View.INVISIBLE);
						userName
								.setVisibility(android.view.View.VISIBLE);
						userName.setText(response.getString("nickname"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else if (msg.what == 1) {
				Bitmap bitmap = (Bitmap) msg.obj;
				userPic.setImageBitmap(bitmap);
				defaultUserPic.setVisibility(View.INVISIBLE);
				userPic.setVisibility(android.view.View.VISIBLE);
			}
		}

	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_home_regist:
			Intent registIntent = new Intent(UiUtils.getContext(),
					RegistActivity.class);
			startActivity(registIntent);
			break;
		case R.id.iv_qq:
			onClickLogin();
			break;
		}
	}

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", loginListener);
		} else {
			mTencent.logout(UiUtils.getContext());
			updateUserInfo();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUEST_LOGIN
				|| requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data, null);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref",
					"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
			updateUserInfo();
		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Toast.makeText(UiUtils.getContext(), "返回为空,登录失败",
						Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				Toast.makeText(UiUtils.getContext(), "返回为空,登录失败",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(UiUtils.getContext(), "登录成功:" + response.toString(),
					Toast.LENGTH_SHORT).show();
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Toast.makeText(UiUtils.getContext(), "onError: " + e.errorDetail,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(UiUtils.getContext(), "onCancel: ",
					Toast.LENGTH_SHORT).show();
		}
	}
}
