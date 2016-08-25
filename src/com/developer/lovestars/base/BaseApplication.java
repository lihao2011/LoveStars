package com.developer.lovestars.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {

	private static Context context;
	private static Handler handler;
	private static int mainThreadId;

	// 做一些初始化的工作
	@Override
	public void onCreate() {
		super.onCreate();
		// context经常使用到。Toast、new View
		context = getApplicationContext();
		// 得到主线程的handler对象，维护的是主线程的MessageQueue
		handler = new Handler();
		// 哪个方法调用了myTid，myTid返回的就是那个方法所在的线程id
		mainThreadId = android.os.Process.myTid();
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}
}
