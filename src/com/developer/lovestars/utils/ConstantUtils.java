package com.developer.lovestars.utils;

public class ConstantUtils {

	/*
	 * 参数名：type
	 * 类型：string
	 * 是否必填：否 
	 * 说明：类型,
	 * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),
	 * tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
	 */
	
	// AppKey：12e09f1041415ea93baf19481bd47405
	public static final String NEWS_TOP_KEY = "12e09f1041415ea93baf19481bd47405";
	// 新闻类型
	public static final String NEWS_TYPE = "keji";
	// 新闻头条服务器请求地址
	public static final String BASE_URL = "http://v.juhe.cn/toutiao/index";
	// type请求地址
	public static final String BASE_URL_TYPE = BASE_URL + "?" + "type="
			+ NEWS_TYPE + "&key=" + NEWS_TOP_KEY;
	// http://v.juhe.cn/toutiao/index?type=keji&key=12e09f1041415ea93baf19481bd47405
	
}
