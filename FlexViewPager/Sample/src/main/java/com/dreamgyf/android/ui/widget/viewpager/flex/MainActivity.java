package com.dreamgyf.android.ui.widget.viewpager.flex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private FlexViewPager mVpFlex;

	private FlexPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<View> viewList = new ArrayList<>();
		viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_1, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_2, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_3, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_4, null));
		viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_5, null));

		mVpFlex = findViewById(R.id.vp_flex);
		mVpFlex.setAdapter(mAdapter = new FlexPagerAdapter());
		mAdapter.setViews(viewList);
	}
}