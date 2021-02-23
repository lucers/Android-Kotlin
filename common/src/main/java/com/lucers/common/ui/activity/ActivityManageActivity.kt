package com.lucers.common.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.R
import com.lucers.common.constants.AppRouteConstants
import com.lucers.common.databinding.ActivityActivityManageBinding
import com.lucers.common.mvvm.BaseMvvmActivity
import com.lucers.common.ui.adapter.ActivityAdapter
import com.lucers.common.view_model.ActivityManageViewModel
import com.lucers.widget.adapter.BaseRecyclerViewAdapter
import com.lucers.widget.itemdecoration.SimpleLineItemDecoration

/**
 * ActivityManageActivity
 */
@Route(path = AppRouteConstants.activityManageRoute)
class ActivityManageActivity :
    BaseMvvmActivity<ActivityActivityManageBinding, ActivityManageViewModel>(R.layout.activity_activity_manage),
    BaseRecyclerViewAdapter.OnItemClickListener {

    private val activityAdapter by lazy {
        ActivityAdapter().also {
            it.itemClickListener = this
        }
    }

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        dataBinding.lifecycleOwner = this

        dataBinding.rvActivity.layoutManager = GridLayoutManager(this, 3)
        if (dataBinding.rvActivity.itemDecorationCount == 0) {
            dataBinding.rvActivity.addItemDecoration(SimpleLineItemDecoration(5f))
        }
        dataBinding.rvActivity.adapter = activityAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val activities = mutableListOf<Class<*>>()
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        for (activityInfo: ActivityInfo in packageInfo.activities) {
            if (activityInfo.name == this.javaClass.name) {
                continue
            }
            val activityClass = Class.forName(activityInfo.name)
            activities.add(activityClass)
        }
        activityAdapter.list = activities
    }

    override fun onItemClick(view: View, position: Int) {
        startActivity(Intent(this, activityAdapter.list[position]))
    }
}