package br.com.adenilson.gist.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Gist(
    var id: Long? = null,
    val webId: String,
    val owner: Owner,
    val files: List<File>,
    val description: String?,
    val lastUpdate: Date?
) : Parcelable {

    val gistType: String
        get() = files.minBy { it.filename }?.filename.orEmpty()

}