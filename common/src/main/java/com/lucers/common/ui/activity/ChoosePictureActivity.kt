package com.lucers.common.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.R
import com.lucers.common.base.BaseApplication
import com.lucers.common.constants.AppRouteConstants
import com.lucers.common.constants.BundleConstants
import com.lucers.common.databinding.ActivityChoosePictureBinding
import com.lucers.common.mvvm.BaseMvvmActivity
import com.lucers.common.ui.adapter.PhotoAdapter
import com.lucers.common.view_model.ChoosePictureViewModel
import com.lucers.widget.adapter.BaseRecyclerViewAdapter
import com.lucers.widget.itemdecoration.SimpleLineItemDecoration
import com.permissionx.guolindev.PermissionX
import java.util.Collections.reverse

/**
 * ChoosePictureActivity
 */
@Route(path = AppRouteConstants.choosePictureRoute)
class ChoosePictureActivity : BaseMvvmActivity<ActivityChoosePictureBinding, ChoosePictureViewModel>(R.layout.activity_choose_picture),
    BaseRecyclerViewAdapter.OnItemClickListener {

    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter().also {
            it.itemClickListener = this
        }
    }

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColorInt(Color.WHITE)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        dataBinding.lifecycleOwner = this

        dataBinding.ivBack.setOnClickListener { onBackPressed() }
        dataBinding.ivRightFunction.setOnClickListener { startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)) }

        dataBinding.rvPhotos.layoutManager = GridLayoutManager(this, 3)
        if (dataBinding.rvPhotos.itemDecorationCount == 0) {
            dataBinding.rvPhotos.addItemDecoration(SimpleLineItemDecoration(2f))
        }
        dataBinding.rvPhotos.adapter = photoAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        PermissionX.init(this)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    getLocalPhoto()
                } else {
                    ToastUtils.showShort(R.string.need_access_external_permission)
                }
            }
    }

    private fun getLocalPhoto() {
        LoaderManager.getInstance(this).initLoader(0, Bundle(), object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                LogUtils.d("onCreateLoader")
                return CursorLoader(
                    BaseApplication.INSTANCE,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                    arrayOf("image/jpeg", "image/png"),
                    MediaStore.Images.Media.DATE_MODIFIED
                )
            }

            override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
                cursor?.let {
                    val photos = mutableListOf<String>()
                    if (it.count == 0) {
                        LogUtils.e("Cursor is empty")
                        photoAdapter.list = photos
                        return
                    }
                    cursor.moveToFirst()
                    do {
                        val path = cursor.getString(cursor.getColumnIndex("_data"))
                        photos.add(path)
                    } while (cursor.moveToNext())
                    reverse(photos)
                    photoAdapter.list = photos
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
                LogUtils.d("onLoaderReset")
            }
        })
    }

    override fun onItemClick(view: View, position: Int) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(BundleConstants.photo, photoAdapter.list[position])
        })
        finish()
    }
}