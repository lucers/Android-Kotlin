package com.lucers.common.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.lucers.common.base.BaseFragment
import com.lucers.common.utils.ClassUtils

/**
 * BaseMvvmFragment
 */
abstract class BaseMvvmFragment<DataBinding : ViewDataBinding, VM : BaseViewModel>(contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {

    lateinit var dataBinding: DataBinding
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        dataBinding.lifecycleOwner = this

        ClassUtils.getViewModel<VM>(this)?.let {
            viewModel = ViewModelProvider.NewInstanceFactory().create(it)
        }

        lifecycle.addObserver(viewModel)

        return dataBinding.root
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        super.initData(view, savedInstanceState)
        subscribeUI()
    }

    open fun subscribeUI() {
        viewModel.toastMsg.observe(this, {
            if (it.isBlank().not()) {
                ToastUtils.showShort(it)
            }
        })
        viewModel.showLoading.observe(this, {
            if (it) {
                startLoading()
            } else {
                stopLoading()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onStopRequest()
    }
}