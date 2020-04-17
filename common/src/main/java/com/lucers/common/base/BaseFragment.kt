package com.lucers.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * BaseFragment
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initData(view, savedInstanceState)
    }

    open fun initView(view: View, savedInstanceState: Bundle?) {

    }

    open fun initData(view: View, savedInstanceState: Bundle?) {

    }
}