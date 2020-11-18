package br.com.adenilson.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GistResponse(
    val id: String,
    val owner: OwnerResponse,
    val files: Map<String, FileResponse>,
    val description: String?,
    @Json(name = "html_url")
    val htmlUrl: String
)

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)

@JsonClass(generateAdapter = true)
data class FileResponse(
    val filename: String,
    val type: String,
    @Json(name = "raw_url")
    val rawUrl: String,
    val size: Long,
    val language: String?
)
