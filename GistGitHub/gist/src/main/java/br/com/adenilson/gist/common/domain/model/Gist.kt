package br.com.adenilson.gist.common.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gist(
    var id: Long? = null,
    val webId: String,
    val owner: Owner,
    val files: List<File>,
    val description: String?,
    var favorite: Boolean = false,
    val htmlUrl: String
) : Parcelable {
    val gistType: String
        get() = files.minByOrNull { it.filename }?.filename.orEmpty()
}