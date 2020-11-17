package br.com.adenilson.base.androidextensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> ViewModelProvider.Factory.createViewModel(fragment: Fragment) =
    ViewModelProviders.of(fragment, this).get(T::class.java)

inline fun <reified T : ViewModel> ViewModelProvider.Factory.createViewModel(activity: FragmentActivity) =
    ViewModelProviders.of(activity, this).get(T::class.java)