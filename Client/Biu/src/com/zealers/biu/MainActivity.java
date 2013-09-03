package com.zealers.biu;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	private View myContactList;
	private View myInfo;
	private View mainPage;
	private ArrayList<View> views;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		views = new ArrayList<View>();
		mViewPager = (ViewPager)findViewById(R.id.main_viewpager);
		mViewPager.setOnPageChangeListener(null);
		initView();
		addViews();
		
		
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
				if (position == 2){
					Log.i("INFO:","I'm coming");
					
				}
			}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(1);
	}

	private void addViews() {
		// TODO Auto-generated method stub

		views.add(myInfo);
		views.add(mainPage);
		views.add(myContactList);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		myContactList = getViews(MyContactList.class,"1");
		myInfo = getViews(MyInfo.class, "2");
		Log.i("INFO:","Do you start, main?");
		mainPage = getViews(MainPage.class, "3");
		
	}

	private View getViews(Class<?> cls, String pageid) {
		// TODO Auto-generated method stub
		return getLocalActivityManager().
				startActivity(pageid, new Intent(MainActivity.this,cls).
						addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)).getDecorView();
	}
	

}