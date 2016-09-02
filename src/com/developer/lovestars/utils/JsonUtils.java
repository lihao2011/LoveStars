package com.developer.lovestars.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class JsonUtils {

	public JSONObject readTitle() {
		try {
			InputStreamReader isr = new InputStreamReader(UiUtils.getContext()
					.getAssets().open("titledata.json"),"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			JSONObject titleJsonObject = new JSONObject(builder.toString());
			
			return titleJsonObject;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
