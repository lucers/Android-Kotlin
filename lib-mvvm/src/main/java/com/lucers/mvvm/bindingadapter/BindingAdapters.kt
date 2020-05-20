package com.lucers.mvvm.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * BindingAdapters
 */

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, imageUrl: String) {
    if (imageUrl.isEmpty()) {
        return
    }
    Glide.with(view.context)
        .load(imageUrl)
        .centerCrop()
        .into(view)
}