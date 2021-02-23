package com.lucers.widget.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lucers.widget.R

/**
 * ImageBanner
 */
class ImageBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle), LifecycleObserver {

    private val horizontal = 0
    private val vertical = 1

    var autoLoop: Boolean = false
    var orientation: Int = horizontal
    var delaySec: Int = 3

    var scrolling: Boolean = false

    var currentPosition = 0

    init {
        initAttrs(context, attrs)

        isNestedScrollingEnabled = false

        layoutManager = if (orientation == vertical) {
            LinearLayoutManager(context, VERTICAL, false)
        } else {
            LinearLayoutManager(context, HORIZONTAL, false)
        }

        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

        PagerSnapHelper().attachToRecyclerView(this)

        addOnScrollListener(object : OnScrollListener() {
            var drag: Boolean = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                        if (position < 0) {
                            return
                        }
                        currentPosition = position
                    }
                    if (drag) {
                        drag = false
                    }
                    if (autoLoop) {
                        startScrolling()
                    }
                }
                if (newState == SCROLL_STATE_DRAGGING) {
                    drag = true
                    stopScrolling()
                }
            }
        })
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageBanner)
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            when (val index = typedArray.getIndex(i)) {
                R.styleable.ImageBanner_autoLoop -> {
                    autoLoop = typedArray.getBoolean(index, autoLoop)
                }
                R.styleable.ImageBanner_orientation -> {
                    orientation = typedArray.getInt(index, orientation)
                }
                R.styleable.ImageBanner_delaySec -> {
                    delaySec = typedArray.getInt(index, delaySec)
                }
            }
        }
        typedArray.recycle()
    }

    private val loopHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val runnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                adapter?.let {
                    currentPosition++
                    if (currentPosition == it.itemCount) {
                        currentPosition = 0
                        scrollToPosition(currentPosition)
                        // trigger scroll state change
                        smoothScrollToPosition(currentPosition)
                    } else {
                        smoothScrollToPosition(currentPosition)
                    }
                    loopHandler.postDelayed(this, delaySec * 1000L)
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startScrolling() {
        if (scrolling) {
            return
        }
        adapter?.let {
            if (it.itemCount <= 1) {
                return
            }

            loopHandler.removeCallbacks(runnable)
            loopHandler.postDelayed(runnable, delaySec * 1000L)

            scrolling = true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopScrolling() {
        loopHandler.removeCallbacks(runnable)
        loopHandler.removeMessages(0)
        scrolling = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        loopHandler.removeCallbacks(runnable)
        loopHandler.removeMessages(0)
        scrolling = false
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (autoLoop) {
            startScrolling()
        }
    }
}