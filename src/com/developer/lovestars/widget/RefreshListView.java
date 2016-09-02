package com.developer.lovestars.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.developer.lovestars.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RefreshListView extends ListView {

	private int downY = -1;
	private int headerHeight;
	private View header;

	private static final int PULLDOWN_STATE = 0;// 下拉刷新状态
	private static final int RELEASE_STATE = 1;// 松开刷新状态
	private static final int REFRESHING_STATE = 2;// 正在刷新状态
	private int current_state = PULLDOWN_STATE;

	private ImageView iv_header_arrow;
	private ProgressBar pb_header_progress;
	private TextView tv_header_state;
	private TextView tv_refresh_time;

	private RotateAnimation up;
	private RotateAnimation down;
	private OnrefreshListener mListener;
	private View footer;
	private int footerHeight;

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeader();
		initAnim();
		initFooter();
	}

	private void initFooter() {
		footer = View.inflate(getContext(), R.layout.refresh_footer, null);
		footer.measure(0, 0);
		footerHeight = footer.getMeasuredHeight();
		footer.setPadding(0, -footerHeight, 0, 0);
		this.addFooterView(footer);
		// 监听Listview的滚动事件
		this.setOnScrollListener(new MyOnScrollListener());
	}

	private void initAnim() {
		up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		up.setDuration(500);
		up.setFillAfter(true);
		down = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		down.setDuration(500);
		down.setFillAfter(true);
	}

	private void initHeader() {
		header = View.inflate(getContext(), R.layout.refresh_header, null);
		iv_header_arrow = (ImageView) header.findViewById(R.id.iv_header_arrow);
		pb_header_progress = (ProgressBar) header
				.findViewById(R.id.pb_header_progress);
		tv_header_state = (TextView) header.findViewById(R.id.tv_header_state);
		tv_refresh_time = (TextView) header.findViewById(R.id.tv_refresh_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		tv_refresh_time.setText(time);
		// 获取测量高度
		header.measure(0, 0);
		headerHeight = header.getMeasuredHeight();
		header.setPadding(0, -headerHeight, 0, 0);
		this.addHeaderView(header);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// System.out.println(ev.getAction());
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (downY == -1) {
				downY = (int) ev.getY();
			}
			if (getFirstVisiblePosition() != 0) {
				break;
			}

			int moveY = (int) ev.getY();
			int diffY = moveY - downY;
			// 只处理从上往下滑动
			if (diffY > 0) {
				int toppading = diffY - headerHeight;
				if (toppading < 0 && current_state != PULLDOWN_STATE) {
					current_state = PULLDOWN_STATE;
					System.out.println("切换到下拉刷新");
					switchState(current_state);
				} else if (toppading > 0 && current_state != RELEASE_STATE) {
					current_state = RELEASE_STATE;
					System.out.println("切换到松开刷新");
					switchState(current_state);
				}
				header.setPadding(0, toppading, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			downY = -1;
			if (current_state == PULLDOWN_STATE) {
				header.setPadding(0, -headerHeight, 0, 0);
			} else if (current_state == RELEASE_STATE) {
				current_state = REFRESHING_STATE;
				header.setPadding(0, 0, 0, 0);
				System.out.println("切换到正在刷新");
				switchState(current_state);
				// 调用外界的刷新业务
				if (mListener != null) {
					mListener.onRefreshing();
				}
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void switchState(int state) {
		switch (state) {
		case PULLDOWN_STATE:
			tv_header_state.setText("下拉刷新");
			iv_header_arrow.setVisibility(View.VISIBLE);
			pb_header_progress.setVisibility(View.INVISIBLE);
			iv_header_arrow.startAnimation(down);
			break;
		case RELEASE_STATE:
			tv_header_state.setText("松开刷新");
			iv_header_arrow.startAnimation(up);
			break;
		case REFRESHING_STATE:
			iv_header_arrow.clearAnimation();
			tv_header_state.setText("正在刷新");
			iv_header_arrow.setVisibility(View.INVISIBLE);
			pb_header_progress.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	public interface OnrefreshListener {
		void onRefreshing();

		void onLoadingMore();
	}

	public void setOnrefreshListener(OnrefreshListener listener) {
		this.mListener = listener;
	}

	public void refreshFinished(boolean success) {
		tv_header_state.setText("下拉刷新");
		iv_header_arrow.setVisibility(View.VISIBLE);
		pb_header_progress.setVisibility(View.INVISIBLE);
		header.setPadding(0, -headerHeight, 0, 0);
		current_state = PULLDOWN_STATE;
		if (success) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			tv_refresh_time.setText(time);
		} else {
			Toast.makeText(getContext(), "亲，网络出问题了", 0).show();
		}

	}

	public void loadMoreFinished() {
		footer.setPadding(0, -footerHeight, 0, 0);
		isLoadMore = false;
	}

	private boolean isLoadMore = false;

	class MyOnScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// 当处于停止或惯性停止状态
			if (OnScrollListener.SCROLL_STATE_IDLE == scrollState
					|| OnScrollListener.SCROLL_STATE_FLING == scrollState) {
				// 当前显示的最后一个数据是否是Adapter中最后一条
				if (getLastVisiblePosition() == getAdapter().getCount() - 1
						&& !isLoadMore) {
					isLoadMore = true;
					System.out.println("加载更多了");
					footer.setPadding(0, 0, 0, 0);
					// 让脚布局自动显示
					setSelection(getAdapter().getCount());
					// 调用加载更多的业务
					if (mListener != null) {
						mListener.onLoadingMore();
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}
	}
}
