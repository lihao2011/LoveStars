package com.developer.lovestars.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CreateJsonObject {

	public JSONObject createJson() {

		try {
			JSONObject object = new JSONObject();
			JSONArray array = new JSONArray();

			JSONObject item_0 = new JSONObject();
			item_0.put("id", "00");
			item_0.put("title", "头条");
			item_0.put("type_url", "toutiao");
			array.put(item_0);

			JSONObject item_1 = new JSONObject();
			item_1.put("id", "01");
			item_1.put("title", "社会");
			item_1.put("type_url", "shehui");
			array.put(item_1);

			JSONObject item_2 = new JSONObject();
			item_2.put("id", "02");
			item_2.put("title", "国内");
			item_2.put("type_url", "guonei");
			array.put(item_2);

			JSONObject item_3 = new JSONObject();
			item_3.put("id", "03");
			item_3.put("title", "国际");
			item_3.put("type_url", "guoji");
			array.put(item_3);

			JSONObject item_4 = new JSONObject();
			item_4.put("id", "04");
			item_4.put("title", "娱乐");
			item_4.put("type_url", "yule");
			array.put(item_4);

			JSONObject item_5 = new JSONObject();
			item_5.put("id", "05");
			item_5.put("title", "体育");
			item_5.put("type_url", "tiyu");
			array.put(item_5);

			JSONObject item_6 = new JSONObject();
			item_6.put("id", "06");
			item_6.put("title", "军事");
			item_6.put("type_url", "junshi");
			array.put(item_6);

			JSONObject item_7 = new JSONObject();
			item_7.put("id", "07");
			item_7.put("title", "科技");
			item_7.put("type_url", "keji");
			array.put(item_7);

			JSONObject item_8 = new JSONObject();
			item_8.put("id", "08");
			item_8.put("title", "财经");
			item_8.put("type_url", "caijing");
			array.put(item_8);

			JSONObject item_9 = new JSONObject();
			item_9.put("id", "09");
			item_9.put("title", "时尚");
			item_9.put("type_url", "shishang");
			array.put(item_9);

			object.put("data_json", array);
			object.put("retcode", 200);

			Log.d("baishi", "" + object.toString());

			return object;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
