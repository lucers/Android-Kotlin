package com.lucers.mvvm.model

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide

/**
 * PosterModel
 */
class PosterModel(val context: Application) : AndroidViewModel(context) {

    @BindingAdapter("poster")
    fun loadPoster(imageView: ImageView, url: String) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    fun clickPoster() {

    }
}