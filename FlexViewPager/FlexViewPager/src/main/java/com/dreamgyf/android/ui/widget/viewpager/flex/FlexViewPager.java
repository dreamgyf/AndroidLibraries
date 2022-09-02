package com.dreamgyf.android.ui.widget.viewpager.flex;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author: dreamgyf
 * @Date: 2021/7/13
 */
public class FlexViewPager extends ViewPager {

	private int mLastWidthMeasureSpec;

	private int mCurrentHeight;

	private int mLastPosition;

	private boolean mIsScrolling;

	public FlexViewPager(@NonNull Context context) {
		super(context);
		init();
	}

	public FlexViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	final public void setAdapter(@Nullable FlexPagerAdapter adapter) {
		super.setAdapter(adapter);
	}

	@Override
	final public void setAdapter(@Nullable PagerAdapter adapter) {
		if (adapter != null && !(adapter instanceof FlexPagerAdapter)) {
			throw new IllegalArgumentException("FlexViewPager need to use FlexPagerAdapter");
		}
		super.setAdapter(adapter);
	}

	@Nullable
	@Override
	final public FlexPagerAdapter getAdapter() {
		PagerAdapter adapter = super.getAdapter();
		return adapter instanceof FlexPagerAdapter ? (FlexPagerAdapter) adapter : null;
	}

	private void init() {
		addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (positionOffset == 0) {
					mIsScrolling = false;
					requestLayout();
					return;
				}

				int srcPosition, destPosition;

				if (position >= mLastPosition) {
					srcPosition = position;
					destPosition = position + 1;
				} else {
					srcPosition = position + 1;
					destPosition = position;
				}

				View lastView = getView(srcPosition);
				View destView = getView(destPosition);
				if (lastView == null || destView == null) {
					return;
				}

				int lastViewHeight = getViewHeight(lastView, mLastWidthMeasureSpec);
				int destViewHeight = getViewHeight(destView, mLastWidthMeasureSpec);

				float heightDiff = destViewHeight - lastViewHeight;

				mCurrentHeight = (int) (lastViewHeight + heightDiff * (position >= mLastPosition ? positionOffset : (1 - positionOffset)));
				mIsScrolling = true;
				requestLayout();
			}

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == SCROLL_STATE_IDLE) {
					mLastPosition = getCurrentItem();
				}
			}
		});
	}

	private View getView(int position) {
		View view = null;
		FlexPagerAdapter adapter = getAdapter();
		if (adapter != null && position >= 0 && position < adapter.getCount()) {
			view = adapter.getView(position);
		}
		return view;
	}

	private int getViewHeight(View view, int widthMeasureSpec) {
		int childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(widthMeasureSpec, childHeightSpec);
		return view.getMeasuredHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mLastWidthMeasureSpec = widthMeasureSpec;
		if (mIsScrolling) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(mCurrentHeight, MeasureSpec.EXACTLY);
		} else {
			View currentView = getView(getCurrentItem());
			if (currentView != null) {
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(getViewHeight(currentView, widthMeasureSpec), MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
