package com.developer.lovestars.ui;

import com.developer.lovestars.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NewsWebViewActivity extends Activity {
	private WebView wv_news;
	private ProgressBar pb_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview_news);

		wv_news = (WebView) findViewById(R.id.wv_news);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
		
		initWebView();
	}

	
	private void initWebView() {
		//监听WebView加载完成
		wv_news.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pb_progress.setVisibility(View.GONE);
			}
		});
		//WebView配置
		WebSettings settings = wv_news.getSettings();
		settings.setBuiltInZoomControls(true);//启用缩放按钮
		settings.setUseWideViewPort(true);//启用双击缩放
		settings.setJavaScriptEnabled(true);//启用JavaScript
		
		//WebView加载数据
		String webUrl = getIntent().getStringExtra("url");
		wv_news.loadUrl(webUrl);
	}
}
