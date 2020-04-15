package com.lucers.widget.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants
import com.lucers.widget.R

/**
 * WidgetActivity
 */
@Route(path = AppRouteConstants.widgetRoute)
class WidgetActivity : BaseActivity(R.layout.activity_widget) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }
}
