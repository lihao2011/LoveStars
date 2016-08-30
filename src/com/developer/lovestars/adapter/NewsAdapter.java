package com.developer.lovestars.adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.developer.lovestars.R;
import com.developer.lovestars.bean.NewsBean.Result.Data;
import com.developer.lovestars.utils.BitmapCache;
import com.developer.lovestars.utils.UiUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private List<Data> mData;
	private ImageLoader mImageLoader;

	public NewsAdapter(List<Data> mData) {
		this.mData = mData;
		RequestQueue mQueue = Volley.newRequestQueue(UiUtils.getContext());
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsHolder newsHolder = null;
		if (convertView == null) {
			convertView = View.inflate(UiUtils.getContext(),
					R.layout.item_news, null);
			newsHolder = new NewsHolder();
			newsHolder.thumbnail_pic_s = (NetworkImageView) convertView
					.findViewById(R.id.niv_yule_thumbnail_pic_s);
			newsHolder.title = (TextView) convertView
					.findViewById(R.id.tv_yule_title);
			newsHolder.author_name = (TextView) convertView
					.findViewById(R.id.tv_yule_author_name);
			newsHolder.date = (TextView) convertView
					.findViewById(R.id.tv_yule_date);
			convertView.setTag(newsHolder);
		} else {
			newsHolder = (NewsHolder) convertView.getTag();
		}
		Data data = mData.get(position);
		String pic_s_url = data.thumbnail_pic_s;
		String title = data.title;
		String author_name = data.author_name;
		String date = data.date;

		newsHolder.thumbnail_pic_s.setImageUrl(pic_s_url, mImageLoader);
		newsHolder.thumbnail_pic_s
				.setErrorImageResId(R.drawable.item_error_pic);
		newsHolder.thumbnail_pic_s
				.setDefaultImageResId(R.drawable.item_default_pic);
		newsHolder.title.setText(title);
		newsHolder.author_name.setText(author_name);
		newsHolder.date.setText(date);

		return convertView;
	}

	public static class NewsHolder {
		NetworkImageView thumbnail_pic_s;
		TextView title;
		TextView author_name;
		TextView date;
	}
}
