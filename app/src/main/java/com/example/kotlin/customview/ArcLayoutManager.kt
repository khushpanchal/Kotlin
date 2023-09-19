package com.example.kotlin.customview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.acos
import kotlin.math.floor
import kotlin.math.sin

class ArcLayoutManager : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = 0
    private var horizontalScrollEnable = true
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        horizontalScrollOffset += dx
        fill(recycler)
        return dx
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fill(recycler)
    }

    override fun canScrollHorizontally(): Boolean {
        return horizontalScrollEnable
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    private fun fill(recycler: RecyclerView.Recycler?) {
        if(itemCount==0) return
        if (recycler != null) {
            detachAndScrapAttachedViews(recycler)
        }
        val viewWidth = 275
        val viewHeight = 275
        val firstVisPos = floor(horizontalScrollOffset.toDouble()/viewWidth.toDouble()).toInt()
        val lastVisPos = (horizontalScrollOffset+width)/viewWidth


        for(index in firstVisPos .. lastVisPos+1) {
            val recycleIndex = index%itemCount
            if(index <0 || index>=itemCount) {
                continue
            }
            val view = kotlin.runCatching {
                recycler?.getViewForPosition(recycleIndex)
            }.getOrNull() ?: continue

            addView(view)
            measureChildWithMargins(view, viewWidth, viewWidth)

            val left = index*viewWidth - horizontalScrollOffset
            val right = left + viewWidth
            val top = computeYComponent(((left + right) / 2).toFloat(), (viewHeight).toFloat())
            val bottom = top + viewWidth

            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }

        val scrapListCopy = recycler?.scrapList?.toList()
        scrapListCopy?.forEach {
            recycler.recycleView(it.itemView)
        }

    }

    private fun computeYComponent(viewCenterX: Float,
                                  h: Float): Int {

        val s = width.toDouble() / 2
        val radius = (h * h + s * s) / (h * 2)

        val xScreenFraction = viewCenterX.toDouble() / width.toDouble()
        val beta = acos(s / radius)

        val alpha = beta + (xScreenFraction * (Math.PI - (2 * beta)))
        val yComponent = radius - (radius * sin(alpha))

        return yComponent.toInt()

    }

}