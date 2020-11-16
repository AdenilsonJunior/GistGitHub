package br.com.adenilson.gist.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.adenilson.base.R
import br.com.adenilson.base.androidextensions.hide
import br.com.adenilson.base.androidextensions.loadFromUrl
import br.com.adenilson.base.androidextensions.show

@BindingAdapter("android:loadAvatar")
fun setAvatar(view: ImageView, url: String?) {
    url?.run {
        view.loadFromUrl(this, R.drawable.placeholder_user)
    }
}

@BindingAdapter("android:setGistDescription")
fun setGistDescription(view: TextView, description: String?) {
    if (description.isNullOrBlank()) {
        view.hide()
    } else {
        view.show()
        view.text = description
    }
}