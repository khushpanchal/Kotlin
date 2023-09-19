package com.example.kotlin.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.khush.news.R

class CircularImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defSyleAttr: Int = 0):
    AppCompatImageView(context, attrs, defSyleAttr) {

    private var radius: Float = 0f
    private val clipPath = Path()

    init {
        val typedArray: TypedArray? = attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.CircularImageView, 0, 0)
        }
        radius = typedArray?.getDimension(R.styleable.CircularImageView_radius, 0F) ?: 0F
        typedArray?.recycle()
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onDraw(canvas: Canvas?) {
        clipPath.reset()
        clipPath.addCircle(width / 2f, height / 2f, radius, Path.Direction.CW)
        canvas?.clipPath(clipPath)
        super.onDraw(canvas)
    }


}