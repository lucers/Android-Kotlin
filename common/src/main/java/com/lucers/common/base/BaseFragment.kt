package com.lucers.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * BaseFragment
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(getFragmentLayoutId(), container, false)
        initFragment(view,container,savedInstanceState)
        return view
    }

    abstract fun getFragmentLayoutId(): Int

    abstract fun initFragment(view: View, container: ViewGroup?, savedInstanceState: Bundle?)
}