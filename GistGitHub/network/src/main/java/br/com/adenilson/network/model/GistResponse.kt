package br.com.adenilson.network.model

import com.google.gson.annotations.SerializedName

data class GistResponse(
    val id: String,
    val owner: OwnerResponse,
    val files: Map<String, FileResponse>
)

data class OwnerResponse(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

data class FileResponse(
    val filename: String,
    val type: String,
    @SerializedName("raw_url")
    val rawUrl: String
)
