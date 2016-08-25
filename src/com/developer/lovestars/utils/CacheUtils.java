package com.developer.lovestars.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {

	private static final String FILE_NAME = "lovastars";
	private static SharedPreferences sp;

	// 存一个boolean值
	public static void putBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	// 取一个boolean值
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
}
