package com.lucers.widget.wheelview

import android.content.Context
import android.util.AttributeSet
import com.lucers.widget.wheelview.WheelView.OnWheelChangeListener

/**
 * StringWheelView
 *
 * @author Lucers
 * @date 2019/9/3 0003
 */
class StringWheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : WheelView<String?>(context, attrs, defStyle) {

    var selectString: String? = null

    var selectPosition = 0

    private var onStringChangeListener: OnStringChangeListener? = null

    fun initData(strings: List<String>, position: Int) {
        selectPosition = position
        if (strings.isEmpty() || position > strings.size - 1) {
            return
        }
        if (position < 0) {
            selectPosition = 0
        }
        selectPosition = position
        selectString = strings[position]
        setDataList(strings)
        setCurrentPosition(position, false)
        onWheelChangeListener = OnWheelChangeListener { item, index ->
            selectString = item
            selectPosition = index
            if (onStringChangeListener != null) {
                onStringChangeListener!!.onStringChange(item, index)
            }
        }
    }

    fun setOnStringChangeListener(onStringChangeListener: OnStringChangeListener?) {
        this.onStringChangeListener = onStringChangeListener
    }

    interface OnStringChangeListener {
        /**
         * onStringChange
         *
         * @param item     item
         * @param position position
         */
        fun onStringChange(item: String?, position: Int)
    }
}