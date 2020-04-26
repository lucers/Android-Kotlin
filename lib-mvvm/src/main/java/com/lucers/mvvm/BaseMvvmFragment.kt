package com.lucers.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lucers.common.base.BaseFragment

/**
 * BaseMvvmFragment
 */
abstract class BaseMvvmFragment<DataBinding : ViewDataBinding>(contentLayoutId: Int) : BaseFragment(contentLayoutId) {

    lateinit var dataBinding: DataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }
}