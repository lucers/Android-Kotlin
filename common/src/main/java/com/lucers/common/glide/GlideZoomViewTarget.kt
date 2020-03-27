package com.lucers.common.glide

import android.graphics.Bitmap
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * GlideZoomViewTarget
 *
 * @author Lucers
 * @date 2019/9/19 0019
 */
class GlideZoomViewTarget(private val imageView: ImageView) :
    BitmapImageViewTarget(imageView) {

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        super.onResourceReady(resource, transition)
        val imageWidth = resource.width
        val imageHeight = resource.height
        val width = imageView.width
        val height = imageHeight * width / imageWidth
        LogUtils.d("imageWidth:$imageWidth,imageHeight$imageHeight,resultSize:$width,$height")

        val para = imageView.layoutParams
        para.height = height
        para.width = width
        imageView.setImageBitmap(resource)
    }
}