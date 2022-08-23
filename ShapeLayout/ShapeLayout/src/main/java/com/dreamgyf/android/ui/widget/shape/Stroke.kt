package com.dreamgyf.android.ui.widget.shape

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * @Author: dreamgyf
 * @Date: 2022/4/28
 */
class Stroke private constructor() {

    internal var width = 0

    internal var color = ColorStateList.valueOf(Color.TRANSPARENT)

    internal var dashWidth = 0f

    internal var dashGap = 0f

    companion object {
        fun newBuilder() = Builder()
    }

    class Builder internal constructor() {

        private val stroke = Stroke()

        fun width(width: Int): Builder {
            stroke.width = width
            return this
        }

        fun color(@ColorInt color: Int): Builder {
            stroke.color = ColorStateList.valueOf(color)
            return this
        }

        fun color(color: ColorStateList): Builder {
            stroke.color = color
            return this
        }

        fun dashWidth(dashWidth: Float): Builder {
            stroke.dashWidth = dashWidth
            return this
        }

        fun dashGap(dashGap: Float): Builder {
            stroke.dashGap = dashGap
            return this
        }

        fun build() = stroke
    }
}