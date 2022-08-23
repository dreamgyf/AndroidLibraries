package com.dreamgyf.android.ui.widget.shape

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout

/**
 * 这个布局可以使用属性替代shape背景
 * 避免重复创建大量相似但又细微不同的shape xml
 * 支持除padding和size外的全部shape属性
 * 属性名和shape属性名类似，加上了前缀
 *
 * cornerRadius --- 对应corner标签下的radius属性，圆角
 * cornerTopLeftRadius --- 对应corner标签下的topLeftRadius属性，左上圆角
 * cornerTopRightRadius --- 对应corner标签下的topRightRadius属性，右上圆角
 * cornerBottomRightRadius --- 对应corner标签下的bottomRightRadius属性，右下圆角
 * cornerBottomLeftRadius --- 对应corner标签下的bottomLeftRadius属性，左下圆角
 *
 * gradientStartColor --- 对应gradient标签下的startColor属性，渐变起始颜色
 * gradientCenterColor --- 对应gradient标签下的centerColor属性，渐变中间颜色（可选）
 * gradientEndColor --- 对应gradient标签下的endColor属性，渐变结束颜色
 * gradientAngle --- 对应gradient标签下的angle属性，渐变角度，默认为270度，即从上向下
 * gradientType --- 对应gradient标签下的type属性，渐变类型，分别为线性、圆形和扫射，默认为线性
 * gradientCenterX --- 对应gradient标签下的centerX属性，相对X的渐变位置
 * gradientCenterY --- 对应gradient标签下的centerY属性，相对Y的渐变位置
 * gradientRadius --- 对应gradient标签下的gradientRadius属性，渐变颜色的半径
 * 注：gradientType为 radial（圆形）的情况下，必须要设置gradientRadius，否则会报错
 *
 * solid --- 对应solid标签下的color属性，填充颜色
 *
 * strokeWidth --- 对应stroke标签下的width属性，边框粗细
 * strokeColor --- 对应stroke标签下的color属性，边框颜色
 * strokeDashWidth --- 对应stroke标签下的dashWidth属性，虚线边框中，每条线段的长度
 * strokeDashGap --- 对应stroke标签下的dashGap属性，虚线边框中，每条线段之间间隔的长度
 *
 * @Author: dreamgyf
 * @Date: 2022/4/28
 */
class ShapeLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val shapeBuilder = ShapeBuilder()

    private val gradientBuilder = Gradient.newBuilder()

    private val strokeBuilder = Stroke.newBuilder()

    init {
        if (attrs != null) {
            setShapeFromAttrs(attrs)
        }
        background = shapeBuilder.build()
    }

    private fun setShapeFromAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeLayout)

        /* Corner */
        val cornerRadius = typedArray.getDimension(R.styleable.ShapeLayout_cornerRadius, -1f)
        val cornerTopLeftRadius =
                typedArray.getDimension(R.styleable.ShapeLayout_cornerTopLeftRadius, -1f)
        val cornerTopRightRadius =
                typedArray.getDimension(R.styleable.ShapeLayout_cornerTopRightRadius, -1f)
        val cornerBottomRightRadius =
                typedArray.getDimension(R.styleable.ShapeLayout_cornerBottomRightRadius, -1f)
        val cornerBottomLeftRadius =
                typedArray.getDimension(R.styleable.ShapeLayout_cornerBottomLeftRadius, -1f)

        shapeBuilder.corner(
                when {
                    cornerTopLeftRadius != -1f -> cornerTopLeftRadius
                    cornerRadius != -1f -> cornerRadius
                    else -> 0f
                },
                when {
                    cornerTopRightRadius != -1f -> cornerTopRightRadius
                    cornerRadius != -1f -> cornerRadius
                    else -> 0f
                },
                when {
                    cornerBottomRightRadius != -1f -> cornerBottomRightRadius
                    cornerRadius != -1f -> cornerRadius
                    else -> 0f
                },
                when {
                    cornerBottomLeftRadius != -1f -> cornerBottomLeftRadius
                    cornerRadius != -1f -> cornerRadius
                    else -> 0f
                }
        )
        /* Corner */

        /* Gradient */
        val haveGradientAttrs =
                typedArray.hasValue(R.styleable.ShapeLayout_gradientStartColor)
                        && typedArray.hasValue(R.styleable.ShapeLayout_gradientEndColor)

        if (haveGradientAttrs) {
            val gradientStartColor =
                    typedArray.getColor(R.styleable.ShapeLayout_gradientStartColor, 0)
            val hasGradientCenterColor =
                    typedArray.hasValue(R.styleable.ShapeLayout_gradientCenterColor)
            val gradientCenterColor =
                    typedArray.getColor(R.styleable.ShapeLayout_gradientCenterColor, 0)
            val gradientEndColor =
                    typedArray.getColor(R.styleable.ShapeLayout_gradientEndColor, 0)
            val gradientAngle = typedArray.getInteger(R.styleable.ShapeLayout_gradientAngle, 270)
            val gradientType = typedArray.getInteger(
                    R.styleable.ShapeLayout_gradientType, GradientDrawable.LINEAR_GRADIENT
            )
            val gradientCenterX =
                    typedArray.getFloatOrFraction(R.styleable.ShapeLayout_gradientCenterX, 0.5f)
            val gradientCenterY =
                    typedArray.getFloatOrFraction(R.styleable.ShapeLayout_gradientCenterY, 0.5f)
            val gradientRadius =
                    typedArray.getDimension(R.styleable.ShapeLayout_gradientRadius, 0.5f)

            if (hasGradientCenterColor) {
                gradientBuilder.colors(gradientStartColor, gradientCenterColor, gradientEndColor)
            } else {
                gradientBuilder.colors(gradientStartColor, gradientEndColor)
            }
            gradientBuilder
                    .angle(gradientAngle)
                    .type(gradientType)
                    .centerX(gradientCenterX)
                    .centerY(gradientCenterY)
                    .gradientRadius(gradientRadius)
            shapeBuilder.gradient(gradientBuilder.build())
        }
        /* Gradient */

        /* Solid */
        val solidColor = typedArray.getColorStateList(R.styleable.ShapeLayout_solid)
        if (solidColor != null) {
            shapeBuilder.solid(solidColor)
        }
        /* Solid */

        /* Stroke */
        val strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeLayout_strokeWidth, 0)
        val strokeColor = typedArray.getColorStateList(R.styleable.ShapeLayout_strokeColor)
                ?: ColorStateList.valueOf(Color.TRANSPARENT)
        val strokeDashWidth = typedArray.getDimension(R.styleable.ShapeLayout_strokeDashWidth, 0f)
        val strokeDashGap = typedArray.getDimension(R.styleable.ShapeLayout_strokeDashGap, 0f)

        strokeBuilder
                .width(strokeWidth)
                .color(strokeColor)
                .dashWidth(strokeDashWidth)
                .dashGap(strokeDashGap)
        shapeBuilder.stroke(strokeBuilder.build())
        /* Stroke */

        typedArray.recycle()
    }

    private fun TypedArray.getFloatOrFraction(index: Int, defaultValue: Float): Float {
        val tv = peekValue(index)
        var v = defaultValue
        if (tv != null) {
            val vIsFraction = tv.type == TypedValue.TYPE_FRACTION
            v = if (vIsFraction) tv.getFraction(1.0f, 1.0f) else tv.float
        }
        return v
    }
}