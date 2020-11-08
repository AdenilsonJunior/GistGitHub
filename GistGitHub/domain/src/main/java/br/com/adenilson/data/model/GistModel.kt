package br.com.adenilson.data.model

import java.util.Date

data class GistModel(
    var id: Long? = null,
    val webId: String,
    val owner: OwnerModel,
    val files: List<FileModel>,
    val description: String?,
    val lastUpdate: Date?,
    val favorite: Boolean = false
) {
    val gistType: String
        get() = files.minBy { it.filename }?.filename.orEmpty()
}