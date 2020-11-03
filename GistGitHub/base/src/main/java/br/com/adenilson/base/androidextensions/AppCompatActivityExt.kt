package br.com.adenilson.base.androidextensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController

fun AppCompatActivity.setupToolbarWithNavigationController(
    toolbar: Toolbar,
    navController: NavController
) {
    setSupportActionBar(toolbar)
    toolbar.setupWithNavController(navController)
}