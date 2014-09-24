package com.arianaantonio.astropix;

import java.util.ArrayList;
import java.util.HashMap;

import Fragments.DetailFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class DetailActivity extends FragmentActivity {
	private static final int NUM_PAGES = 20;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	ArrayList<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();
	Bundle bundle2 = new Bundle();
	int clickedPosition;
	private int currentTabPosition;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.viewpager);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		
		Bundle bundle = intent.getBundleExtra("Grid bundle");
		
		data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("Grid Images");
		clickedPosition = intent.getIntExtra("Position", 0);
		
		//Bundle bundle2 = new Bundle();
		//bundle2.putSerializable("clicked data", data1.get(position));
		Log.i("Detail Activity", "Clicked image: " +data1.get(clickedPosition));
		
		//DetailFragment fragment = new DetailFragment();
		//fragment.setArguments(bundle2);
		
		Log.i("Detail Activity", "Position: " +clickedPosition);
		viewPager= (ViewPager) findViewById(R.id.pager);
		Log.i("Detail Activity", "Working1");
		pagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager());
		
		Log.i("Detail Activity", "Working2");
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(clickedPosition -1);   
		viewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override 
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				Log.i("Detail Activity", "Swipe Right - position: " +position);
				if (position > clickedPosition) {
					Log.i("Detail Activity", "Swipe Right");
				} else {
					Log.i("Detail Activity", "Swipe Left");
				}
			}  
			
		});
		Log.i("Detail Activity", "Working3"); 
	}
	
	@Override
	public void onBackPressed() {
		
		if (viewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			viewPager.setCurrentItem(viewPager.getCurrentItem() -1);
		}
	}

	private class ScreenPagerAdapter extends FragmentStatePagerAdapter {
		
		public ScreenPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			//viewPager.setCurrentItem(clickedPosition); 
			DetailFragment fragment = new DetailFragment();
			Log.i("Detail Activity", "getItem position: " +position);
			
			bundle2.putSerializable("clicked data", data1.get(position));
			fragment.setArguments(bundle2);
			return fragment;  
		}

		@Override
		public int getCount() {
			
			return NUM_PAGES; 
		}
	}

}