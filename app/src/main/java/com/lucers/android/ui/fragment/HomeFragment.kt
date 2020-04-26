package com.lucers.android.ui.fragment

import android.os.Bundle
import android.view.View
import com.lucers.android.R
import com.lucers.android.databinding.FragmentHomeBinding
import com.lucers.mvvm.BaseMvvmFragment

/**
 * HomeFragment
 */
class HomeFragment : BaseMvvmFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initData(view: View, savedInstanceState: Bundle?) {
        super.initData(view, savedInstanceState)
        lifecycle.addObserver(dataBinding.textBanner)
    }
}