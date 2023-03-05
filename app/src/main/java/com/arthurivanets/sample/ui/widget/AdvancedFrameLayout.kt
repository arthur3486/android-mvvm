/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.sample.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.arthurivanets.commons.ktx.extractStyledAttributes
import com.arthurivanets.sample.R
import com.arthurivanets.sample.ui.widget.markers.Roundable

open class AdvancedFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Roundable {

    var scalingType = ScalingType.WIDTH_BASED
        private set

    var aspectRatio = Float.MIN_VALUE
        private set

    private var topLeftCornerRadius = 0f
    private var topRightCornerRadius = 0f
    private var bottomLeftCornerRadius = 0f
    private var bottomRightCornerRadius = 0f

    private lateinit var rect: RectF
    private lateinit var path: Path

    object ScalingType {

        const val WIDTH_BASED = 1
        const val HEIGHT_BASED = 2

        @JvmStatic
        fun isValid(scalingType: Int): Boolean {
            return ((scalingType == WIDTH_BASED) || (scalingType == HEIGHT_BASED))
        }

    }

    init {
        attrs?.let(::fetchAttributes)
        init()
    }

    private fun fetchAttributes(attributes: AttributeSet) {
        context?.extractStyledAttributes(attributes, R.styleable.AdvancedFrameLayout) {
            if (hasValue(R.styleable.AdvancedFrameLayout_corner_radius)) {
                checkCornerRadiusValidity(getDimension(R.styleable.AdvancedFrameLayout_corner_radius, 0f)).also {
                    topLeftCornerRadius = it
                    topRightCornerRadius = it
                    bottomLeftCornerRadius = it
                    bottomRightCornerRadius = it
                }
            } else {
                topLeftCornerRadius =
                    checkCornerRadiusValidity(getDimension(R.styleable.AdvancedFrameLayout_top_left_corner_radius, topLeftCornerRadius))
                topRightCornerRadius =
                    checkCornerRadiusValidity(getDimension(R.styleable.AdvancedFrameLayout_top_right_corner_radius, topRightCornerRadius))
                bottomLeftCornerRadius = checkCornerRadiusValidity(
                    getDimension(
                        R.styleable.AdvancedFrameLayout_bottom_left_corner_radius,
                        bottomLeftCornerRadius
                    )
                )
                bottomRightCornerRadius = checkCornerRadiusValidity(
                    getDimension(
                        R.styleable.AdvancedFrameLayout_bottom_right_corner_radius,
                        bottomRightCornerRadius
                    )
                )
            }

            scalingType = checkScalingTypeValidity(getInt(R.styleable.AdvancedFrameLayout_scaling_type, scalingType))

            if (hasValue(R.styleable.AdvancedFrameLayout_aspect_ratio)) {
                aspectRatio = checkAspectRatioValidity(getFloat(R.styleable.AdvancedFrameLayout_aspect_ratio, aspectRatio))
            }
        }
    }

    private fun init() {
        rect = RectF()
        path = Path()
    }

    private fun reconfigurePath() {
        with(path) {
            reset()
            addRoundRect(rect, getCornerRadii(), Path.Direction.CW)
            close()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // performing the default measuring
        if (!isAspectRationSpecified()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        // performing the custom measuring to satisfy the specified aspect ratio
        val originalWidth = MeasureSpec.getSize(widthMeasureSpec)
        val originalHeight = MeasureSpec.getSize(heightMeasureSpec)

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(
                calculateNewWidth(
                    originalWidth = originalWidth,
                    originalHeight = originalHeight
                ),
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                calculateNewHeight(
                    originalWidth = originalWidth,
                    originalHeight = originalHeight
                ),
                MeasureSpec.EXACTLY
            )
        )
    }

    override fun onSizeChanged(
        width: Int,
        height: Int,
        oldWidth: Int,
        oldHeight: Int
    ) {
        super.onSizeChanged(
            width,
            height,
            oldWidth,
            oldHeight
        )

        rect.set(
            0f,
            0f,
            width.toFloat(),
            height.toFloat()
        )
        reconfigurePath()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.apply {
            val savedState = save()

            if (shouldRoundCorners()) {
                clipPath(path)
            }

            super.dispatchDraw(this)

            restoreToCount(savedState)
        }
    }

    private fun calculateNewWidth(originalWidth: Int, originalHeight: Int): Int {
        return (if (scalingType == ScalingType.WIDTH_BASED) originalWidth else (originalHeight / aspectRatio).toInt())
    }

    private fun calculateNewHeight(originalWidth: Int, originalHeight: Int): Int {
        return (if (scalingType == ScalingType.HEIGHT_BASED) originalHeight else (originalWidth / aspectRatio).toInt())
    }

    private fun checkCornerRadiusValidity(cornerRadius: Float): Float {
        require(cornerRadius >= 0f) {
            "Invalid Corner Radius: $cornerRadius. A valid Corner Radius must be provided."
        }

        return cornerRadius
    }

    private fun checkScalingTypeValidity(scalingType: Int): Int {
        require(ScalingType.isValid(scalingType)) {
            "Invalid Scaling Type: $scalingType. A valid Scaling Type must be provided."
        }

        return scalingType
    }

    private fun checkAspectRatioValidity(aspectRatio: Float): Float {
        require(aspectRatio > 0f) {
            "Invalid Aspect Ratio: $aspectRatio. The Aspect Ratio must be a positive number."
        }

        return aspectRatio
    }

    override fun setCornerRadius(cornerRadius: Float) {
        this.topLeftCornerRadius = cornerRadius
        this.topRightCornerRadius = cornerRadius
        this.bottomLeftCornerRadius = cornerRadius
        this.bottomRightCornerRadius = cornerRadius

        reconfigurePath()
        invalidate()
    }

    override fun setTopLeftCornerRadius(topLeftCornerRadius: Float) {
        this.topLeftCornerRadius = topLeftCornerRadius

        reconfigurePath()
        invalidate()
    }

    override fun getTopLeftCornerRadius(): Float {
        return this.topLeftCornerRadius
    }

    override fun setTopRightCornerRadius(topRightCornerRadius: Float) {
        this.topRightCornerRadius = topRightCornerRadius

        reconfigurePath()
        invalidate()
    }

    override fun getTopRightCornerRadius(): Float {
        return this.topRightCornerRadius
    }

    override fun setBottomLeftCornerRadius(bottomLeftCornerRadius: Float) {
        this.bottomLeftCornerRadius = bottomLeftCornerRadius

        reconfigurePath()
        invalidate()
    }

    override fun getBottomLeftCornerRadius(): Float {
        return this.bottomLeftCornerRadius
    }

    override fun setBottomRightCornerRadius(bottomRightCornerRadius: Float) {
        this.bottomRightCornerRadius = bottomRightCornerRadius

        reconfigurePath()
        invalidate()
    }

    override fun getBottomRightCornerRadius(): Float {
        return this.bottomRightCornerRadius
    }

    private fun getCornerRadii(): FloatArray {
        return floatArrayOf(
            topLeftCornerRadius,
            topLeftCornerRadius,
            topRightCornerRadius,
            topRightCornerRadius,
            bottomRightCornerRadius,
            bottomRightCornerRadius,
            bottomLeftCornerRadius,
            bottomLeftCornerRadius
        )
    }

    fun setAspectRatio(aspectRatio: Float, scalingType: Int = ScalingType.WIDTH_BASED) {
        this.scalingType = scalingType
        this.aspectRatio = aspectRatio

        requestLayout()
    }

    private fun isAspectRationSpecified(): Boolean {
        return (aspectRatio != Float.MIN_VALUE)
    }

    private fun shouldRoundCorners(): Boolean {
        return (
            (topLeftCornerRadius > 0)
                || (topRightCornerRadius > 0)
                || (bottomLeftCornerRadius > 0)
                || (bottomRightCornerRadius > 0)
            )
    }

}
