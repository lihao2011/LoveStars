package com.developer.lovestars.utils;

import com.developer.lovestars.base.BaseApplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

public class UiUtils {
	// 返回context对象
	public static Context getContext() {
		return BaseApplication.getContext();
	}

	// 返回主线程的handler
	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}

	// 得到主线程的id
	public static int getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	// 得到字符串资源
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// 得到字符串数组
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	// 得到图片
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	// 获取到颜色
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	// 获取颜色的状态选择器
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// dimen
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}

	// dp--px
	public static int dip2px(int dp) {
		//density:屏幕的密度比
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);// 加上0.5 为了四舍五入
	}

	// 判断当前线程是否运行在主线程当中
	public static boolean isRunOnUiThread() {
		// 得到当前的线程，跟主线程进行比较
		int currentThreadId = android.os.Process.myTid();
		return currentThreadId == getMainThreadId();
	}

	// 保证传递进来的r一定是在主线程中运行
	public static void runOnUiThread(Runnable r) {
		if (isRunOnUiThread()) {
			r.run();//
		} else {
			getHandler().post(r);// 将r放到handler所在的那个线程进行处理
		}
	}

	public static View inflateView(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}
}
