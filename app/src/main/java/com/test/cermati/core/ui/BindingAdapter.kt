package com.test.cermati.core.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.test.cermati.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isGone")
    fun bindIsGone(view: View, isGone: Boolean) {
        view.visibility = if (isGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imgView: ImageView, url: String?) {
        url?.let {
            Picasso.get()
                .load(url)
                .placeholder(R.color.grey)
                .fit()
                .centerCrop()
                .into(imgView)
        }
    }

    @JvmStatic
    @BindingAdapter("android:loadImage")
    fun loadImage(imgView: ImageView, image: Int) {

        Picasso.get()
            .load(R.color.white)
            .placeholder(image)
            .fit()
            .centerInside()
            .into(imgView)

    }


}