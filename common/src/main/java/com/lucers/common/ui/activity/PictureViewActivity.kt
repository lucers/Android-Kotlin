package com.lucers.common.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.StringUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.R
import com.lucers.common.constants.AppRouteConstants
import com.lucers.common.constants.BundleConstants
import com.lucers.common.databinding.ActivityImageViewBinding
import com.lucers.common.mvvm.BaseMvvmActivity
import com.lucers.common.mvvm.BaseViewModel
import com.lucers.common.ui.adapter.PictureViewAdapter

/**
 * PictureViewActivity
 */
@Route(path = AppRouteConstants.pictureViewRoute)
class PictureViewActivity : BaseMvvmActivity<ActivityImageViewBinding, BaseViewModel>(R.layout.activity_image_view) {

    @JvmField
    @Autowired(name = BundleConstants.editPicture)
    var editPicture: Boolean = false

    private val pictureViewAdapter: PictureViewAdapter by lazy {
        PictureViewAdapter()
    }

    override fun initWindow() {
        immersionBar {
            statusBarColor(R.color.black)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        dataBinding.ivClose.setOnClickListener { onBackPressed() }
        dataBinding.ivDelete.setOnClickListener {
            pictureViewAdapter.removeData(index = dataBinding.vpPicture.currentItem)
            setResult(RESULT_OK, Intent().apply {
                val list = arrayListOf<String>()
                pictureViewAdapter.list.forEach { string ->
                    list.add(string)
                }
                putStringArrayListExtra(BundleConstants.pictureList, list)
            })
            if (pictureViewAdapter.list.isEmpty()) {
                finish()
            } else {
                dataBinding.tvPictureCount.text = String.format("%d/%d", dataBinding.vpPicture.currentItem + 1, pictureViewAdapter.itemCount)
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        intent?.let {
            val stringList = it.getStringArrayListExtra(BundleConstants.pictureList)
            if (stringList.isNullOrEmpty()) {
                return
            }
            val index = it.getIntExtra(BundleConstants.pictureIndex, 0)
            val text = it.getStringExtra(BundleConstants.pictureText)

            dataBinding.tvPictureCount.text = String.format("%d/%d", index + 1, stringList.size)

            if (text.isNullOrBlank().not()) {
                dataBinding.tvContent.text = text
                dataBinding.tvContent.visibility = View.VISIBLE
            }

            pictureViewAdapter.list = stringList

            dataBinding.vpPicture.adapter = pictureViewAdapter

            dataBinding.vpPicture.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    dataBinding.tvPictureCount.text = String.format("%d/%d", position + 1, pictureViewAdapter.itemCount)
                }
            })
            dataBinding.ivDelete.visibility = if (editPicture) View.VISIBLE else View.GONE
        }
    }

    override fun onBackPressed() {
        finish()
    }
}