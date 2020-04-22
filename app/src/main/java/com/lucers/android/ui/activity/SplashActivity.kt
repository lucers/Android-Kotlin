package com.lucers.android.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.android.R
import com.lucers.android.databinding.ActivitySplashBinding
import com.lucers.common.constants.AppRouteConstants
import com.lucers.mvvm.base.BaseMvvmActivity
import com.lucers.mvvm.model.CountDownViewModel

/**
 * SplashActivity
 */
@Route(path = AppRouteConstants.splashRoute)
class SplashActivity : BaseMvvmActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: CountDownViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CountDownViewModel::class.java)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        dataBinding.countDownModel = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun onBackPressed() {
        //Splash activity can't be back
    }
}
