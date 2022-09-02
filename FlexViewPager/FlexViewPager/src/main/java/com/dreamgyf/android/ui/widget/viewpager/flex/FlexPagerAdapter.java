package com.dreamgyf.android.ui.widget.viewpager.flex;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @Author: dreamgyf
 * @Date: 2021/7/13
 */
public abstract class FlexPagerAdapter extends PagerAdapter {

	@Override
	public int getItemPosition(@NonNull Object object) {
		return POSITION_NONE;
	}

	@Nullable
	abstract public View getView(int position);
}
