package com.lucers.common.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

/**
 * WindowInsetsFrameLayout
 */
class WindowInsetsFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View, child: View) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    requestApplyInsets()
                }
            }

            override fun onChildViewRemoved(parent: View, child: View) {
            }
        })
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        for (index in 0 until childCount) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                getChildAt(index).dispatchApplyWindowInsets(insets)
            }
        }
        return insets
    }
}