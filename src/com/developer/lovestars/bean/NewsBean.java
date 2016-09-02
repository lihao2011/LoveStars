package com.developer.lovestars.bean;

import java.util.List;

public class NewsBean {

	public String reason;
	public int error_code;
	public Result result;

	public class Result {

		public String stat;
		public List<Data> data;

		public class Data {

			public String title;
			public String category;
			public String thumbnail_pic_s03;
			public String author_name;
			public String thumbnail_pic_s;
			public String date;
			public String url;
		}
	}
}
