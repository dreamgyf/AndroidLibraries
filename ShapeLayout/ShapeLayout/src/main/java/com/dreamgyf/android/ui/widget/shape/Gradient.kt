package com.dreamgyf.android.ui.widget.shape

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

/**
 * @Author: dreamgyf
 * @Date: 2022/4/28
 */
class Gradient private constructor() {

    internal var orientation = GradientDrawable.Orientation.TOP_BOTTOM

    @ColorInt
    internal var colors: IntArray? = null

    @FloatRange(from = 0.0, to = 1.0)
    internal var centerX = 0.5f

    @FloatRange(from = 0.0, to = 1.0)
    internal var centerY = 0.5f

    internal var gradientRadius = 0.5f

    internal var type = GradientDrawable.RECTANGLE

    companion object {
        fun newBuilder() = Builder()
    }

    class Builder internal constructor() {

        private val gradient = Gradient()

        fun angle(angle: Int): Builder {
            //矫正负角度
            val fixedAngle = ((angle % 360) + 360) % 360
            gradient.orientation = when (fixedAngle) {
                0 -> GradientDrawable.Orientation.LEFT_RIGHT
                45 -> GradientDrawable.Orientation.BL_TR
                90 -> GradientDrawable.Orientation.BOTTOM_TOP
                135 -> GradientDrawable.Orientation.BR_TL
                180 -> GradientDrawable.Orientation.RIGHT_LEFT
                225 -> GradientDrawable.Orientation.TR_BL
                270 -> GradientDrawable.Orientation.TOP_BOTTOM
                315 -> GradientDrawable.Orientation.TL_BR
                else -> GradientDrawable.Orientation.TOP_BOTTOM
            }
            return this
        }

        fun colors(@ColorInt startColor: Int, @ColorInt endColor: Int): Builder {
            gradient.colors = intArrayOf(startColor, endColor)
            return this
        }

        fun colors(@ColorInt startColor: Int,
                   @ColorInt centerColor: Int,
                   @ColorInt endColor: Int): Builder {
            gradient.colors = intArrayOf(startColor, centerColor, endColor)
            return this
        }

        fun centerX(@FloatRange(from = 0.0, to = 1.0) x: Float): Builder {
            gradient.centerX = x
            return this
        }

        fun centerY(@FloatRange(from = 0.0, to = 1.0) y: Float): Builder {
            gradient.centerY = y
            return this
        }

        fun gradientRadius(radius: Float): Builder {
            gradient.gradientRadius = radius
            return this
        }

        fun type(type: Int): Builder {
            gradient.type = type
            return this
        }

        fun build() = gradient
    }
}