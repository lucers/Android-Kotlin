package com.lucers.widget.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.lucers.widget.R

/**
 * IndicatorView
 */
class IndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    private var indicatorWidth = 0
    private var indicatorHeight = 0
    private var indicatorMargin = 0

    private val indicatorViews: MutableList<View> = mutableListOf()

    private var indicatorUnSelected: Drawable? = null
    private var indicatorSelected: Drawable? = null

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.IndicatorView)

            indicatorWidth = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_indicatorWidth, indicatorWidth)
            indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_indicatorHeight, indicatorHeight)
            indicatorMargin = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_indicatorMargin, indicatorMargin)

            indicatorUnSelected = typedArray.getDrawable(R.styleable.IndicatorView_indicatorUnSelected)
            indicatorSelected = typedArray.getDrawable(R.styleable.IndicatorView_indicatorSelected)

            typedArray.recycle()

            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }
    }

    fun initIndicator(indicatorCount: Int, position: Int = 0) {
        indicatorViews.clear()
        removeAllViews()
        val params = LayoutParams(indicatorWidth, indicatorHeight)
        params.setMargins(indicatorMargin, 0, indicatorMargin, 0)
        for (i in 0 until indicatorCount) {
            val view = View(context)
            view.background = indicatorUnSelected
            addView(view, params)
            indicatorViews.add(view)
        }
        if (indicatorViews.size > 0 && position < indicatorViews.size) {
            indicatorViews[position].background = indicatorSelected
        }
    }

    fun setSelectedPage(selected: Int) {
        for (i in indicatorViews.indices) {
            if (i == selected) {
                indicatorViews[i].background = indicatorSelected
            } else {
                indicatorViews[i].background = indicatorUnSelected
            }
        }
    }

    fun setUpViewPager2(viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setSelectedPage(position)
            }
        })
    }

    fun setUpRecyclerView(recycler: RecyclerView) {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (position < 0) {
                        return
                    }
                    setSelectedPage(position)
                }
            }
        })
    }
}