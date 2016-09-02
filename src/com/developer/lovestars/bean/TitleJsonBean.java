package com.developer.lovestars.bean;

import java.util.List;

public class TitleJsonBean{

	public int retcode;
	public List<Data_json> data_json;

	public class Data_json{
		
		public String id;
		public String title;
		public String type_url;
	}
}
