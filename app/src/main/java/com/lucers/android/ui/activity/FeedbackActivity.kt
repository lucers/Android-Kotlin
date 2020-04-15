package com.lucers.android.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

/**
 * FeedbackActivity
 */
@Route(path = AppRouteConstants.feedbackRoute)
class FeedbackActivity : BaseActivity(R.layout.activity_feedback) {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColorInt(Color.WHITE)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}