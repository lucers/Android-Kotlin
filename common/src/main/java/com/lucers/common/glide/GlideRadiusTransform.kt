package com.lucers.common.glide

import android.graphics.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.TransformationUtils

/**
 * GlideRadiusTransform
 *
 * @author Lucers
 * @date 2019/3/5 0005
 */
class GlideRadiusTransform(dp: Int) : CenterCrop() {

    private val radius: Int = SizeUtils.dp2px(dp.toFloat())

    override fun transform(
        pool: BitmapPool, source: Bitmap, outWidth: Int, outHeight: Int
    ): Bitmap? {
        val bitmap = TransformationUtils.centerCrop(pool, source, outWidth, outHeight)
        return roundCrop(pool, bitmap)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }
        LogUtils.d("Bitmap source size:" + source.width + "," + source.height)

        val result = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)
        return result
    }
}