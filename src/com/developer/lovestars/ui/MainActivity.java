package com.developer.lovestars.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.developer.lovestars.R;
import com.developer.lovestars.base.BaseActivity;
import com.developer.lovestars.base.BaseFragment;
import com.developer.lovestars.fragment.ChatFragment;
import com.developer.lovestars.fragment.HomeFragment;
import com.developer.lovestars.fragment.MapFragment;
import com.developer.lovestars.fragment.NewsFragment;
import com.developer.lovestars.fragment.NotesFragment;
import com.developer.lovestars.widget.ActionBarDrawerToggle;
import com.developer.lovestars.widget.DrawerArrowDrawable;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {
	// ViewPager控件
	private ViewPager main_viewPager;
	// RadioGroup控件
	private RadioGroup rg_tab;
	// RadioButton控件
	private RadioButton rb_news;
	private RadioButton rb_chat;
	private RadioButton rb_notes;
	private RadioButton rb_map;
	private RadioButton rb_home;
	// 类型为Fragment的动态数组
	private ArrayList<BaseFragment> fragmentList;
	private DrawerLayout drawer_layout;
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// 界面初始函数，用来获取定义的各控件对应的ID
		InitView();
		// ViewPager初始化函数
		InitViewPager();

		setOverflowShowingAlways();
	}

	public void InitView() {
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
		rb_news = (RadioButton) findViewById(R.id.rb_news);
		rb_chat = (RadioButton) findViewById(R.id.rb_chat);
		rb_notes = (RadioButton) findViewById(R.id.rb_notes);
		rb_map = (RadioButton) findViewById(R.id.rb_map);
		rb_home = (RadioButton) findViewById(R.id.rb_home);

		rg_tab.setOnCheckedChangeListener(this);

		drawer_layout.setScrimColor(0x40000000);// 设置右侧部分覆盖颜色
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setHomeButtonEnabled(true);

		DrawerArrowDrawable drawerImageRes = new DrawerArrowDrawable(this) {

			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout,
				drawerImageRes, 0, 0);

		drawer_layout.setDrawerListener(actionBarDrawerToggle);
	}

	public void InitViewPager() {
		main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);

		fragmentList = new ArrayList<BaseFragment>();

		NewsFragment newsFragment = new NewsFragment();
		ChatFragment chatFragment = new ChatFragment();
		NotesFragment notesFragment = new NotesFragment();
		MapFragment mapFragment = new MapFragment();
		HomeFragment homeFragment = new HomeFragment();

		// 将各Fragment加入数组中
		fragmentList.add(newsFragment);
		fragmentList.add(chatFragment);
		fragmentList.add(notesFragment);
		fragmentList.add(mapFragment);
		fragmentList.add(homeFragment);

		// 设置ViewPager的设配器
		main_viewPager.setAdapter(new MainFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		// 当前为第一个页面
		main_viewPager.setCurrentItem(0);
		// ViewPager的页面改变监听器
		main_viewPager.setOnPageChangeListener(new MainOnPageChangeListener());
	}

	public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<BaseFragment> list;

		public MainFragmentPagerAdapter(FragmentManager fm,
				ArrayList<BaseFragment> fragmentList) {
			super(fm);
			this.list = fragmentList;
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	public class MainOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			// 获取当前页面用于改变对应RadioButton的状态
			int current = main_viewPager.getCurrentItem();
			switch (current) {
			case 0:
				rb_news.setChecked(true);
				break;
			case 1:
				rb_chat.setChecked(true);
				break;
			case 2:
				rb_notes.setChecked(true);
				break;
			case 3:
				rb_map.setChecked(true);
				break;
			case 4:
				rb_home.setChecked(true);
				break;
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
		// 获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
		int current = 0;
		switch (CheckedId) {
		case R.id.rb_news:
			current = 0;
			break;
		case R.id.rb_chat:
			current = 1;
			break;
		case R.id.rb_notes:
			current = 2;
			break;
		case R.id.rb_map:
			current = 3;
			break;
		case R.id.rb_home:
			current = 4;
			break;
		}
		if (main_viewPager.getCurrentItem() != current) {
			main_viewPager.setCurrentItem(current);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	// 设置虚拟键手指显示Overflow按钮
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Overflow菜单显示图标
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View arg0) {

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.actionbar_search:
			Toast.makeText(this, "开始搜索了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.actionbar_add_connect:
			Toast.makeText(this, "开始 blog 了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.actionbar_add_prise:
			Toast.makeText(this, "开始 message 了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.actionbar_add_publish:
			Toast.makeText(this, "开始 note 了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.actionbar_add_spread:
			Toast.makeText(this, "开始 team 了", Toast.LENGTH_SHORT).show();
			break;

		case android.R.id.home:
			Toast.makeText(this, "开始 home 了", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}