package com.lucers.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils

/**
 * BaseFragment
 */
abstract class BaseFragment(val contentLayoutId: Int) : Fragment(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initData(view, savedInstanceState)
    }

    open fun initView(view: View, savedInstanceState: Bundle?) {

    }

    open fun initData(view: View, savedInstanceState: Bundle?) {

    }

    open fun startLoading() {
        activity?.let {
            if (it is BaseActivity) {
                it.startLoading()
            }
        } ?: let {
            val topActivity = ActivityUtils.getTopActivity()
            if (topActivity is BaseActivity) {
                it.startLoading()
            }
        }
    }

    open fun stopLoading() {
        activity?.let {
            if (it is BaseActivity) {
                it.stopLoading()
            }
        } ?: let {
            val topActivity = ActivityUtils.getTopActivity()
            if (topActivity is BaseActivity) {
                it.stopLoading()
            }
        }
    }
}