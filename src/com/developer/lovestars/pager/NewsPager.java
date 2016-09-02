package com.developer.lovestars.pager;

import java.util.ArrayList;
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
import com.developer.lovestars.adapter.TopPagerAdapter;
import com.developer.lovestars.base.BasePager;
import com.developer.lovestars.bean.NewsBean;
import com.developer.lovestars.bean.NewsBean.Result.Data;
import com.developer.lovestars.bean.TitleJsonBean.Data_json;
import com.developer.lovestars.ui.NewsWebViewActivity;
import com.developer.lovestars.utils.UiUtils;
import com.developer.lovestars.widget.RefreshListView;
import com.developer.lovestars.widget.RefreshListView.OnrefreshListener;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class NewsPager extends BasePager {

	private RequestQueue mQueue;
	private JsonObjectRequest request;
	private RefreshListView newsPagerList;

	private NewsBean newsBean;
	private List<Data> mData;
	private View view;
	private Data_json data_json;

	private View topView;
	private ViewPager topImage;
	private LinearLayout topPoints;
	private int preRedPointIndex = 0;// 前一个红点的索引

	protected static final int MSG_ERR = 0;
	protected static final int MSG_SUCC = 1;

	private boolean isRefresh; // 是否是下拉刷新调用的getDataFromServer方法
	private boolean isLoadMore = false;

	// 2.在主线程（Activity）中写一个Handler
	private Handler mHandler = new Handler() {
		// 3.在handlmessage方法中修改UI
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_SUCC:// 成功
				List<String> allPicList = new ArrayList<String>();
				for (int i = 0; i < mData.size(); i++) {
					String allPic = mData.get(i).thumbnail_pic_s03;
					allPicList.add(allPic);
				}
				List<String> topPicList = new ArrayList<String>();
				for (int i = 0; i < 4; i++) {
					int random = (int) (Math.random() * (allPicList.size() - 1));
					String pic = allPicList.get(random);
					topPicList.add(pic);
					allPicList.remove(pic);
				}
				TopPagerAdapter topPagerAdapter = new TopPagerAdapter(
						topPicList);
				topImage.setAdapter(topPagerAdapter);
				topPagerAdapter.notifyDataSetChanged();
				// 监听轮播图
				topImage.setOnPageChangeListener(new MyOnPageChangeListener());
				// 添加点的指示器
				// 删除之前的点
				topPoints.removeAllViews();
				for (int i = 0; i < topPicList.size(); i++) {
					ImageView imageView = new ImageView(mContext);
					imageView
							.setBackgroundResource(R.drawable.viewpager_point_selector);
					// 设置宽高
					LayoutParams params = new LayoutParams(14, 14);
					params.leftMargin = 14;
					imageView.setLayoutParams(params);
					imageView.setEnabled(false);
					topPoints.addView(imageView);
				}
				// 初始化第0个红点
				topPoints.getChildAt(0).setEnabled(true);
				// 把前一个红点位置初始化为0
				preRedPointIndex = 0;

				// 给listView设置数据适配器
				NewsAdapter newsAdapter = new NewsAdapter(mData);
				newsPagerList.setAdapter(newsAdapter);
				newsAdapter.notifyDataSetChanged();
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

	public NewsPager(Context context, Data_json data_json) {
		super(context);
		this.data_json = data_json;
	}

	@Override
	protected View initView() {

		if (view == null) {
			view = View.inflate(mContext, R.layout.list_newspager, null);
			// 添加顶部轮播图布局
			topView = View.inflate(UiUtils.getContext(),
					R.layout.viewpager_image, null);
		}
		topImage = (ViewPager) topView.findViewById(R.id.vp_topImage);
		topPoints = (LinearLayout) topView.findViewById(R.id.ll_topPoints);
		newsPagerList = (RefreshListView) view.findViewById(R.id.newsPagerList);
		// 把轮播图添加到Listview的header
		newsPagerList.addHeaderView(topView);
		// 监听Listview的刷新状态
		newsPagerList.setOnrefreshListener(new NewsOnrefreshListener());
		// 监听条目点击事件
		newsPagerList.setOnItemClickListener(new NewsOnItemClickListener());
		newsPagerList.setMotionEventSplittingEnabled(false);
		return view;
	}

	class NewsOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(mContext, NewsWebViewActivity.class);
			intent.putExtra("url", mData.get(position - 2).url);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
	}
	
	class NewsOnrefreshListener implements OnrefreshListener {

		@Override
		public void onRefreshing() {
			isRefresh = true;
			// 刷新业务
			getDataFromServer();

		}

		@Override
		public void onLoadingMore() {
			isLoadMore = true;
			// 是否有更多数据
			if (!TextUtils.isEmpty(null)) {
				// 加载更多
				getMoreDataFromServer();
			} else {
				// 没有更多数据时，恢复加载更多状态
				newsPagerList.loadMoreFinished();
				Toast.makeText(mContext, "亲，没有更多数据了", Toast.LENGTH_SHORT).show();
			}

		}
	}

	public void getMoreDataFromServer() {
		// 把加载更多标记置为false
		isLoadMore = false;
		newsPagerList.loadMoreFinished();
	}

	@Override
	public void initData() {
		super.initData();

		new Thread() {
			public void run() {
				// 访问网络
				getDataFromServer();
			}
		}.start();
	}

	private void getDataFromServer() {
		mQueue = Volley.newRequestQueue(UiUtils.getContext());

		String requestUrl = "http://v.juhe.cn/toutiao/index";
		// String type = "yule";
		String type = data_json.type_url;
		String key = "12e09f1041415ea93baf19481bd47405";
		String mUrl = requestUrl + "?type=" + type + "&key=" + key;
		request = new JsonObjectRequest(Method.GET, mUrl, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						String responseStr = response.toString();

						backResponse(responseStr);

						// 刷新业务完成，恢复Listview的状态
						if (isRefresh) {
							newsPagerList.refreshFinished(true);
							isRefresh = false;
						}
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

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// 把前一个红点变白色
			topPoints.getChildAt(preRedPointIndex).setEnabled(false);
			// 当选中某一页时，对应的点变红色
			topPoints.getChildAt(position).setEnabled(true);
			preRedPointIndex = position;
		}
	}
}
