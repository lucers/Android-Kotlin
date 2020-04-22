package com.lucers.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucers.android.R
import com.lucers.android.databinding.FragmentHomeBinding
import com.lucers.common.base.BaseFragment

/**
 * HomeFragment
 */
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        super.initData(view, savedInstanceState)
        lifecycle.addObserver(binding.textBanner)
    }
}