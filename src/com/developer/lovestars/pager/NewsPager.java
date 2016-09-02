package com.developer.lovestars.pager;

import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developer.lovestars.R;
import com.developer.lovestars.adapter.NewsAdapter;
import com.developer.lovestars.base.BasePager;
import com.developer.lovestars.bean.NewsBean;
import com.developer.lovestars.bean.NewsBean.Result.Data;
import com.developer.lovestars.utils.UiUtils;
import com.google.gson.Gson;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class NewsPager extends BasePager {

	private RequestQueue mQueue;
	private JsonObjectRequest request;
	private ListView newsPagerList;

	private NewsBean newsBean;
	private List<Data> mData;
	private View view;
	private String newsTabTitle;

	protected static final int MSG_ERR = 0;
	protected static final int MSG_SUCC = 1;

	// 2.在主线程（Activity）中写一个Handler
	private Handler mHandler = new Handler() {
		// 3.在handlmessage方法中修改UI
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_SUCC:// 成功
				// 给listView设置数据适配器
				NewsAdapter newsAdapter = new NewsAdapter(mData);
				newsPagerList.setAdapter(newsAdapter);
				// newsAdapter.notifyDataSetChanged();
				break;
			case MSG_ERR:// 失败
				Toast.makeText(UiUtils.getContext(), "哥，失败low！！",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}

		};
	};

	public NewsPager(Context context, String newsTabTitle) {
		super(context);
		this.newsTabTitle = newsTabTitle;
	}

	@Override
	protected View initView() {

		if (view == null) {
			view = View.inflate(mContext, R.layout.list_newspager, null);
		}
		newsPagerList = (ListView) view.findViewById(R.id.newsPagerList);

		return view;
	}

	@Override
	public void initData() {
		super.initData();

		new Thread() {
			public void run() {
				Log.d("lihao", "newsTabTitle = " + newsTabTitle);
				// 访问网络
				getDataFromServer();
			}
		}.start();
	}

	private void getDataFromServer() {
		mQueue = Volley.newRequestQueue(UiUtils.getContext());

		String requestUrl = "http://v.juhe.cn/toutiao/index";
		// String type = "yule";
		String type = "guonei";
		String key = "12e09f1041415ea93baf19481bd47405";
		String mUrl = requestUrl + "?type=" + type + "&key=" + key;
		request = new JsonObjectRequest(Method.GET, mUrl, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						String responseStr = response.toString();

						backResponse(responseStr);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(UiUtils.getContext(), "访问网络失败...",
								Toast.LENGTH_SHORT).show();
					}
				});
		mQueue.add(request);
	}

	protected void backResponse(String responseStr) {
		Gson gson = new Gson();
		newsBean = gson.fromJson(responseStr, NewsBean.class);
		// int error_code = newsBean.error_code;// 0
		// String reason = newsBean.reason;// 成功的返回
		mData = newsBean.result.data;
		// 1.写一个子线程发送消息
		if (mData == null) {
			Message msg = Message.obtain();
			msg.what = MSG_ERR;
			mHandler.sendMessage(msg);
		} else {
			Message msg = Message.obtain();
			msg.what = MSG_SUCC;
			mHandler.sendMessage(msg);
		}
	}
}
