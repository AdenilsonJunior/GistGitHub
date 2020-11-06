package br.com.adenilson.gist.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class File(
    val filename: String,
    val type: String,
    val rawUrl: String,
    val language: String?,
    val size: Long
) : Parcelable