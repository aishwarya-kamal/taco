package com.candybytes.taco.ui.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.candybytes.taco.R
import timber.log.Timber

@BindingAdapter("goneUnless")
fun goneUnless(view: View?, visible: Boolean?) {
    view?.visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUri: String?) {

    Timber.d("* imgUri String - $imgUri")
    Timber.d("* imgUri uri - ${imgUri?.toUri()}")
    if (imgUri != null) {
        Glide.with(imgView.context)
            .load(imgUri.toUri())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.animation_loading)
            .error(R.drawable.ic_broken_image)
            .into(imgView)
    }
}