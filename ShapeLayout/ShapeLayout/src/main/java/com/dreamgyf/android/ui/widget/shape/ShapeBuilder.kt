package com.dreamgyf.android.ui.widget.shape

import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Size
import androidx.annotation.ColorInt

/**
 * 此工具可以方便的使用代码构建一个shape
 * 其属性命名与xml中的命名基本一致
 *
 * 示例：
 * ShapeBuilder()
 *         .corner(10dp)
 *         .stroke(
 *                 Stroke.newBuilder()
 *                         .width(1dp)
 *                         .color(Color.BLACK)
 *                         .build()
 *         )
 *         .gradient(
 *                 Gradient.newBuilder()
 *                         .colors(Color.BLUE, Color.RED)   // startColor & endColor
 *                         .angle(180)
 *                         .build()
 *         )
 *         .build()
 *
 * 注：padding属性在 Android Q 以上才生效
 *
 * @Author: dreamgyf
 * @Date: 2022/4/27
 */
class ShapeBuilder {

    private var shape = GradientDrawable.RECTANGLE

    private var corners: Corners? = null

    private var gradient: Gradient? = null

    private var padding: Rect? = null

    private var size: Size? = null

    private var solidColor: ColorStateList? = null

    private var stroke: Stroke? = null

    fun shape(shape: Int): ShapeBuilder {
        this.shape = shape
        return this
    }

    fun corner(topLeftRadius: Float,
               topRightRadius: Float,
               bottomRightRadius: Float,
               bottomLeftRadius: Float): ShapeBuilder {
        return corners(
                Corners.newBuilder()
                        .radius(
                                topLeftRadius,
                                topRightRadius,
                                bottomRightRadius,
                                bottomLeftRadius
                        )
                        .build()
        )
    }

    fun corners(radius: Float): ShapeBuilder {
        return corners(
                Corners.newBuilder()
                        .radius(radius)
                        .build()
        )
    }

    fun corners(corners: Corners): ShapeBuilder {
        this.corners = corners
        return this
    }

    fun gradient(gradient: Gradient): ShapeBuilder {
        this.gradient = gradient
        return this
    }

    fun padding(paddingLeft: Int,
                paddingTop: Int,
                paddingRight: Int,
                paddingBottom: Int): ShapeBuilder {
        return padding(
                Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
        )
    }

    fun padding(padding: Rect): ShapeBuilder {
        this.padding = padding
        return this
    }

    fun size(width: Int, height: Int): ShapeBuilder {
        return size(
                Size(width, height)
        )
    }

    fun size(size: Size): ShapeBuilder {
        this.size = size
        return this
    }

    fun solid(@ColorInt color: Int): ShapeBuilder {
        this.solidColor = ColorStateList.valueOf(color)
        return this
    }

    fun solid(color: ColorStateList): ShapeBuilder {
        this.solidColor = color
        return this
    }

    fun stroke(stroke: Stroke): ShapeBuilder {
        this.stroke = stroke
        return this
    }

    fun build(): Drawable {
        return GradientDrawable().apply {
            shape = this@ShapeBuilder.shape

            if (corners != null) {
                cornerRadii = floatArrayOf(
                        corners!!.topLeftRadius,
                        corners!!.topLeftRadius,
                        corners!!.topRightRadius,
                        corners!!.topRightRadius,
                        corners!!.bottomRightRadius,
                        corners!!.bottomRightRadius,
                        corners!!.bottomLeftRadius,
                        corners!!.bottomLeftRadius
                )
            }

            if (gradient != null) {
                orientation = gradient!!.orientation
                colors = gradient!!.colors
                setGradientCenter(gradient!!.centerX, gradient!!.centerY)
                gradientRadius = gradient!!.gradientRadius
                gradientType = gradient!!.type
            }

            if (padding != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setPadding(padding!!.left, padding!!.top, padding!!.right, padding!!.bottom)
                }
            }

            if (size != null) {
                setSize(size!!.width, size!!.height)
            }

            if (solidColor != null) {
                color = solidColor
            }

            if (stroke != null) {
                setStroke(stroke!!.width, stroke!!.color, stroke!!.dashWidth, stroke!!.dashGap)
            }
        }
    }
}