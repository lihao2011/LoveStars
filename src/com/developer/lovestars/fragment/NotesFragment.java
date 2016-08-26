package com.developer.lovestars.fragment;

import com.developer.lovestars.R;
import com.developer.lovestars.utils.UiUtils;
import com.developer.lovestars.widget.PagerTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotesFragment extends Fragment {

	private PagerTab notesPagerTab;
	private ViewPager notesViewPager;
	private String[] notesTabTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(UiUtils.getContext(), R.layout.fragment_notes,
				null);
		
//		notesTabTitle = UiUtils.getStringArray(R.array.notes_TabTitle);
//		
//		notesPagerTab = (PagerTab) view.findViewById(R.id.notes_PagerTab);
//		notesViewPager = (ViewPager) view.findViewById(R.id.notes_ViewPager);
//		NotesAdapter notesAdapter = new NotesAdapter();
//		notesViewPager.setAdapter(notesAdapter);
		return view;
	}
	
//	class NotesAdapter extends PagerAdapter{
//
//		@Override
//		public CharSequence getPageTitle(int position) {
//			return notesTabTitle[position];
//		}
//
//		@Override
//		public int getCount() {
//			return notesTabTitle.length;
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view == object;
//		}
//		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			TextView textView = new TextView(UiUtils.getContext());
//			textView.setText(notesTabTitle[position]);
//			container.addView(textView);
//			return textView;
//		}
//
//	}
}
