package br.com.adenilson.base.androidextensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String, placeholderResId: Int = 0) {
    Glide.with(context).load(url).placeholder(placeholderResId).into(this)
}
