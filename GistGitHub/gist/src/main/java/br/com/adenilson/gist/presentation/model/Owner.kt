package br.com.adenilson.gist.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val id: Long? = null,
    val avatarUrl: String,
    val name: String
) : Parcelable