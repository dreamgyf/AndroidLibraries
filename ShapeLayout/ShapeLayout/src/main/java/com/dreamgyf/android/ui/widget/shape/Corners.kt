package com.dreamgyf.android.ui.widget.shape

/**
 * @Author: dreamgyf
 * @Date: 2022/4/28
 */
class Corners private constructor() {

    internal var topLeftRadius = 0f

    internal var topRightRadius = 0f

    internal var bottomRightRadius = 0f

    internal var bottomLeftRadius = 0f

    companion object {
        fun newBuilder() = Builder()
    }

    class Builder internal constructor() {

        private val corners = Corners()

        fun radius(radius: Float): Builder {
            corners.topLeftRadius = radius
            corners.topRightRadius = radius
            corners.bottomLeftRadius = radius
            corners.bottomRightRadius = radius
            return this
        }

        fun radius(topLeftRadius: Float,
                   topRightRadius: Float,
                   bottomRightRadius: Float,
                   bottomLeftRadius: Float): Builder {
            corners.topLeftRadius = topLeftRadius
            corners.topRightRadius = topRightRadius
            corners.bottomRightRadius = bottomRightRadius
            corners.bottomLeftRadius = bottomLeftRadius
            return this
        }

        fun build() = corners
    }
}