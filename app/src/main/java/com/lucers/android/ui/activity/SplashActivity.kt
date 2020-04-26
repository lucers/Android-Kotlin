package com.lucers.android.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.lucers.android.R
import com.lucers.android.databinding.ActivitySplashBinding
import com.lucers.common.constants.AppRouteConstants
import com.lucers.mvvm.BaseMvvmActivity
import com.lucers.android.model.CountDownViewModel

/**
 * SplashActivity
 */
@Route(path = AppRouteConstants.splashRoute)
class SplashActivity : BaseMvvmActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        const val countDownTime: Long = 5L
    }

    private val viewModel: CountDownViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CountDownViewModel::class.java)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        dataBinding.countModel = viewModel
        viewModel.countTime = countDownTime
    }

    override fun onBackPressed() {
        //Splash activity can't be back
    }
}
