package com.dreamgyf.android.ui.rv

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView内部Items等分间距装饰类
 * 支持：
 * LinearLayoutManager
 * GridLayoutManager
 *
 * 在StaggeredGridLayoutManager下
 * 当列表发生变化时，在ItemDecoration中无法准确获得Item的SpanIndex
 * 所以无法完美支持StaggeredGridLayoutManager，如果列表不会动态修改则可以正常使用
 *
 * @Author: dreamgyf
 * @Date: 2022/5/10
 */
class SpaceItemDecoration : RecyclerView.ItemDecoration {

    private val horizontalSpace: Int

    private val verticalSpace: Int

    constructor(space: Int) {
        this.horizontalSpace = space
        this.verticalSpace = space
    }

    constructor(horizontalSpace: Int, verticalSpace: Int) {
        this.horizontalSpace = horizontalSpace
        this.verticalSpace = verticalSpace
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val layoutManager = parent.layoutManager ?: return

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                handleStaggeredGridLayout(layoutManager, outRect, view, parent)
            }
            is GridLayoutManager -> {
                handleGridLayout(layoutManager, outRect, view, parent)
            }
            is LinearLayoutManager -> {
                handleLinearLayout(layoutManager, outRect, view, parent)
            }
        }
    }

    private fun handleStaggeredGridLayout(
            layoutManager: StaggeredGridLayoutManager,
            outRect: Rect,
            view: View,
            parent: RecyclerView
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = layoutManager.spanCount
        val lp = (view.layoutParams as? StaggeredGridLayoutManager.LayoutParams?) ?: return
        val spanIndex = lp.spanIndex
        val spanSize = if (lp.isFullSpan) spanCount else 1
        val isFirstRow = isFirstRow(layoutManager, position)

        handleGridSpace(outRect, layoutManager, position, spanIndex, spanSize, spanCount, isFirstRow)
    }

    private fun handleGridLayout(
            layoutManager: GridLayoutManager,
            outRect: Rect,
            view: View,
            parent: RecyclerView
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = layoutManager.spanCount
        val spanIndex = layoutManager.spanSizeLookup.getSpanIndex(position, spanCount)
        val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)
        val isFirstRow = isFirstRow(layoutManager, position)

        handleGridSpace(outRect, layoutManager, position, spanIndex, spanSize, spanCount, isFirstRow)
    }

    private fun handleGridSpace(outRect: Rect, layoutManager: RecyclerView.LayoutManager,
                                position: Int, spanIndex: Int, spanSize: Int, spanCount: Int, isFirstRow: Boolean) {
        if (!isFirstRow) {
            if (layoutManager.canScrollVertically()) {
                outRect.top = verticalSpace
            } else if (layoutManager.canScrollHorizontally()) {
                outRect.left = horizontalSpace
            }
        }

        /**
         * 这里需要两个式子成立，才能保证间隔相同的情况下，每个Item的宽度也一样：
         * 1、每个Item的outRect.left + outRect.right得出的结果均相等
         * 2、任何一个Item的 outRect.right 加上下一个Item的 outRect.left 的值都相同（等于space）
         *
         * 这里，将间距平均划分成列数相应的等分，同时计算出每个Item的左右可用空间（outRect.left + outRect.right）
         * 根据如下代码的规律将左右空间均匀分配给每个Item，使其满足上述两个式子
         */
        if (layoutManager.canScrollVertically()) {
            val unitSpace = horizontalSpace / spanCount
            outRect.left = spanIndex * unitSpace
            outRect.right = horizontalSpace - (spanIndex + spanSize) * unitSpace
        } else if (layoutManager.canScrollHorizontally()) {
            val unitSpace = verticalSpace / spanCount
            outRect.top = spanIndex * unitSpace
            outRect.bottom = verticalSpace - (spanIndex + spanSize) * unitSpace
        }
    }

    private fun handleLinearLayout(
            layoutManager: LinearLayoutManager,
            outRect: Rect,
            view: View,
            parent: RecyclerView
    ) {
        if (parent.getChildAdapterPosition(view) > 0) {
            if (layoutManager.canScrollVertically()) {
                outRect.top = verticalSpace
            } else if (layoutManager.canScrollHorizontally()) {
                outRect.left = horizontalSpace
            }
        }
    }

    private fun isFirstRow(layoutManager: StaggeredGridLayoutManager, position: Int): Boolean {
        val spanCount = layoutManager.spanCount

        if (position == 0) {
            return true
        }
        if (position >= spanCount) {
            return false
        }

        for (i in 1..position) {
            val view = layoutManager.getChildAt(i)
            val lp = view?.layoutParams as? StaggeredGridLayoutManager.LayoutParams?
            if (lp?.isFullSpan == true) {
                return false
            }
        }

        return true
    }

    private fun isFirstRow(layoutManager: GridLayoutManager, position: Int): Boolean {
        val spanCount = layoutManager.spanCount

        if (position == 0) {
            return true
        }
        if (position >= spanCount) {
            return false
        }

        val spanIndex = layoutManager.spanSizeLookup.getSpanIndex(position, spanCount)
        return spanIndex == position
    }
}