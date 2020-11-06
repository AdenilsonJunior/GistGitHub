package br.com.adenilson.gist.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val avatarUrl: String,
    val name: String
) : Parcelable