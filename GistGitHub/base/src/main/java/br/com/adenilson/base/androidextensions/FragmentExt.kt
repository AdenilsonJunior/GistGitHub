package br.com.adenilson.base.androidextensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String) {
    view?.run {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }
}

fun Fragment.showIndefiniteSnackBar(message: String, actionText: String, actionClick: () -> Unit) {
    view?.run {
        Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText) {
                actionClick()
            }
            .show()
    }
}