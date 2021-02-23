package com.lucers.common.mvvm

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * BaseMvvmPopupWindow
 */
abstract class BaseMvvmPopupWindow<DataBinding : ViewDataBinding>(val context: Context) : PopupWindow(context) {

    lateinit var dataBinding: DataBinding

    open fun initWindow() {
        dataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), getDialogLayoutId(), FrameLayout(context), false)

        contentView = dataBinding.root
    }

    abstract fun getDialogLayoutId(): Int
}